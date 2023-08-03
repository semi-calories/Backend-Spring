package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.example.demo.dto.User.Request.RequestDislikeSaveDto;
import com.example.demo.dto.User.Request.RequestPreferSaveDto;
import com.example.demo.dto.User.Request.RequestUserGoalUpdateDto;
import com.example.demo.dto.User.Request.RequestUserInfoUpdateDto;
import com.example.demo.dto.User.Response.ResponseUserGoalGetDto;
import com.example.demo.dto.User.Response.ResponseUserInfoGetDto;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final DietService dietService;

    /**
     * 회원 정보 저장 (= 회원 가입)
     */

    /**
     * 로그인
     */

    /**
     * 회원 기본 정보 추가 저장(수정)
     */
    @PostMapping("/updateInfo")
    public ReturnDto updateUserInfo(@RequestBody RequestUserInfoUpdateDto requestInfoUpdateDto) throws Exception{
        Long userCode = userService.userUpdate(requestInfoUpdateDto);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return returnDto;
    }

    /**
     * 회원 목표 정보 추가 저장(수정)
     */
    @PostMapping("/updateGoal")
    public ReturnDto updateUserInfo(@RequestBody RequestUserGoalUpdateDto requestUserGoalUpdateDto) throws Exception{
        Long userCode = userService.userGoalUpdate(requestUserGoalUpdateDto);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return returnDto;
    }

    /**
     * 회원 기본 정보 조회
     */
    @GetMapping("/getInfo")
    public ResponseUserInfoGetDto getInfo(Long userCode) throws Exception {
        User findUser = userService.findOne(userCode);
        ResponseUserInfoGetDto responseUserInfoGetDto = new ResponseUserInfoGetDto(findUser);
        return responseUserInfoGetDto;
    }

    /**
     * 회원 목표 정보 조회
     */
    @GetMapping("/getGoal")
    public ResponseUserGoalGetDto getGoal(Long userCode) throws Exception {
        UserGoal findGoal = userService.findUserWithUserGoal(userCode);
        ResponseUserGoalGetDto responseUserGoalGetDto = new ResponseUserGoalGetDto(findGoal);
        return responseUserGoalGetDto;
    }

    /**
     * 선호 음식 저장
     */
    @PostMapping("/savePrefer")
    public ReturnDto savePrefer(@RequestBody RequestPreferSaveDto requestPreferSaveDto) throws Exception{

        User user = userService.findOne(requestPreferSaveDto.getUserCode());
        Long userCode = dietService.savePreferDiet(user, requestPreferSaveDto);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return  returnDto;
    }

    /**
     * 비선호 음식 저장
     */
    @PostMapping("/saveDislike")
    public ReturnDto savePrefer(@RequestBody RequestDislikeSaveDto requestDislikeSaveDto) throws Exception{

        User user = userService.findOne(requestDislikeSaveDto.getUserCode());
        Long userCode = dietService.saveDislikeDiet(user, requestDislikeSaveDto);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return  returnDto;
    }


    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }
}
