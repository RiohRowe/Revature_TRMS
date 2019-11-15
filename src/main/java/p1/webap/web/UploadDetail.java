package p1.webap.web;

import java.io.Serializable;

public class UploadDetail implements Serializable
{
    private long fileSize;
    private String fileName;
    private String uploadStatus;
 
    private static final long serialVersionUID = 1L;
 
    public long getFileSize() {
        return fileSize;
    }
 
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public String getUploadStatus() {
        return uploadStatus;
    }
 
    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
}

