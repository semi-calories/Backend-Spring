package com.example.demo.controller;

import com.example.demo.feign.FastApiFeign;
import com.example.demo.feign.LogmealApiFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class testController {

    private final FastApiFeign fastApiFeign;
    private final LogmealApiFeign logmealApiFeign;

    @GetMapping("/api")
    public void test(){
        log.info("home api");
        System.out.println(fastApiFeign.test());

        log.info("home");
    }

    @GetMapping("/logmeal-test")
    public Object logmeal(){
        Object logmeal = logmealApiFeign.getService("Bearer f45c34959ac63312f2efeb272f3ec28f4d75a46e");
        log.info("logmeal return 값 = {}", logmeal);
        return logmeal;
    }


}
