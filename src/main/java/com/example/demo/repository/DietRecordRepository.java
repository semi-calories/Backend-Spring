package com.example.demo.repository;

import com.example.demo.domain.Diet.DietRecord;
import com.example.demo.domain.Diet.UserDietPrefer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DietRecordRepository extends JpaRepository<DietRecord, Long> {



    /**
     * 조회 by user code & date
     */
    @Query("select dr from DietRecord dr left join fetch dr.userCode where  dr.userCode.userCode = :userCode and dr.eatDate between :startDatetime and :endDatetime")
    List<DietRecord> findAllByUserCodeWithEatDateBetween(@Param("userCode") Long userCode, @Param("startDatetime") LocalDateTime startDatetime,@Param("endDatetime") LocalDateTime endDatetime);

    /**
     * 조회 by user code
     */
    @Query("select dr from DietRecord dr left join fetch dr.userCode where  dr.userCode.userCode = :userCode ")
    List<DietRecord> findAllByUserCode(@Param("userCode") Long userCode);

}
