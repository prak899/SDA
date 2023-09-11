package com.example.testing.utils;
import android.util.Base64;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

    public static String encrypt(String input, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getKey(secretKey));
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }

    public static String decrypt(String encryptedInput, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, getKey(secretKey));
        byte[] encryptedBytes = Base64.decode(encryptedInput, Base64.DEFAULT);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private static SecretKey getKey(String secretKey) {
        byte[] decodedKey = Base64.decode(secretKey, Base64.DEFAULT);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static String generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom();
        keyGen.init(256, secureRandom);
        SecretKey secretKey = keyGen.generateKey();
        return Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
    }
}
