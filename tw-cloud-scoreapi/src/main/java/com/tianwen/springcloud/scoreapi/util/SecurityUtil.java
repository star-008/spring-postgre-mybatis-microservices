/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tianwen.springcloud.scoreapi.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 *
 * @author rjh
 */
public class SecurityUtil {
    public static String generateHash(String str) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = new byte[0];
            try {
                array = md.digest(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }

            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        
        return null;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
