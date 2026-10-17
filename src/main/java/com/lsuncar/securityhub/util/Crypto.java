package com.lsuncar.securityhub.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

@Component
public class Crypto {

    private final SecretKey secretKey;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final String KEY_ALGORITHM = "AES";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;
    private static final int KEY_LENGTH = 32;
    public Crypto(@Value("${securityhub.encryption.key}") String encryptionKey) {
        this.secretKey = convertStringToSecretKey(encryptionKey);
    }

    public String encrypt(String secret) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec iv = generateIv();
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, iv);
            byte[] cipherText = cipher.doFinal(secret.getBytes(StandardCharsets.UTF_8));
            //iv + cipherText ( cipher text contains authentication tag )
            byte[] result = ByteBuffer.allocate(IV_LENGTH + cipherText.length).put(iv.getIV()).put(cipherText).array();
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String decrypt(String secret) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] decodedSecret = Base64.getDecoder().decode(secret.getBytes(StandardCharsets.UTF_8));
            byte[] iv = Arrays.copyOfRange(decodedSecret, 0, IV_LENGTH);
            byte[] justCipher = Arrays.copyOfRange(decodedSecret, IV_LENGTH, decodedSecret.length) ;
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey, new GCMParameterSpec(TAG_LENGTH, iv));
            byte[] cipherText = cipher.doFinal(justCipher);
            return new String(cipherText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static GCMParameterSpec generateIv() {
        byte[] iv = new byte[IV_LENGTH];
        SECURE_RANDOM.nextBytes(iv);
        return new GCMParameterSpec(TAG_LENGTH, iv);
    }

    private SecretKey convertStringToSecretKey(String encryptionKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encryptionKey);
        if (decodedKey.length != KEY_LENGTH)
            throw new IllegalArgumentException("Invalid encryption key length"); //TODO custom exception
        return new SecretKeySpec(decodedKey, KEY_ALGORITHM);
    }

}