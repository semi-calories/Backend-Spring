package com.example.demo.controller;

import com.example.demo.feign.FastApiFeign;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

    private final FastApiFeign fastApiFeign;

    @Retryable(maxAttempts = 3)
    @GetMapping("/api")
    public String testFAST(){
        log.info("####################### home api");
        System.out.println(fastApiFeign.test());

        log.info("###################### home");
        return "연결 성공";
    }



    @GetMapping("/test")
    public int test(){
        return 2023;
    }

}
