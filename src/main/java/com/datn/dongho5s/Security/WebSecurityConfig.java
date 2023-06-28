package com.datn.dongho5s.Security;

//import com.datn.dongho5s.Service.impl.NhanVienDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@EnableWebSecurity
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

}
