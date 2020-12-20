/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import Login.HashPassword;
import Login.UserToken;
import com.User;
import com.Person;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author WIN 10
 */
public class testMain {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String SALT = "ABHPS";

    public static void main(String args[]) {
//        User user1 = new User("jamie", "nguyen", "doctor", true);
//        user1.signup();
//        
//        Person p1 = new Person("bao", "bui", "client", true, "Bao Thien", "Phu Nhuan");
//        p1.signup();

//        System.out.println(generateToken());
//        System.out.println(generateToken());
//        System.out.println(generateToken());
//        System.out.println(generateToken());
//        System.out.println(generateToken());
//        System.out.println(hashPassword("minh"));
//        System.out.println(hashPassword("minh"));
//        System.out.println(hashPassword("mint"));
//        System.out.println(hashPassword("minhhue"));

        HashPassword hashpw = new HashPassword();
//        System.out.println(hashpw.hashPassword("nguyen"));
        System.out.println(hashpw.hashPassword("jamie"));
//        System.out.println(hashpw.hashPassword("jamie4"));
//        System.out.println(hashpw.hashPassword("hayden"));

//        String token = new UserToken().generateToken();
//        System.out.println(token);
//        System.out.println(new UserToken().generateToken());
//        System.out.println(new UserToken().generateToken());
//        System.out.println(new UserToken().generateToken());
    }

    static String generateToken() {
        char[] hexArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        String token = "";

        for (int i = 0; i < 50; i++) {
            int rand = ThreadLocalRandom.current().nextInt(0, 36);
            token += hexArray[rand];
        }

        return token;

    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int j;
        for (int i = 0; i < bytes.length; i++) {
            j = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[j >>> 4];
            hexChars[i * 2 + 1] = hexArray[j & 0x0F];
        }
        return new String(hexChars);
    }

    public static String hashPassword(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes());     // prepend SALT
            md.update(in.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
