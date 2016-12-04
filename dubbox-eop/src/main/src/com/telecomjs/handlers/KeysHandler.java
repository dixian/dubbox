package com.telecomjs.handlers;

import java.util.Random;

/**
 * Created by zark on 16/12/3.
 */
public class KeysHandler {
    public static Random random;
    static {
        random = new Random();
    }
    static public String generateSequence(){
        long seq = System.currentTimeMillis();
        int rint = random.nextInt(1000);
        return String.valueOf(seq).concat(String.format("%03d", rint));
    }
}
