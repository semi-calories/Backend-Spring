package com.example.demo.controller;

import com.example.demo.feign.LogmealApiFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recognizer")
public class FoodRecognizerController {

    private final LogmealApiFeign logmealApiFeign;

    /**
     * 음식 이미지 받는 API
     */
    @PostMapping(name="/receive-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object receiveImage(@RequestPart(value="image") MultipartFile imgFile){
        System.out.println("수신");
        log.info("이미지={}",String.valueOf(imgFile));
        return imgFile;
    }

    @PostMapping("/upload")
    public ResponseEntity upload(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("image");

        String originalFileName = file.getOriginalFilename();
//        File destination = new File("upload/dir" + originalFileName);
//        file.transferTo(destination);

        return ResponseEntity.status(HttpStatus.CREATED).body(originalFileName);
    }

    @PostMapping("/test")
    public String test(){
        System.out.println("접속");
        return "ok";
    }
}
