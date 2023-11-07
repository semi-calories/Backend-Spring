package com.example.demo.config.JWT;

import com.example.demo.domain.User.CustomUserDetails;
import com.example.demo.dto.Login.TokenDto;
import com.example.demo.service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtService jwtService;
    public static final String BEARER_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";

    @Getter
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.access-token-expiration-millis}")
    private long accessTokenExpirationMillis;

    @Getter
    @Value("${jwt.refresh-token-expiration-millis}")
    private long refreshTokenExpirationMillis;
    private Key key;


    // Bean 등록후 Key SecretKey HS256 decode
    @PostConstruct
    public void init() {
        String base64EncodedSecretKey = encodeBase64SecretKey(this.secretKey);
        this.key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
    }

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 토큰 생성
     */
    public TokenDto generateToken(CustomUserDetails customUserDetails){
        Date accessTokenExpiresIn = getTokenExpiration(accessTokenExpirationMillis);
        Date refreshTokenExpiresIn = getTokenExpiration(refreshTokenExpirationMillis);

        // Claim = JWT에 들어갈 정보
        // Claim에 loginId를 넣어 나중에 이를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("loginId", customUserDetails.getUsername());

        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보
                .setSubject(customUserDetails.getUsername()) // 제목
                .setExpiration(accessTokenExpiresIn) // 만료일자
                .setIssuedAt(Calendar.getInstance().getTime()) // 발행일자
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key)
                .compact();

        return new TokenDto(BEARER_TYPE, AUTHORIZATION_HEADER,accessToken,refreshToken,accessTokenExpiresIn.getTime());
    }

    // 토큰 만료시간 get
    private Date getTokenExpiration(long expirationMillisecond) {

        Date date = new Date();
        return new Date(date.getTime() + expirationMillisecond);
    }

    // 토큰 남은 시간 get
    public Long getExpiration(String accessToken){
        // access token 남은 시간 get
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();

        // 현재시간 get
        Long now = new Date().getTime();
        return (expiration.getTime()-now);
    }

    /**
     * JWT 토큰을 복호화 해 토큰 정보 반환
     */
    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);

        if(claims.get("loginId") == null){
            throw new IllegalArgumentException("접근 불가 토큰");
        }

        String authority = claims.get("loginId").toString();
        CustomUserDetails customUserDetails = new CustomUserDetails(claims.getSubject() , authority);
        UserDetails userDetails = jwtService.loadUserByUsername(customUserDetails.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities() );
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token){
        try{
            parseClaims(token);
        }catch (MalformedJwtException e){
            log.info("Invalid JWT Token");
            log.trace("Invalid JWT Token trace",e);
        }catch (ExpiredJwtException e){
            log.info("Expired JWT Token");
            log.trace("Expired JWT Token trace",e);
        }catch(UnsupportedJwtException e){
            log.info("Unsupported JWT Token");
            log.trace("Unsupported JWT Token trace",e);
        }catch(IllegalArgumentException e){
            log.info("JWT claims string is empty");
            log.trace("JWT claims string is empty trace",e);
        }
        return true;
    }

    // Token 복호화 및 예외발생시 Claims 객체 생성 x
    // 예외) 토큰 만료, 시그니처 오류 등
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void accessTokenSetHeader(String accessToken, HttpServletResponse response){
        String headerValue = BEARER_PREFIX + accessToken;
        response.setHeader(AUTHORIZATION_HEADER, headerValue);
    }

    public void refreshTokenSetHeader(String refreshToken, HttpServletResponse response){
        response.setHeader("Refresh", refreshToken);
    }

    // request header에 access token 정보 추출
    public String resolveAccessToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }

        return null;
    }

    // request header에 refresh token 정보 추출
    public String resolveRefreshToken(HttpServletRequest request){
        String bearerToken = request.getHeader(REFRESH_HEADER);
        if(StringUtils.hasText(bearerToken)){
            return bearerToken;
        }
        return null;
    }



}
