package com.example.demo.domain.User.Alert;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.DB.DietList;
import com.example.demo.domain.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "Alert_record")
public class AlertRecord extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="alert_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    @JsonIgnore
    private User userCode;

    @Column(name="user_token")
    private String userToken;

    @Column(name="alert_date")
    private LocalDateTime alertDate;
    @Column(name="alert_hour")
    private int alertHour;
    @Column(name="alert_minute")
    private int alertMinute;

    @Column(name="food_times")
    private int foodTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code")
    @JsonIgnore
    private DietList foodCode;

    @Column(name="food_name")
    private String foodName;

    @Column(name="food_kcal")
    private Double foodKcal;

    @Column(name="food_carbo")
    private Double foodCarbo;

    @Column(name="food_protein")
    private Double foodProtein;

    @Column(name="food_fat")
    private Double foodFat;

    @Column(name="alert_status")
    private int alertStatus;

    @Column(name="reg_dtm")
    private LocalDateTime regDtm;
    @Column(name="send_dtm")
    private LocalDateTime sendDtm;

    //==생성자==//
    public AlertRecord(User userCode, String userToken, LocalDateTime alertDate, int alertHour, int alertMinute, int foodTimes,
                       DietList foodCode, String foodName, Double foodKcal, Double foodCarbo, Double foodProtein, Double foodFat){
        this.userCode = userCode;
        this.userToken = userToken;
        this.alertDate = alertDate;
        this.alertHour = alertHour;
        this.alertMinute = alertMinute;
        this.foodTimes = foodTimes;
        this.foodCode = foodCode;
        this.foodName = foodName;
        this.foodKcal = foodKcal;
        this.foodCarbo = foodCarbo;
        this.foodProtein = foodProtein;
        this.foodFat = foodFat;
    }

    //==비즈니스 로직==//

    // 푸시 알람 발송 시간 변경
    public void changeTime(int alertHour, int alertMinute){
        this.alertHour = alertHour;
        this.alertMinute = alertMinute;
    }

    // 푸시 알람 발송 이후
    public void changeDtm(int alertStatus, LocalDateTime regDtm, LocalDateTime sendDtm){
        this.alertStatus = alertStatus;
        this.regDtm = regDtm;
        this.sendDtm = sendDtm;
    }
}
