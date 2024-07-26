package com.GSU24SE43.ConstructionDrawingManagement.Utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class FileUtils {

    public InputStream decryptFile(String filePath, String secretKey) throws IOException {
        // Decode the base64 encoded secret key
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);

        // Read the encrypted file
        FileInputStream fis = new FileInputStream(filePath);
        byte[] encryptedData = new byte[fis.available()];
        fis.read(encryptedData);
        fis.close();

        try {
            // Decrypt the data
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
            byte[] decryptedData = cipher.doFinal(encryptedData);

            // Convert decrypted data to InputStream
            return new ByteArrayInputStream(decryptedData);
        } catch (Exception e) {
            log.error("Failed to decrypt file", e);
            throw new IOException("Failed to decrypt file", e);
        }
    }

    public void encryptFile(String filePath) {
        try {
            // Path to the original file
            //String filePath = "src/main/resources/serviceAccountKey.json";

            // Read the original file
            byte[] fileData = Files.readAllBytes(Paths.get(filePath));

            // Generate a key
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            byte[] keyBytes = secretKey.getEncoded();

            // Save the key securely (for demonstration, we'll print it)
            String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
            log.info("Secret Key: {}", encodedKey);

            // Encrypt the file data
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
            byte[] encryptedData = cipher.doFinal(fileData);

            // Write the encrypted data to a file
            String encryptedFilePath = "src/main/resources/encryptedServiceAccountKey.enc";
            FileOutputStream fos = new FileOutputStream(encryptedFilePath);
            fos.write(encryptedData);
            fos.close();
        } catch (IOException ioException) {
            log.error("Encryption failed", ioException);
        } catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            log.error("Encryption failed", e);
            throw new RuntimeException(e);
        }
    }
}
