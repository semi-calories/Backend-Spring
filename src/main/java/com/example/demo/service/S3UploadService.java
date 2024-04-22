package com.example.demo.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // multipart file을 전달받아 s3 업로드
    // 동일 유저 사진은 overwrite
    public String upload(MultipartFile multipartFile, String fileName) {
        String s3FileName = fileName+".jpg";

        ObjectMetadata objMeta = new ObjectMetadata();
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
            objMeta.setContentLength(inputStream.available());
        } catch (IOException e) {
            log.info("S3 업로드 실패",e);
        }

        amazonS3.putObject(bucket, s3FileName, inputStream, objMeta);


        return amazonS3.getUrl(bucket, s3FileName).toString();
    }




}
