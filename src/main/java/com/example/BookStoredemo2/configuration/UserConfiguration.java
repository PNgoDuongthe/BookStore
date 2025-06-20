package com.example.BookStoredemo2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class UserConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/viewdetails", "/addbook", "/availablebook", "/mybook").authenticated()
                        .anyRequest().permitAll());
//                .formLogin(form -> form
//                        .loginProcessingUrl("/homepage")
//                        .defaultSuccessUrl("/homepage",true)
//                        .permitAll()
//                )
//                .exceptionHandling(ex -> ex
//                        .authenticationEntryPoint((request, response, authException) ->
//                                response.sendRedirect("/login")))
//                .sessionManagement(session -> session
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                        .invalidSessionUrl("/login")
//                        .maximumSessions(1)
//                        .expiredUrl("/login?expired")
//                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}