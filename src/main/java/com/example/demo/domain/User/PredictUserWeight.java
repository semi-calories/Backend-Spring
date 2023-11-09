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
@Table(schema = "Predict_user_weight")
public class PredictUserWeight extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="predict_user_weight_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    @JsonIgnore
    private User userCode;

    @Column(name="predict_weight")
    private Double predictWeight;

    private LocalDateTime timestamp;

    public PredictUserWeight(User userCode, Double predictWeight, LocalDateTime timestamp) {
        this.userCode = userCode;
        this.predictWeight = predictWeight;
        this.timestamp = timestamp;
    }
}
