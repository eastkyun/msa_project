package com.msa.membership.adapter.out.security;

import com.msa.membership.SecurityKeyConfig;
import org.springframework.stereotype.Component;

@Component
public class SecurityAdapter {
    private final AESProvider encryptor;

    public SecurityAdapter(SecurityKeyConfig securityKeyConfig) {
        this.encryptor = new AESProvider(securityKeyConfig.getKey());
    }
    public String encrypt(String plainText)  {
        try {
            return encryptor.encrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            return encryptor.decrypt(encryptedText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}