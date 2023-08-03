package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import javax.annotation.processing.Generated;
import java.io.Serializable;

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
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name= "user_gender")
    private Gender gender;

    @Column(name= "user_phone")
    private String phone;

    @Column(name= "user_image")
    private byte[] image;

    @Column(name= "user_height")
    private double height;

    @Column(name= "user_weight")
    private double weight;


    //==비즈니스 로직==//
    public void change(String name, String email, int age, Gender gender, String phone, double height, double weight){
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.height = height;
        this.weight = weight;
    }
}
