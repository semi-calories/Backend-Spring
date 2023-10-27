package com.example.demo.repository;

import com.example.demo.domain.User.PredictUserWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PredictUserWeightRepository extends JpaRepository<PredictUserWeight, Long> {


    @Modifying
    @Query("delete from PredictUserWeight pdw where pdw.userCode.userCode = :userCode")
    void deleteByUserCode(@Param("userCode") Long userCode);

    @Query("select count (pdw.id) from PredictUserWeight pdw where pdw.userCode.userCode = :userCode and pdw.predictWeight = :predictWeight")
    Long countByUserCondAndPredictWeight(@Param("userCode") Long userCode, @Param("predictWeight") Double predictWeight);

    @Query("select puw from PredictUserWeight puw left join fetch puw.userCode where puw.userCode.userCode = :userCode")
    List<PredictUserWeight> findByUserCode(@Param("userCode") Long userCode);
}
