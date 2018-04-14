/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author npvu
 */
public class EncryptionUtils {
    
    public EncryptionUtils(){
        
    }
    
    public static String encryptMatKhau(String matKhau){
        String encrypt = "";
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(matKhau.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            encrypt = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encrypt;
    }
}
