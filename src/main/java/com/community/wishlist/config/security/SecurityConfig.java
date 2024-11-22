package com.community.wishlist.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(c -> {
                    c.ignoringRequestMatchers("/**");
                });

        return httpSecurity.build();
    }
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers( "/api/**")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))
//                .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().authenticated();
//                })
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
}
