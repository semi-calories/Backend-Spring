package com.example.demo.repository;

import com.example.demo.domain.User.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    Long countByUserEmail(String userEmail);

    @Query("select l from Login l left join fetch l.userCode where l.userCode.userCode = :userCode")
    Optional<Login> findByUserCode(@Param("userCode") Long userCode);

    Optional<Login> findByUserEmail(String userEmail);

    @Modifying
    @Query("update Login l set l.userToken = :refreshToken where l.userCode.userCode = :userCode")
    void bulkModifyingByUserCode(@Param("userCode") Long userCode, @Param("refreshToken") String refreshToken);
    
}
