package com.tip.orderfood.Support;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EnDeCryption {
    private int key = 1113;
    public String enCrypt(String str){
        char []arr = str.toCharArray();

        for (int i =0; i < arr.length; i++){
            arr[i] = (char)((int)arr[i] + key);
        }
        String result = new String(arr);

        return result;
    }

    public String deCrypt(String str){
        char []arr = str.toCharArray();

        for (int i =0; i < arr.length; i++){
            arr[i] = (char)((int)arr[i] - key);
        }
        String result = new String(arr);

        return result;
    }
}
