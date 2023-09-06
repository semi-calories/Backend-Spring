package com.example.demo.repository;

import com.example.demo.domain.User.Diet.UserSatisfaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSatisfactionRepository extends JpaRepository<UserSatisfaction, Long> {


    @Query("select us from UserSatisfaction us " +
            "left join fetch us.userCode " +
            "left join fetch us.foodCode " +
            "where us.userCode.userCode = :userCode and us.foodCode.foodCode = :foodCode")
    Optional<UserSatisfaction> findByUserCodeAndFoodCode(@Param("userCode") Long userCode, @Param("foodCode") Long foodCode);
}
