package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(FileService fileService, UserService userService) {

        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/file-upload")
    public String handleFileUploadAndEdit(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication ){
        if(fileUpload.isEmpty()){
            System.out.println("Empty file");
            model.addAttribute("error", true);
            model.addAttribute("message", "Empty file selected or there is no file which is selected");
            return "result";
        }
        User user = this.userService.findUser(authentication.getName());
        Integer userId = user.getUserId();

        if(fileService.filenameExists(fileUpload.getOriginalFilename(), userId)) {
            model.addAttribute("error",true);
            model.addAttribute("message","File with that name already exists");
            return "result";
        }
        try {
            fileService.storeInDB(fileUpload, userId);
            model.addAttribute("success",true);
            model.addAttribute("message","New File added successfully!");
        }catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }

        return "result";
    }



}
