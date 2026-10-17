package com.lsuncar.securityhub;

import com.lsuncar.securityhub.util.Crypto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoTest {
    private static final Logger logger = LoggerFactory.getLogger(CryptoTest.class);
    private static final String secretKey = "************************************";

    @Test
    public void shouldEncryptAndDecryptSuccessfully () {
        Crypto crypto = new Crypto(secretKey);
        String testText = "test";
        String encrypted = crypto.encrypt(testText);
        logger.info("Encrypted text: {}", encrypted);
        String decrypted = crypto.decrypt(encrypted);
        logger.info("Decrypted text: {}", decrypted);

        assertEquals(testText, decrypted);
    }

    @Test
    public void shouldGenerateDifferentCiphertextForSamePlaintext () {
        Crypto crypto = new Crypto(secretKey);
        String testText = "test";
        String encrypted = crypto.encrypt(testText);
        logger.info("Encrypted text: {}", encrypted);
        String encrypted2 = crypto.encrypt(testText);
        logger.info("Encrypted text: {}", encrypted2);

        assertNotEquals(encrypted, encrypted2);
    }

    @Test
    public void shouldRejectTamperedCiphertext () {
        Crypto crypto = new Crypto(secretKey);
        String testText = "test";
        String encrypted = crypto.encrypt(testText);
        byte [] encryptedBytes = Base64.getDecoder().decode(encrypted);
        encryptedBytes[18] ^= 1;
        String tampered = Base64.getEncoder().encodeToString(encryptedBytes);
        logger.info("Tampered text: {}", tampered);

        Exception exception = assertThrows( RuntimeException.class, () -> crypto.decrypt(tampered) );
        logger.error( exception.getMessage(), exception );
    }

    @Test
    public void shouldRejectDecryptionWithWrongKey() {
        Crypto crypto = new Crypto(secretKey);
        String testText = "test";
        String encrypted = crypto.encrypt(testText);
        String wrongSecret = "*****************************************";
        Crypto wrongCrypto = new Crypto(wrongSecret);
        Exception exception = assertThrows( RuntimeException.class, () -> wrongCrypto.decrypt(encrypted) );
        logger.error( exception.getMessage(), exception );
    }

}
