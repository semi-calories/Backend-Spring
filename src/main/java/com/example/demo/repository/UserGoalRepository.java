package com.example.demo.repository;

import com.example.demo.domain.UserGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserGoalRepository extends JpaRepository<UserGoal, Long> {

    @Query("select ug from UserGoal ug left join fetch ug.userCode")
    Optional<UserGoal> findAllWithUser(@Param("userCode") Long userCode);
}
