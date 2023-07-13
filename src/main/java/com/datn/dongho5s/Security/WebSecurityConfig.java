package com.datn.dongho5s.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  {
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new NhanVienDetailsService();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authorProvider = new DaoAuthenticationProvider();
//        authorProvider.setUserDetailsService(userDetailsService());
//        authorProvider.setPasswordEncoder(passwordEncoder());
//
//        return authorProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests().antMatchers("/").permitAll()
////                .antMatchers("/users/**").hasAuthority("Admin")
////                .antMatchers("/categories/**").hasAnyAuthority("Admin","Staff")
////                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .defaultSuccessUrl("/", true)
//                .permitAll()
//                .and().logout().permitAll()
//                .and().rememberMe()
//                        .key("AbcDefgHijKlmnOpqrs_1234567890")
//                        .tokenValiditySeconds(7 * 24 * 60 * 60);
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
//    }

    private final AuthenticationProvider authenticationProvider;
    private final AccountFilter accountFilter;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, AccountFilter accountFilter) {
        this.authenticationProvider = authenticationProvider;
        this.accountFilter = accountFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/v1/customer/**").hasAuthority("ROLE_CUSTOMER")
                .antMatchers("/users/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/categories/**").hasAnyAuthority("ROLE_ADMIN","ROLE_STAFF")
                .antMatchers("/users/**", "/categories/**")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(accountFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
