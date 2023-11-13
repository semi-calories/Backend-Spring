package com.example.demo.repository;

import com.example.demo.domain.User.Alert.AlertRecord;
import com.example.demo.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AlertRecordRepository extends JpaRepository<AlertRecord, User> {
    /**
     * 조회 by user code & date
     */
    //@Query("select ar from AlertRecord as left join fetch ar.userCode where  ar.userCode.userCode = :userCode and ar.alertDate between :startDatetime and :endDatetime")
    //Optional<AlertRecord> findByUserWithAlertDateBetween(@Param("userCode") User userCode, @Param("startDatetime") LocalDateTime startDatetime, @Param("endDatetime") LocalDateTime endDatetime);

    /**
     * 조회 by alert_status & date
     */
    @Query("select ar from AlertRecord ar where ar.alertStatus = :alertStatus and ar.alertDate = :nowDatetime")
    List<AlertRecord> findAllByAlertStatusAndAlertDate(@Param("alertStatus") boolean alertStatus, @Param("nowDatetime") String nowDatetime);

    /**
     * 조회 by user code & alert_status & date
     */
    @Query("select ar from AlertRecord ar left join fetch ar.userCode where  ar.userCode.userCode = :userCode and ar.alertStatus = :alertStatus and ar.alertDate between :startDatetime and :endDatetime ")
    List<AlertRecord> findAllByUserCodeAndAlertStatusWithAlertDateBetween(@Param("userCode") Long userCode, @Param("alertStatus") boolean alertStatus, @Param("startDatetime") String startDatetime,
                                                                   @Param("endDatetime") String endDatetime);
}