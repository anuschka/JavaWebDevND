package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class FileController implements HandlerExceptionResolver {
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

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> handleFileDownload(@PathVariable("fileId") Integer fileId) {

        File file = fileService.findByFileId(fileId);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFilename());
        header.add("Cache-control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource((file.getFiledata()));
        return ResponseEntity.ok()
                .headers(header)
                .body(resource);
    }


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView("result");
        if (e instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("error", true);
            modelAndView.getModel().put("message", "Uploaded file size exceeds limit!");
        }
        return modelAndView;
    }

    @GetMapping("/deleteFile/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        try{
            this.fileService.deleteFile(fileId);
            model.addAttribute("success",true);
            model.addAttribute("message", "File deleted successfully!");
        }catch(Exception e){
            model.addAttribute("error",true);
            model.addAttribute("message", "Error is : " + e.getMessage());
        }
        return "result";
    }
}
