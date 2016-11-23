package com.telecomjs.utils;

import org.mortbay.jetty.security.DigestAuthenticator;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by zark on 16/11/21.
 */
public class CodeUtil {
    public static final String passwordSalt = "thisisatest";
    public static String encodePassword(String origin){

        String newPassword = null;

        try {
            MessageDigest md5  = MessageDigest.getInstance("md5");
            BASE64Encoder encoder = new BASE64Encoder();
            newPassword = encoder.encode (md5.digest(origin.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return newPassword;
    }

    public static String generateToken() {
        final long stamp = System.currentTimeMillis();
        Random rand = new Random();
        int randInt = rand.nextInt();

        byte[] array = new byte[12];
        for (int i=3; i>=0; i--)
        {
            array[0+3-i] = (byte) (randInt >> i*8);
        }
        for (int i=7; i>=0; i--)
        {
            array[4+7-i] = (byte) (stamp >> i*8);
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(array);
    }
}
