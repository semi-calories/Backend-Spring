package com.example.demo.repository;

import com.example.demo.domain.User.Diet.UserDietPrefer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreferDietRepository extends JpaRepository<UserDietPrefer, Long> {

    /**
     * prefer diet 조회 by user code
     */
    @Query("select udp from UserDietPrefer udp left join fetch udp.userCode where udp.userCode.userCode = :userCode")
    List<UserDietPrefer> findByUserCode(@Param("userCode") Long userCode);

    @Modifying
    @Query("delete from UserDietPrefer udp where udp.userCode.userCode = :userCode")
    void deleteByUserCode(@Param("userCode") Long userCode);
}
