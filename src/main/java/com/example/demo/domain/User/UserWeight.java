package com.example.demo.domain.User;

import com.example.demo.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "User_weight")
public class UserWeight extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_weight_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    @JsonIgnore
    private User userCode;

    private Double weight;

    private LocalDateTime timestamp;

    public UserWeight(User userCode, Double weight) {
        this.userCode = userCode;
        this.weight = weight;
        this.timestamp = LocalDateTime.now();
    }

    public UserWeight(User userCode, Double weight, LocalDateTime dateTime) {
        this.userCode = userCode;
        this.weight = weight;
        this.timestamp = dateTime;
    }

    //==비즈니스 로직==//
    public void change(Double weight){
        this.weight = weight;
    }
}
