package com.sparta.zipsa.config;


import com.sparta.zipsa.jwt.JwtAccessDeniedHandler;
import com.sparta.zipsa.jwt.JwtAuthFilter;
import com.sparta.zipsa.jwt.JwtAuthenticationEntryPoint;
import com.sparta.zipsa.jwt.JwtUtil;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 세션을 사용하는 방식이기 때문에 csrf를 disable한다.

        http.headers().frameOptions().disable(); // 해당 페이지를 <frame> 또는<iframe>, <object> 에서 렌더링할 수 있는지 여부

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션이 필요하면 생성하도록 셋팅

        http.exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.authorizeHttpRequests()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/user/**").permitAll()
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class); // JWT 인증/인가를 사용하기 위한 설정

        return http.build();
    }
}