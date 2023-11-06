package com.example.demo.repository;

import com.example.demo.domain.Diet.UserDietDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DislikeDietRepository  extends JpaRepository<UserDietDislike, Long> {

    /**
     * dislike diet 조회 by user code
     */
//    @Query("select udd from UserDietDislike udd left join fetch udd.userCode where udd.userCode.userCode = :userCode")
//    List<UserDietDislike> findByUserCode(@Param("userCode") Long userCode);
    @Query("select udd from UserDietDislike udd left join fetch udd.userCode where udd.userCode.userCode = :userCode")
    List<UserDietDislike> findByUserCode(@Param("userCode") Long userCode);




    @Modifying
    @Query("delete from UserDietDislike udd where udd.userCode.userCode = :userCode")
    void deleteByUserCode(@Param("userCode") Long userCode);
}
