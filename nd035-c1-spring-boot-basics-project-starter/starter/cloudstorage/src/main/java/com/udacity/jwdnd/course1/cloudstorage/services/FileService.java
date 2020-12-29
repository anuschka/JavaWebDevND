package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    private boolean successfullySaved;

    public FileService(final FileMapper fileMapper, final UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<File> filesUpload(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public boolean filenameExists(String originalFilename, Integer userId) {
        return !fileMapper.getByUsername(originalFilename, userId).isEmpty();
    }

    public void storeInDB(MultipartFile fileUpload, Integer userId) throws IOException {
        File file = new File();
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContenttype(fileUpload.getContentType());
        file.setFilesize(fileUpload.getSize());
        file.setFiledata(fileUpload.getBytes());
        file.setUserid(userId);
        fileMapper.insertFile(file);
    }

    public File findByFileId(Integer fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public void deleteFile(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
