package com.example.demo.controller;

import com.example.demo.feign.FastApiFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

//    private final FastApiFeign fastApiFeign;
//
//    @GetMapping("/api")
//    public void test(){
//        log.info("home api");
//        System.out.println(fastApiFeign.test());
//
//        log.info("home");
//    }
//


    @GetMapping("/test")
    public int test(){
        System.out.println("#3333333333 test 받음 ############");
        return 2023;
    }

}
