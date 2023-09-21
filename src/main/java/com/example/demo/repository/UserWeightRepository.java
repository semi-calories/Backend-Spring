package com.example.demo.repository;

import com.example.demo.domain.User.UserWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UserWeightRepository extends JpaRepository<UserWeight, Long> {

    @Procedure(procedureName = "insert_user_weight") // 프로시저 이름을 지정
    void insertUserWeight(@Param("p_user_code") Long p_user_code, @Param("p_weight") Double p_weight);

}
