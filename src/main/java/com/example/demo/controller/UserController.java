package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.UserGoal;
import com.example.demo.dto.User.Request.*;
import com.example.demo.dto.User.Response.ResponseUserGetDto;
import com.example.demo.service.DietService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
     * 회원 정보 추가 저장(수정)
     */
    @PostMapping("/updateInfo")
    public ReturnDto updateUserInfo(@RequestBody RequestUserUpdateDto requestInfoUpdateDto) throws Exception{

        // 유저 수정
        Long userCode = userService.userUpdate(requestInfoUpdateDto);

        // 유저 목표 수정
        userService.userGoalUpdate(requestInfoUpdateDto);

        // 헤리스 베네딕트 수정
        userService.changeHarrisBenedict(requestInfoUpdateDto.getUserCode());

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return returnDto;
    }


    /**
     * 회원 정보 조회
     */
    @GetMapping("/getInfo")
    public ResponseUserGetDto getInfo(Long userCode) throws Exception {

        // 기본 정보 조회
        User findUser = userService.findOne(userCode);

        // 목표 정보 조회
        UserGoal findGoal = userService.findUserWithUserGoal(userCode);

        // 응답 DTO 생성
        ResponseUserGetDto responseUserInfoGetDto = new ResponseUserGetDto(findUser, findGoal);
        return responseUserInfoGetDto;
    }

    /**
     * 선호 음식 저장
     */
    @PostMapping("/savePrefer")
    public ReturnDto savePrefer(@RequestBody RequestPreferenceSaveDto requestPreferSaveDto) throws Exception{

        // 유저 찾기
        User user = userService.findOne(requestPreferSaveDto.getUserCode());

        // 선호 음식 저장
        Long userCode = dietService.savePreferDiet(user, requestPreferSaveDto, true);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return  returnDto;
    }

    /**
     * 비선호 음식 저장
     */
    @PostMapping("/saveDislike")
    public ReturnDto saveDislike(@RequestBody RequestPreferenceSaveDto requestDislikeSaveDto) throws Exception{

        // 유저 찾기
        User user = userService.findOne(requestDislikeSaveDto.getUserCode());

        // 비선호음식 저장
        Long userCode = dietService.saveDislikeDiet(user, requestDislikeSaveDto, false);

        ReturnDto<Long> returnDto = new ReturnDto<>(userCode);
        return  returnDto;
    }


    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }
}
