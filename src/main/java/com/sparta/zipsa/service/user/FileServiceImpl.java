package com.sparta.zipsa.service.user;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{

    @Value("${upload.image.location}")
    private String location;


    @Override
    @PostConstruct
    public void constructor() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile multipartFile, String filename) {
        try{
            multipartFile.transferTo(new File(location + filename));
        } catch(IOException e) {
            System.out.println("오류오류오류오류");
        }

    }

    @Override
    public void deleteFile(String filename) {
        new File(location + filename).delete();

    }
}
