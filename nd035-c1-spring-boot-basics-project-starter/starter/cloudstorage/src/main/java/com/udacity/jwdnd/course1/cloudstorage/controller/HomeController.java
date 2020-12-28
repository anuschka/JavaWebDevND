package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private NoteService noteService;
    private CredentialService credentialService;
    private FileService fileService;
    private List<Note> notes;
    private List<Credentials> credentials;
    private List<File> files;

    public HomeController(final UserService userService, final NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }
    @PostConstruct
    public void postConstruct(){
        credentials = new ArrayList<>();
        notes = new ArrayList<>();
        files = new ArrayList<>();

    }
    @ModelAttribute("note")
    public NoteForm getNoteForm(){
        return new NoteForm();
    }
    @ModelAttribute("credential")
    public CredentialsForm getCredentialsForm(){
        return new CredentialsForm();
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionService(){
        return new EncryptionService();
    }

    @ModelAttribute("file")
    public FileForm getFileForm(){
        return new FileForm();
    }

    @GetMapping
    public String getHomeMap(Model model, Authentication authentication) {
        User user = this.userService.findUser(authentication.getName());
        if(user == null || user.getUserId() == UserService.USER_NOT_FOUND) {
            return "home";
        }
        notes = noteService.findAllNotes(user.getUserId());
        credentials = credentialService.credsUpload(user.getUserId());
        files = fileService.filesUpload(user.getUserId());
        if(notes == null) {
            notes = new ArrayList<>();
        }
        if(credentials == null) {
            notes = new ArrayList<>();
        }
        if(files == null) {
            notes = new ArrayList<>();
        }
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("files", files);
        return "home";
    }
}