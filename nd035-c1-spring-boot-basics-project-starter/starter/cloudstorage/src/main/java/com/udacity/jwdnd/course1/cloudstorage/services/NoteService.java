package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    private boolean successfullySaved;

    public NoteService(final NoteMapper noteMapper, final UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int createNote(final Note note) {
        return noteMapper.insertNote(note);
    }

    public void update(final Note note) {
        noteMapper.updateNote(note);
    }

    public List<Note> findAllNotes(final Integer userId) {
        return noteMapper.findNotes(userId);
    }

    public Integer findUserId(final String username) {
        return userService.findUserId(username);
    }

    public boolean isSuccessfullySaved() {
        return successfullySaved;
    }

    public void setSuccessfullySaved(boolean successfullySaved) {
        this.successfullySaved = successfullySaved;
    }

    public String getNoteTitle(Note note){
        int noteId = note.getNoteId();
        return noteMapper.getNotetile(noteId);
    }

    public int deleteNote(int noteId, int userId) {
//        note.setUserId(userId);
        return noteMapper.delete(noteId,userId);
    }
}
