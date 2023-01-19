package com.sparta.zipsa.service.user;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void constructor();

    void upload(MultipartFile multipartFile, String filename);

    void deleteFile(String filename);


}




