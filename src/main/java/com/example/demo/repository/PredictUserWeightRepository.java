package com.example.demo.repository;

import com.example.demo.domain.User.PredictUserWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PredictUserWeightRepository extends JpaRepository<PredictUserWeight, Long> {


    @Modifying
    @Query("delete from PredictUserWeight pdw where pdw.userCode.userCode = :userCode")
    void deleteByUserCode(@Param("userCode") Long userCode);

    @Query("select puw from PredictUserWeight puw left join fetch puw.userCode where puw.userCode.userCode = :userCode  and date(puw.timestamp) = :timestamp ")
    Optional<PredictUserWeight> findByUserCodeAndTimestamp(@Param("userCode") Long userCode, @Param("timestamp") LocalDate timestamp);
}



