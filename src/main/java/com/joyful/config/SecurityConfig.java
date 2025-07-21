package com.joyful.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for non-browser clients like Postman
            .cors(Customizer.withDefaults()) // Enable CORS (optional but useful)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Allow everything
            );

        return http.build();
    }
}
//package com.joyful.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable()) // Disable CSRF for non-browser clients like Postman
//            .cors(Customizer.withDefaults()) // Enable CORS (optional but useful)
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/**").permitAll() // Allow everything
//            );
//
//        return http.build();
//    }
//}