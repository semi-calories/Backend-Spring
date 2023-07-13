package com.example.demo.feign;

import com.example.demo.dto.TestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="FastApiFeign", url="http://localhost:8000")
public interface FastApiFeign {

    /**
     * test 연결
     */
    @GetMapping("/test")
    public int test();

}