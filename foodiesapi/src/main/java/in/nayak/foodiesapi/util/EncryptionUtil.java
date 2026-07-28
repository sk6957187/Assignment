package in.nayak.foodiesapi.util;

import in.nayak.foodiesapi.config.ReadExternalFile;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class EncryptionUtil {

    private final SecretKey key;

    public EncryptionUtil(ReadExternalFile readExternalFile) {

        String secretKey = readExternalFile.getEncryptedKey();

        if (secretKey == null || secretKey.length() != 32) {
            throw new IllegalArgumentException(
                    "AES-256 key must be exactly 32 characters long."
            );
        }

        this.key = new SecretKeySpec(
                secretKey.getBytes(StandardCharsets.UTF_8),
                "AES"
        );
    }

    public byte[] encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    public String decrypt(byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(
                cipher.doFinal(encryptedData),
                StandardCharsets.UTF_8
        );
    }
}