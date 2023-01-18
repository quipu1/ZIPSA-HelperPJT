package com.sparta.zipsa.service.user;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public void constructor();

    public void upload(MultipartFile multipartFile, String filename);

    public void deleteFile(String filename);


}




