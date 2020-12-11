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
    private List<Note> notes;
    private List<Credentials> credentials;

    public HomeController(final UserService userService, final NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }
    @PostConstruct
    public void postConstruct(){
        credentials = new ArrayList<>();
        notes = new ArrayList<>();
    }

    @GetMapping
    public String getHomeMap(@ModelAttribute("note") NoteForm noteForm,
                             @ModelAttribute("credentials") CredentialsForm credentialsForm,
                             Model model, Authentication authentication) {
        User user = this.userService.findUser(authentication.getName());
        if(user == null || user.getUserId() == UserService.USER_NOT_FOUND) {
            return "home";
        }

        notes = noteService.findAllNotes(user.getUserId());
        credentials = credentialService.credsUpload(user.getUserId());

        if(notes == null) {
            notes = new ArrayList<>();
        }
        if(credentials == null) {
            notes = new ArrayList<>();
        }


        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);

        return "home";
    }
}