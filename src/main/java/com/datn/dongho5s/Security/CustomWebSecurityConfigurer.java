package com.datn.dongho5s.Security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface CustomWebSecurityConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
