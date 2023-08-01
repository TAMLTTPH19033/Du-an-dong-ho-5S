package com.datn.dongho5s.Security;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Repository.NhanVienRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    private final NhanVienRepository nhanVienRepository;
    private final KhachHangRepository khachHangRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                KhachHang customer = khachHangRepository.getKhachHangByEmail(username);
                NhanVien staff = nhanVienRepository.getNhanVienByEmail(username);
                if(customer != null){
                    return customer.get();
                }else if (staff != null) {
                    System.out.println(staff);
                    return buildStaffUserDetails(staff);
                } else {
                    throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + username);
                }
            }
            private UserDetails buildStaffUserDetails(NhanVien staff) {
                // Xác định các vai trò cho tài khoản Nhân viên (Admin và Staff)
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
                authorities.add(new SimpleGrantedAuthority("STAFF"));

                return new org.springframework.security.core.userdetails.User(staff.getEmail(), staff.getMatKhau(), staff.isEnabled(), true, true, true, authorities);
            }
        };
    }



    @Bean
    public AuthenticationProvider getProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}


