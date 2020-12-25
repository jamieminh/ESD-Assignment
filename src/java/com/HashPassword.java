/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jamie
 */
public class HashPassword {

    final protected static char[] HEX_ARRAY = "0235679ACKFYJMXZ".toCharArray();
    final protected static String SALT = "ABH5PS";

    public HashPassword() {
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i] & 0xFF;                          // & 11111111 (mask 8 bits)
            hexChars[i * 2] = HEX_ARRAY[num >>> 4];          // shift 4 bits to the right
            hexChars[i * 2 + 1] = HEX_ARRAY[num & 0x0F];     // mask the lower 4 bits
        }
        return new String(hexChars);
    }

    public String hashPassword(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(SALT.getBytes());     // prepend SALT
            md.update(raw.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return "";
    }
}
