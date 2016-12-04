package com.telecomjs.handlers;

import java.util.Random;

/**
 * Created by zark on 16/12/3.
 */
public class KeysHandler {
    //public static Random random = new Random();

    private int count=0;
    private Object lock = new Object();

    private static class SingletonHolder {
        private static final KeysHandler INSTANCE = new KeysHandler();
    }
    public static final KeysHandler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private  int  increment(){
        synchronized(lock) {
            if (count >= 1000) {
                count = 0;
            }
            return count++;
        }
    }


    public String generateSequence(){
        long seq = System.currentTimeMillis();
        return String.valueOf(seq).concat(String.format("%03d", increment()));
    }


}
