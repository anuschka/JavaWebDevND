package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {
    private CredentialService credentialService;
    private UserService userService;
    public CredentialsController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }
    private Credentials convertToCredentials(CredentialsForm credentialsForm, Integer userId){
        Credentials credential = new Credentials();
        credential.setUserId(userId);
        credential.setCredentialId(credentialsForm.getCredentialId());
        credential.setUrl(credentialsForm.getUrl());
        credential.setUsername(credentialsForm.getUrl());
        credential.setPassword(credentialsForm.getPassword());
        return credential;
    }
    @PostMapping("/insert-credential")
    public String handleCredentialUploadAndEdit(Model model, @ModelAttribute("credential") CredentialsForm credentialsForm, Authentication authentication, @ModelAttribute("encryptionService")EncryptionService encryptionService){
        User user = this.userService.findUser(authentication.getName());
        Integer userId = user.getUserId();
        Credentials credential = convertToCredentials(credentialsForm, userId);
        System.out.println("ID: " + credential.getCredentialId());
        try{
            if(credential.getCredentialId() == 0){
                credentialService.insertCreds(credential, userId);
                model.addAttribute("message","New Credentials added successfully!");
            } else{
                credentialService.updateCreds(credential, userId);
                model.addAttribute("message","Credentials updated successfully!");
            }
            model.addAttribute("success",true);
        }catch(Exception e){
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }
        return "result";
    }
    @GetMapping("/deleteCredential/{credentialId}")
    public String deleteNote(@PathVariable("credentialId") int credentialId, Authentication authentication, Model model) {
        User user = this.userService.findUser(authentication.getName());
        int userId = user.getUserId();
        System.out.println("credentialId in the deleteCredential function is "+credentialId);
        try {
            credentialService.deleteCreds(credentialId, userId);
            model.addAttribute("success",true);
            model.addAttribute("message", "Credential deleted!");
        } catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message","System error!" + e.getMessage());
        }
        return "result";
    }
}