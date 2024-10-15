package com.sp23.koifishproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/users/login").permitAll()
                .requestMatchers("/api/ponds/**").authenticated()
                .requestMatchers("/api/measurements/**").authenticated()
                .requestMatchers("/api/measure-data/**").authenticated()
                .requestMatchers("/api/units/**").authenticated()
                .requestMatchers("/api/kois/**").authenticated()
                .requestMatchers("/api/feeding-schedules/**").authenticated()
                .requestMatchers("/api/koi-records/**").authenticated()
                .requestMatchers("/api/development-stages/**").authenticated()
                .requestMatchers("/api/orders/**").authenticated()
                .requestMatchers("/api/order-details/**").authenticated()
                .requestMatchers("/api/products/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/users").authenticated() // Phải có quyền admin
                .requestMatchers("/api/users/**").authenticated()  // Yêu cầu JWT cho các endpoint khác
                .anyRequest().authenticated()  // Các yêu cầu khác cần xác thực
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);  // Sử dụng JWT, không cần session


        // Thêm JwtRequestFilter trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
