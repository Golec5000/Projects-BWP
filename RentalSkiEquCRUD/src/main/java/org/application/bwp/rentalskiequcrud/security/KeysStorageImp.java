package org.application.bwp.rentalskiequcrud.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeysStorageImp implements KeysStorage {

    private static KeysStorageImp instance;
    private static final String PRIVATE_KEY_FILE = "src/main/resources/org/application/bwp/rentalskiequcrud/security/privateKey_";
    private static final String PUBLIC_KEY_FILE = "src/main/resources/org/application/bwp/rentalskiequcrud/security/publicKey_";

    private KeysStorageImp() {
    }

    public static KeysStorageImp getInstance() {
        if (instance == null) {
            instance = new KeysStorageImp();
        }
        return instance;
    }

    @Override
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String signData(String data, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            byte[] signedData = signature.sign();
            return Base64.getEncoder().encodeToString(signedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean verifySignature(String data, String signature, PublicKey publicKey) {
        try {
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);
            sig.update(data.getBytes());
            return sig.verify(signatureBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean saveKeys(KeyPair keyPair, String userId, char[] password) {
        try {
            // Save the private key
            try (FileOutputStream fos = new FileOutputStream(PRIVATE_KEY_FILE + userId + ".key")) {
                fos.write(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
            }

            // Save the public key
            try (FileOutputStream fos = new FileOutputStream(PUBLIC_KEY_FILE + userId + ".key")) {
                fos.write(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public KeyPair loadKeys(String userId, char[] password) {
        try {
            // Load the private key
            byte[] privateKeyBytes;
            try (FileInputStream fis = new FileInputStream(PRIVATE_KEY_FILE + userId + ".key")) {
                privateKeyBytes = Base64.getDecoder().decode(fis.readAllBytes());
            }
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);

            // Load the public key
            byte[] publicKeyBytes;
            try (FileInputStream fis = new FileInputStream(PUBLIC_KEY_FILE + userId + ".key")) {
                publicKeyBytes = Base64.getDecoder().decode(fis.readAllBytes());
            }
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}