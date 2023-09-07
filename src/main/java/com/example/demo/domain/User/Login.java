package com.example.demo.domain.User;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Table(schema = "Login")
public class Login {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private Long id;

    @OneToOne
    @JoinColumn(name="user_code")
    private User userCode;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="user_password")
    private String userPassword;

    @Column(name="user_token")
    private String userToken;

    //==생성자==//

    public Login(User userCode, String userEmail, String userPassword, String userToken) {
        this.userCode = userCode;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userToken = userToken;
    }


    //==암호화 로직==//
    /**
     * 비밀번호 암호화
     */
    public String passwordEncode(String password, PasswordEncoder passwordEncoder){
        this.userPassword = passwordEncoder.encode(password);
        return this.userPassword;
    }

    /**
     * 비밀번호 변경
     */
    public String changePassword(String newPw){
        this.userPassword = newPw;
        return this.userPassword;
    }
}
