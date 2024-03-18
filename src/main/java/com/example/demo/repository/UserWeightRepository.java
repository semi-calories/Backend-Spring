package com.example.demo.repository;

import com.example.demo.domain.User.User;
import com.example.demo.domain.User.UserWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserWeightRepository extends JpaRepository<UserWeight, Long> {

    @Query("select uw from UserWeight uw left join fetch uw.userCode where  uw.userCode.userCode = :userCode and uw.timestamp between :startDatetime and :endDatetime order by uw.timestamp")
    List<UserWeight> findByUserCodeWithDateBetween(@Param("userCode") Long userCode, @Param("startDatetime") LocalDateTime startDatetime, @Param("endDatetime") LocalDateTime endDatetime);


    Optional<UserWeight> findTopByUserCodeOrderByTimestampDesc(@Param("userCode") User user);
}
