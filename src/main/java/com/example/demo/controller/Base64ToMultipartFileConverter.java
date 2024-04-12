package com.example.demo.controller;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

// @Component
public class Base64ToMultipartFileConverter {

    public static MultipartFile getMultipartFile(String file, String fileName) {

        try {
            // Base64 디코딩
            byte[] decodedBytes = Base64.getDecoder().decode(file);

            // 파일로 저장
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(decodedBytes);
            }

            // 파일을 MultipartFile로 변환
            File testFile = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(testFile);



            MultipartFile multipartFile = new MockMultipartFile(
                    fileName,           // 파일 이름
                    fileName,           // 오리지널 파일 이름
                    "image/png",       // 컨텐츠 타입
                    fileInputStream);
            return multipartFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
