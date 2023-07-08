package com.datn.dongho5s.Security;

import com.datn.dongho5s.Service.impl.KhachHangDetailsServiceImpl;
import com.datn.dongho5s.Service.impl.NhanVienDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final int TOKEN_EXPIRATION = 7 * 24 * 60 * 60;
    private final String KEY = "AbcDefgHijKlmnOpqrs_1234567890";

    @Bean
    public KhachHangDetailsServiceImpl khachHangDetailsServices(){
        return new KhachHangDetailsServiceImpl();
    }

    @Bean
    public NhanVienDetailsServiceImpl nhanVienDetailsServices(){
        return new NhanVienDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProviderNhanVien(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(nhanVienDetailsServices());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProviderKhachHang(){

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(khachHangDetailsServices());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(khachHangDetailsServices())
                .and()
                .userDetailsService(nhanVienDetailsServices());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/client/index","/client/login").permitAll()
                    .antMatchers("/users/**").hasAuthority("ADMIN_ROOT")
                    .antMatchers("/categories/**").hasAnyAuthority("ADMIN_ROOT", "ADMIN_STAFF")
                    .antMatchers("/client/**").hasAuthority("CLIENT")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/client/login")
                    .loginProcessingUrl("/client/login") // API call
                    .usernameParameter("email")
                    .successHandler(new CustomClientAuthenticationSuccessHandler())
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("email")
                    .successHandler(new CustomAdminAuthenticationSuccessHandler())
                    .and()
                .logout()
                    .permitAll()
                    .and()
                .rememberMe()
                    .key(KEY)
                    .tokenValiditySeconds(TOKEN_EXPIRATION)
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                    .authenticationEntryPoint(new CustomAuthenticationEntryPoint("/client/login", "/login"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
    }

}
