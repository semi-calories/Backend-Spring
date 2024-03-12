package com.example.demo.config.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String > redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            // refresh token 만료 확인
            if (request.getHeader("Refresh") != null && !request.getHeader("Refresh").isEmpty()) {
                String refreshToken = jwtProvider.resolveRefreshToken(request);
                jwtProvider.validateRefreshToken(refreshToken);
            }

            // access token 확인
            String accessToken = jwtProvider.resolveAccessToken(request);
            if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken)) {

                String isLogout = redisTemplate.opsForValue().get(accessToken);

                if (ObjectUtils.isEmpty(isLogout)) {
                    setAuthenticationToContext(accessToken);
                }
            }

            // 예외처리
        }catch(ExpiredJwtException e){
            log.info("access 토큰 만료 에러 발생");
            jwtExceptionHandler(response);

            return;
        }catch(IllegalArgumentException e){
            log.info("refresh 토큰 만료 에러 발생");
            jwtExceptionHandler(response);

            return;
        }catch(RuntimeException e){
            log.info("token 저장 에러 발생", e);
        }

        filterChain.doFilter(request, response);

    }

    private void jwtExceptionHandler(HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        String jsonResponse = "{ \"error\": \"Unauthorized\", \"message\": \"" + "토큰 만료" + "\" }";
        response.getWriter().write(jsonResponse);

    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("## token verification success! security context에 인증정보 저장");
    }

}
