package com.example.peeppo.global.config;

import com.example.peeppo.global.security.jwt.AuthExceptionFilter;
import com.example.peeppo.global.security.jwt.JwtAuthenticationFilter;
import com.example.peeppo.global.security.jwt.JwtAuthorizationFilter;
import com.example.peeppo.global.security.jwt.JwtUtil;
import com.example.peeppo.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final static String LOGIN_URL = "/api/users/login";

  /*  @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT","OPTIONS","PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.addExposedHeader("*");
        config.setAllowCredentials(true);
       *//* config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("*"); // https://iyk2h.tistory.com/184?category=875351 // 헤더값 보내줄 거 설정.*//*
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }*/

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://localhost:3000", "http://54.180.87.141:8080",  "https://54.180.87.141:8080", "https://54.180.87.141:80","http://54.180.87.141:80", "http://peeppo.store", "https://peeppo.store", "https://apic.app", "chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam", "https://jxy.me", "https://apic.app", "https://peeppo.site", "chrome-extension://ggnhohnkfcpcanfekomdkjffnfcjnjam")); // 이 부분에 출처를 추가합니다.
        config.setAllowedMethods(Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.addExposedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(RedisTemplate redisTemplate) throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, redisTemplate);
        filter.setFilterProcessesUrl(LOGIN_URL);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }


/*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers("/stomp/chat");

        };
    }
*/


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // CSRF 설정
//        http.csrf(AbstractHttpConfigurer::disable);
//        // 기본 설정인 Session 방식이 아닌 JWT 방식을 사용하기 위한 설정
//        http.sessionManagement((sessionManagement) ->
//                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        http
//                .cors(withDefaults())
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                authorizeHttpRequests
//                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/**").permitAll()
//                        .requestMatchers(HttpMethod.DELETE, "/**").permitAll()
//                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
//        );
//
//        http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new AuthExceptionFilter(), JwtAuthorizationFilter.class);
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf(AbstractHttpConfigurer::disable);
        // 기본 설정인 Session 방식이 아닌 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http
//                .cors(withDefaults())
                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                                authorizeHttpRequests
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .requestMatchers(HttpMethod.GET,
                                                "/",
                                                "/api/auction/{auctionId}",
                                                "/api/auction/{auctionId}/bid",
                                                "/api/auction/{auctionId}/bid/page/{page}",
                                                "/api/goods","/api/goods/{goodsId}",
                                                "/api/goods/pocket/{nickname}",
                                                "/api/home",
                                                "/api/chat/**",
                                                "/api/goods/recent",
                                                "/api/goods/search",
                                                "/api/users/login",
                                                "/chat/**",
                                                "/chatroom/**",
                                                "/stomp/chat").permitAll()
                                        .requestMatchers(HttpMethod.POST,
                                                "/api/users/signup",
                                                "/api/users/nickname",
                                                "/api/users/login",
                                                "/api/chat-bot").permitAll()
                                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리

                );
        http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new AuthExceptionFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
//                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(STATELESS))
//                .addFilterBefore(jwtVerificationFilter(), JwtAuthenticationFilter.class)
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(
//                        request -> request
//                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                                .requestMatchers(POST, "/api/pin/**").authenticated()
//                                .requestMatchers(PUT, "/api/**").authenticated()
//                                .requestMatchers(DELETE, "/api/**").authenticated()
//                                .requestMatchers(GET, "/api/v1/oauth2/google", "/login/oauth2/code/google").permitAll()
//                                .requestMatchers(POST, "/api/users/**").permitAll()
//                                .requestMatchers(GET, "/api/users/**", "/api/pin/**").permitAll()
//
//                );
//
//        return http.build();
//    }
}


    // 인증 및 인가가 필요 없음
    // get: /api/auction/**;, /api/auction/{auctionId}/bid, /api/goods, api/goods/{goodsId}, /api/goods/pocket/{nickname}, /api/home
    // post: /api/users/signup, /api/users/nickname

///api/auction
//
///api/auction/{auctionId}
//
//			/api/auction/**
//
// /api/auction/{auctionId}/bid
//
// /api/goods
//
// /api/goods/{goodsId}
//
// /api/goods/pocket/{nickname}
//
// /api/goods/recent
//
// /api/goods/search
//
// /api/home





//      get .. /api/ratings(랜덤 레이팅 받는거)
//      post.. /api/auction/{goodsId}(옥션 생성),  /api/auction/{auctionId}/bid(입찰하기),
//             /api/auction/{auctionId}/choice/bids(경매자가 선호 물품 선택),
//             api/dibs/ ???(찜도 추가해야될까요?) ,  /api/ratings(레이팅)
//      put..  /api/auction/{auctionId}/choice/bids(선호 물품 수정),  /api/goods/{goodsId}(게시글 수정)
//             /api/users/mypage(내 정보 수정)
//      delete.. /api/auction/{auctionId}/pick/{bidId},  /api/auction/{auctionId},
//              /api/goods/{goodsId},
//
//
//       http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(new AuthExceptionFilter(), JwtAuthorizationFilter.class);
//
//        return http.build();
//    }
