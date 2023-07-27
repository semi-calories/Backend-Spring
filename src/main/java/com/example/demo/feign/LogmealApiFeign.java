package com.example.demo.feign;

import com.example.demo.dto.Recognizer.Logmeal.ResponseDto.ResponseLogmealDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name="LogmealApiFeign", url="https://api.logmeal.es")
public interface LogmealApiFeign {

    /**
     *  test: Get list of accessible services (endpoints)
     */
    @GetMapping("/v2/info/services")
    public Object getService(
            @RequestHeader(name = "Authorization") String auth
    );

    @PostMapping(value = "/v2/image/segmentation/complete/v1.0", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseLogmealDto getFoodName(
            @RequestHeader(name = "Authorization") String auth,
            @RequestParam(name="language") String lang,
            @RequestPart(name="image") MultipartFile file
    );
}
