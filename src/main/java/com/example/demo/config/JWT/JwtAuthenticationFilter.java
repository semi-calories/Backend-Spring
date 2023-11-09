package com.example.demo.config.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
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

        try{
            String accessToken = jwtProvider.resolveAccessToken(request);
            if(StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken)){

                String isLogout = redisTemplate.opsForValue().get(accessToken);

                if(ObjectUtils.isEmpty(isLogout)){
                    setAuthenticationToContext(accessToken);
                }
            }
            // TODO 예외처리 리팩토링
        }catch(RuntimeException e){
            log.info("token 저장 에러 발생", e);
        }

        filterChain.doFilter(request, response);

    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("## token verification success!");
    }

}
