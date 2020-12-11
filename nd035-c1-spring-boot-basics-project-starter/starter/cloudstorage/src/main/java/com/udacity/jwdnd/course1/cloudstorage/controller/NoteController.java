package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private NoteService noteService;
    private UserService userService;
    private NoteMapper noteMapper;
    public NoteController(NoteService noteService, UserService userService, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.userService = userService;
        this.noteMapper = noteMapper;
    }
    @PostMapping("/upload-note")
    public String handleNoteUploadAndEdit(Model model, @ModelAttribute("noteForm") Note note, Authentication authentication ){
        User user = this.userService.findUser(authentication.getName());
        System.out.println("ID: " + note.getNoteId());
        Integer userId = user.getUserId();
        note.setUserId(userId);
        try{
            if(note.getNoteId() == 0){
                noteMapper.insertNote(note);
                model.addAttribute("message","New Note added successfully!");
            } else{
                noteMapper.updateNote(note);
                model.addAttribute("message","Note updated successfully!");
            }
            model.addAttribute("success",true);
            //this.noteService.inserOrUpdatetNote(note,userId);
            System.out.println(this.noteService.getNoteTitle(note));
        }catch(Exception e){
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }
        return "result";
    }
    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, Authentication authentication, Model model) {
        User user = this.userService.findUser(authentication.getName());
        int userId = user.getUserId();
        System.out.println("NoteId is in the deleteNote function is "+noteId);
        try {
            noteService.deleteNote(noteId, userId);
            model.addAttribute("success",true);
            model.addAttribute("message", "Note deleted!");
        } catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message","System error!" + e.getMessage());
        }
        return "result";
    }
}
