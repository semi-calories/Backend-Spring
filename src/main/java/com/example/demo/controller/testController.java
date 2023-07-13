package com.example.demo.controller;

import com.example.demo.dto.TestDto;
import com.example.demo.feign.FastApiFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

    private final FastApiFeign fastApiFeign;

    @GetMapping("/api")
    public void test(){
        int result = fastApiFeign.test();
        System.out.println(result);
        log.info("home");
    }


}
