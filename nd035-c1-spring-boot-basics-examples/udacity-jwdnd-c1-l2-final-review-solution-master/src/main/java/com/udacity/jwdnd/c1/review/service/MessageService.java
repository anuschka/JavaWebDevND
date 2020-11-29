package com.udacity.jwdnd.c1.review.service;

import com.udacity.jwdnd.c1.review.mapper.ChatMessageMapper;
import com.udacity.jwdnd.c1.review.model.ChatForm;
import com.udacity.jwdnd.c1.review.model.ChatMessage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    //we replaced the in-memory list with a dependency on a message mapper class
    private ChatMessageMapper chatMessageMapper;

    public MessageService(ChatMessageMapper chatMessageMapper) {
        this.chatMessageMapper = chatMessageMapper;
    }


    //private List<ChatMessage> chatMessages;
    //private String message;

    //public MessageService(String message) {
    //    this.message = message;
    //}

    //public String uppercase() {
    //    return this.message.toUpperCase();
    //}

    //public String lowercase() {
    //    return this.message.toLowerCase();
    //}

    @PostConstruct
    public void postConstruct() {

        System.out.println("Creating MessageService bean");
        //this.chatMessages = new ArrayList<>();
    }



    public void addMessage(ChatForm chatForm) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setUsername(chatForm.getUsername());
        switch (chatForm.getMessageType()){
            case "Say":
                newMessage.setMessagetext(chatForm.getMessageText());
                break;
            case "Shout":
                newMessage.setMessagetext(chatForm.getMessageText().toUpperCase());
                break;
            case "Whisper":
                newMessage.setMessagetext(chatForm.getMessageText().toLowerCase());
                break;
        }
        chatMessageMapper.addMessage(newMessage);
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessageMapper.getAllMessages();
    }
}
