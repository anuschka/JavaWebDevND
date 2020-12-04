package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
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
    private List<Note> notes;

    public HomeController(final UserService userService, final NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }
    @PostConstruct
    public void postConstruct(){
        notes = new ArrayList<>();
    }
    @GetMapping
    public String getHomeMap(@ModelAttribute("note") NoteForm noteForm, Model model) {
        String currentUser = userService.findCurrentUsername(SecurityContextHolder.getContext().getAuthentication());
        User user = userService.findUser(currentUser);
        if(user == null || user.getUserId() == UserService.USER_NOT_FOUND) {
            return "home";
        }

        notes = noteService.findAllNotes(user.getUserId());

        if(notes == null) {
            notes = new ArrayList<>();
        }

        model.addAttribute("notes", notes);

        return "home";
    }
}