package com.project.LimeRMS.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] allowedUrls = {
            "/",
            "/v3/**",
            "/swagger-ui/**",
            "/api-docs/**",
    }; //권한 없이 모두 접근 가능한 url

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .cors(AbstractHttpConfigurer::disable) //cors disable
            .headers((headerConfig) -> headerConfig
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .csrf(AbstractHttpConfigurer::disable) //csrf protection disable
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/v3/**", "/swagger-ui/**", "/swagger-resources/**", "/api-docs/**").permitAll()
                .anyRequest().permitAll())  // swagger jwt 추가 후 denyAll로 변경 필요
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//            .formLogin(formLogin -> formLogin
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/main"))
//            .logout((logoutConfig) -> logoutConfig
//                    .logoutUrl("/logout") //default값이 logout
//                    .logoutSuccessUrl("/")); //JWT와 같이 세션을 사용하지 않는 경우 사용
            //exceptionHandling을 추가해도 좋을 것 같다
        return httpSecurity.build();
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
