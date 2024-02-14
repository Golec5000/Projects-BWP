package org.application.bwp.rentalskiequcrud.security;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeysStorage {
    KeyPair generateKeyPair();
    String signData(String data, PrivateKey privateKey);
    boolean verifySignature(String data, String signature, PublicKey publicKey);
    boolean saveKeys(KeyPair keyPair, String userId, char[] password);
    KeyPair loadKeys(String userId, char[] password);
}
