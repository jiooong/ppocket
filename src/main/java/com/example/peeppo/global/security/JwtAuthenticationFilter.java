package com.example.peeppo.global.security;

import com.example.peeppo.domain.user.dto.LoginRequestDto;
import com.example.peeppo.domain.user.entity.UserRoleEnum;
import com.example.peeppo.global.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("request uri: {}", request.getRequestURI());

        try {
             LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                   requestDto.getEmail(), //수정
                   requestDto.getPassword()
            );
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        Long userId = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getUserId();

        String accessToken = jwtUtil.createAccessToken(username, role);
        response.addHeader(JwtUtil.ACCESS_TOKEN, accessToken);

        String refreshToken = jwtUtil.createRefreshToken(username);
        response.addHeader(JwtUtil.REFRESH_TOKEN, refreshToken);
        // redis에 저장
//        if(redisService.getRefreshToken(userId) == null) {
//            redisService.setRefreshToken(new RefreshToken(refreshToken, userId));
//        }

        response.setStatus(200);
        new ObjectMapper().writeValue(response.getOutputStream(), ("로그인 성공"));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(400);
        new ObjectMapper().writeValue(response.getOutputStream(), ("아이디와 비밀번호를 한번 더 확인해 주세요"));
    }
}