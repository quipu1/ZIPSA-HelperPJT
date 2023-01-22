package com.sparta.zipsa.service.file;

import com.sparta.zipsa.service.file.FileService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Value("${upload.image.location}") //properties에 있는 값 읽기 위한 어노테이션,어디에 저장할지 써놓음
    private String location;

    //디렉토리 생성 메소드
    @Override
    @PostConstruct
    public void constructor() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    //파일 업로드 메소드
    @Override
    public void upload(MultipartFile multipartFile, String filename) {
        try {
            multipartFile.transferTo(new File(location + filename));
        } catch (IOException e) {
            System.out.println("입출력 관련 오류입니다.");
        }
    }
    //파일 삭제
    @Override
    public void deleteFile(String filename) {
        if(!filename.equals("920ba86-defaultImage.jpg")){
            new File(location + filename).delete();
        }
    }
}
