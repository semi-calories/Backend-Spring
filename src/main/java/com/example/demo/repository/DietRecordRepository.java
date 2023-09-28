package com.example.demo.repository;

import com.example.demo.domain.User.Diet.DietRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
     * 조회 by user code & food code& date
     */
    @Query("select dr from DietRecord dr " +
            "left join fetch dr.userCode uc " + // Added alias for userCode relationship
            "left join fetch dr.foodCode fc " + // Added alias for foodCode relationship
            "where uc.userCode = :userCode and " + // Use alias 'uc' for userCode
            "fc.foodCode = :foodCode and " + // Use alias 'fc' for foodCode
            "dr.eatDate = :dateTime")
    List<DietRecord> findAllByUserCodeAndFoodCodeWithEatDateBetween(@Param("userCode") Long userCode, @Param("foodCode") Long foodCode, @Param("dateTime") LocalDateTime dateTime);

    /**
     * 조회 by user code
     */
    @Query("select dr from DietRecord dr left join fetch dr.userCode where  dr.userCode.userCode = :userCode ")
    List<DietRecord> findAllByUserCode(@Param("userCode") Long userCode);


}
