package com.example.demo.domain.User;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


//@NamedStoredProcedureQuery(
//        name = "UserWeight.inserUserWeight",
//        procedureName = "insert_user_weight",
//        resultClasses = UserWeight.class,
//        parameters = {
//                @StoredProcedureParameter(name = "p_user_code", type = Long.class, mode = ParameterMode.IN),
//                @StoredProcedureParameter(name = "p_weight", type = Double.class, mode = ParameterMode.IN),
//                @StoredProcedureParameter(name = "RESULT", type = Integer.class, mode = ParameterMode.OUT),
//        }
//)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "User_weight")
public class UserWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_weight_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User userCode;

    private Double weight;

    private LocalDateTime timestamp;

    public UserWeight(User userCode, Double weight) {
        this.userCode = userCode;
        this.weight = weight;
        this.timestamp = LocalDateTime.now();
    }
}
