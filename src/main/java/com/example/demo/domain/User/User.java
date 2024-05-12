package com.example.demo.domain.User;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "User")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_code")
    private Long userCode;

    @Column(name= "user_email")
    private String email;

    @Column(name= "user_name")
    private String name;

    @Column(name= "user_age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name= "user_gender")
    private Gender gender;


    @Column(name= "user_image")
    private String image;

    @Column(name= "user_height")
    private Double height;

    @Column(name= "user_weight")
    private Double weight;


    //==생성자==//
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    //==비즈니스 변경 로직==//
    public void change(String name, String email, String image,int age, Gender gender, double height, double weight){
        this.name = name;
        this.email = email;
        this.image = image;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public void changeWeight( double weight){
        this.weight = weight;
    }


}
