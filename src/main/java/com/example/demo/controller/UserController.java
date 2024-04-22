package com.example.demo.controller;

import com.example.demo.domain.Diet.UserDietDislike;
import com.example.demo.domain.Diet.UserDietPrefer;
import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserGoal;
import com.example.demo.dto.User.Request.RequestPreferenceSaveDto;
import com.example.demo.dto.User.Request.RequestUserUpdateDto;
import com.example.demo.dto.User.Response.ResponseUserGetDto;
import com.example.demo.service.DietService;
import com.example.demo.service.LoginService;
import com.example.demo.service.S3UploadService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final DietService dietService;
    private final LoginService loginService;
    private final S3UploadService s3UploadService;


    /**
     * 회원 정보 추가 저장(수정)
     */
    @PostMapping("/updateInfo")
    public ReturnDto updateUserInfo(@ModelAttribute RequestUserUpdateDto requestInfoUpdateDto){

        // 유저 이미지 저장
        String imageUrl = s3UploadService.upload(requestInfoUpdateDto.getImage(), requestInfoUpdateDto.getUserCode().toString());

        // 유저 정보 수정
        userService.userUpdate(requestInfoUpdateDto, imageUrl);

        // 유저 이메일 수정
        loginService.updateEmail(requestInfoUpdateDto.getUserCode(), requestInfoUpdateDto.getEmail());

        // 유저 목표 수정
        userService.userGoalUpdate(requestInfoUpdateDto);

        // 헤리스 베네딕트 수정
        userService.changeHarrisBenedict(requestInfoUpdateDto.getUserCode(), requestInfoUpdateDto.getWeight()   );

        // 유저 예상 몸무게 추이 저장
        userService.savePredictWeight(requestInfoUpdateDto.getUserCode(),requestInfoUpdateDto.getPeriod());

        return new ReturnDto<>(requestInfoUpdateDto.getUserCode());
    }



    /**
     * 회원 정보 조회
     */
    @GetMapping("/getInfo")
    public ResponseUserGetDto getUserInfo(Long userCode){

        // 기본 정보 조회
        User findUser = userService.findOne(userCode);

        // 목표 정보 조회
        UserGoal findGoal = userService.findUserWithUserGoal(userCode);

        // 응답 DTO 생성
        return new ResponseUserGetDto(findUser, findGoal);
    }





    /**
     * 선호 음식 저장
     */
    @PostMapping("/savePrefer")
    public ReturnDto savePrefer(@RequestBody RequestPreferenceSaveDto requestPreferSaveDto){

        // 유저 찾기
        User user = userService.findOne(requestPreferSaveDto.getUserCode());

        // 선호 음식 저장
        dietService.savePreferDiet(user, requestPreferSaveDto, true);

        return new ReturnDto<>(true);
    }

    /**
     * 선호 음식 조회
     */
    @GetMapping("/getPrefer")
    public ReturnDto getPrefer(Long userCode){

        // 선호음식 조회
        List<UserDietPrefer> preferDiet = dietService.getPreferDiet(userCode);

        ReturnDto<List<UserDietPrefer>> returnDto = new ReturnDto<>(preferDiet);
        return  returnDto;

    }


    /**
     * 비선호 음식 저장
     */
    @PostMapping("/saveDislike")
    public ReturnDto saveDislike(@RequestBody RequestPreferenceSaveDto requestDislikeSaveDto){

        // 유저 찾기
        User user = userService.findOne(requestDislikeSaveDto.getUserCode());

        // 비선호음식 저장
        Long userCode = dietService.saveDislikeDiet(user, requestDislikeSaveDto, false);

        return new ReturnDto<>(true);
    }

    /**
     * 비선호 음식 조회
     */
    @GetMapping("/getDislike")
    public ReturnDto getDislike(Long userCode){

        // 비선호음식 조회
        List<UserDietDislike> dislikeDiet = dietService.getDislikeDiet(userCode);

        ReturnDto<List<UserDietDislike>> returnDto = new ReturnDto<>(dislikeDiet);
        return  returnDto;

    }



    @AllArgsConstructor
    @Getter
    static class ReturnDto<T>{
        private T response;
    }
}
