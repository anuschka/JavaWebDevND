package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    public NoteController(UserService userService, NoteService noteService){
        this.userService = userService;
        this.noteService = noteService;
    }
    @PostMapping("/upload-note")
    public String createOrUpdateNote(@ModelAttribute("note") NoteForm noteForm, Model model){
        String currentUsername = userService.findCurrentUsername(SecurityContextHolder.getContext().getAuthentication());
        Integer userId = noteService.findUserId(currentUsername);
        if(noteForm.getNoteId() != null){
            Note note = new Note(noteForm.getNoteId(),noteForm.getTitle(),noteForm.getDescription(),userId);
            noteService.update(note);
        } else{
            Note note = new Note(null,noteForm.getTitle(),noteForm.getDescription(),userId);
            noteService.createNote(note);
        }
        model.addAttribute("successMessage", true);
        return "result";
    }
}
