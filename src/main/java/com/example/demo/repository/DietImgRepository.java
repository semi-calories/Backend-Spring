package com.example.demo.repository;

import com.example.demo.domain.DB.DietImg;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User.Diet.DietRecord;
import com.example.demo.domain.User.Diet.UserDietDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DietImgRepository extends JpaRepository<DietImg, Long> {
    // Optional<List<DietImg>> findByFoodMainCategory(String foodMainCategory);

    //추천 음식명, 사진, 칼로리, 탄, 단, 지

    //Optional<List<DietList>> findByFoodMainCategory(String foodName);

    /**
     * diet img 조회 by food MainCategory
     */

    Optional<DietImg> findByMainCategory(@Param("foodMainCategory") String foodMainCategory);

    //findAllBy와 findBy?
    //left join fetch
    //:userCode?
    //dr.userCode.userCode
}

