package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

    private Integer fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(Integer fileId, String fileName, String contentType, Long fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public File(){}

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
        this.fileName = fileName;
    }

    public String getContenttype() {
        return contentType;
    }

    public void setContenttype(String contentType) {
        this.contentType = contentType;
    }

    public Long getFilesize() {
        return fileSize;
    }

    public void setFilesize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getUserid() {
        return userId;
    }

    public void setUserid(Integer userId) {
        this.userId = userId;
    }

    public byte[] getFiledata() {
        return fileData;
    }

    public void setFiledata(byte[] fileData) {
        this.fileData = fileData;
    }
}