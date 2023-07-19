package com.datn.dongho5s.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



    @Configuration
    @AllArgsConstructor
    public class SetupSecurity extends WebSecurityConfigurerAdapter {
        private final AuthenticationProvider authenticationProvider;
        private final AccountFilter accountFilter;
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/").authenticated() // Yêu cầu xác thực để truy cập vào trang chủ
                    .antMatchers("/login").permitAll()
                    .antMatchers("/users/**").hasAnyAuthority("ADMIN", "STAFF")
                    .antMatchers("/categories/**").hasAnyAuthority("ADMIN", "STAFF")
                    .antMatchers("/api/giohang/**").hasRole("CUSTOMER")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .permitAll()
                    .and()
                    .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
                    .and()
                    .logout().permitAll()
                    .and()
                    .headers().frameOptions().sameOrigin();
        }
    }

