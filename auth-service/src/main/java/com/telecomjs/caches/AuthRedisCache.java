package com.telecomjs.caches;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class AuthRedisCache implements Cache{

    private RedisTemplate<String, Object> redisTemplate;
    private String                        name;
    private long    liveTime = 3600;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLiveTime(long liveTime) {
        this.liveTime = liveTime;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }

    public long getIncrementValue(String key){
        Object object = redisTemplate.opsForValue().get(key);
        if (object == null){
            redisTemplate.opsForValue().increment(key,0l);
        }
        return (object==null ? 0 : Long.valueOf((String) object));
    }

    public void incrementValue(final String key, long value ){
        redisTemplate.opsForValue().increment(key,1L);
        Object object = redisTemplate.opsForValue().get(key);
        String times = (object == null ? "0" : (String)object);
        final long currLiveTime = 86400;
        if (times.equals("1")){
            redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] keyb = key.getBytes();
                    connection.expire(keyb, currLiveTime );
                    return 1L;
                }
            });
        }
    }
    @Override
    public ValueWrapper get(Object key) {
        String simpleKey = null;
        if (key instanceof SimpleKey)
            simpleKey = ((SimpleKey) key).toString();
        final String keyf = simpleKey==null ? (String) key : simpleKey;
        Object object = null;

        object = redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return toObject(value);

            }
        });
        return (object != null ? new SimpleValueWrapper(object) : null);
    }

    @Override
    public void put(Object key, Object value) {
        final String keyf = (String) key;
        final Object valuef = value;
        final long currLiveTime = this.liveTime;

        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                if (currLiveTime > 0) {
                    connection.expire(keyb, currLiveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 描述 : <Object转byte[]>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param obj
     * @return
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 描述 : <byte[]转Object>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     *
     * @param bytes
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    @Override
    public void evict(Object key) {
        final String keyf = (String) key;
        redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(keyf.getBytes());
            }
        });
    }

    @Override
    public void clear() {
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = get(key);
        return wrapper == null ? null : (T) wrapper.get();
    }

    @Override
    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

}
