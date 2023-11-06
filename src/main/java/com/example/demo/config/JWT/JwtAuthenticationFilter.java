package com.example.demo.config.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private static final List<String> EXCLUDE_URL =
            List.of("/",
                    "/sign-up",
                    "/passwordMatch");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String accessToken = jwtProvider.resolveAccessToken(request);
            System.out.println("#### header에서 accessToken 받아옴 = " + accessToken);
            if(StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken)){
                setAuthenticationToContext(accessToken);
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
