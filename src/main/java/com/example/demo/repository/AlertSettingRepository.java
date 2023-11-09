package com.example.demo.repository;

import com.example.demo.domain.User.Alert.AlertSetting;
import com.example.demo.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AlertSettingRepository extends JpaRepository<AlertSetting, User> {
    /**
     * 조회 by user code
     */
    @Query("select alertSet from AlertSetting alertSet left join fetch alertSet.userCode where  alertSet.userCode.userCode = :userCode ")
    Optional<AlertSetting> findByUserCode(@Param("userCode") Long userCode);
}
