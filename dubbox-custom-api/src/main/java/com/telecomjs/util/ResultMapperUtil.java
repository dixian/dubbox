package com.telecomjs.util;

/**
 * Created by zark on 16/11/9.
 */
public class ResultMapperUtil {
    public static ResultMapper okResultMapper(String m,Object o){
        return new ResultMapper(m,o,ResultMapper.CODE_OK);
    }
    public static  ResultMapper errResultMapperErr(String m,Object o){
        return new ResultMapper(m,o,ResultMapper.CODE_ERR);
    }

    public static ResultMapper defaultResultMapper(String m,Object o){
        if (o == null){
            return new ResultMapper(m,o,ResultMapper.CODE_OK);
        }
        else {
            return new ResultMapper(m,o,ResultMapper.CODE_ERR);
        }
    }
}
