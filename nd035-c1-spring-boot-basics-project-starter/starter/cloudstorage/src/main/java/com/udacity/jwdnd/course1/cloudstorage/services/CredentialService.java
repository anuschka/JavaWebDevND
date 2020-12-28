package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CredentialService {
    private UserService userService;
    private EncryptionService encryptionService;
    private final HashService hashService;
    private CredentialsMapper credentialsMapper;

    public CredentialService(UserService userService, CredentialsMapper credentialsMapper, HashService hashService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialsMapper = credentialsMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    private String[] encodePassword(String password, Credentials credential) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);
        return new String[]{encryptedPassword, encodedKey};
    }

    public int deleteCreds(int credentialId, int userId) {
        return credentialsMapper.deleteCredential(credentialId, userId);
    }

    public int insertCreds(Credentials creds, int userId) {
        creds.setUserId(userId);
        creds.setUsername(creds.getUsername());
        String[] result = encodePassword(creds.getPassword(), creds);
        creds.setPassword(result[0]);
        creds.setKey(result[1]);
        return credentialsMapper.insertCredential(creds);
    }

    public int updateCreds(Credentials creds, int userId) {
        creds.setUserId(userId);
        creds.setUsername(creds.getUsername());
        String[] result = encodePassword(creds.getPassword(), creds);
        creds.setPassword(result[0]);
        creds.setKey(result[1]);
        return credentialsMapper.updateCredential(creds);
    }

    public List<Credentials> credsUpload(int userId) {
        return credentialsMapper.getCredsByUserId(userId);
    }


}
