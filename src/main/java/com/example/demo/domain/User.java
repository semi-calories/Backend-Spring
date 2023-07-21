package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import javax.annotation.processing.Generated;
import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="user_code")
    private Long userCode;

    @Column(name= "user_email")
    private String email;

    @Column(name= "user_name")
    private String name;

    @Column(name= "user_nickname")
    private String nickname;

    @Column(name= "user_age")
    private int age;

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

}
