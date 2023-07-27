package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="LogmealApiFeign", url="https://api.logmeal.es")
public interface LogmealApiFeign {

    /**
     *  test: Get list of accessible services (endpoints)
     */
    @GetMapping("/v2/info/services")
    public Object getService(
            @RequestHeader(name = "Authorization") String auth
    );
}
