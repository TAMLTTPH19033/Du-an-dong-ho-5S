package com.datn.dongho5s.Security;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class SecurityConfig  {


    private final KhachHangRepository khachHangRepository;
    private final NhanVienRepository nhanVienRepository;

    public SecurityConfig(NhanVienRepository nhanVienRepository, KhachHangRepository khachHangRepository) {
        this.nhanVienRepository = nhanVienRepository;
        this.khachHangRepository = khachHangRepository;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService (){
        return new UserDetailsService() {
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<KhachHang> customer = khachHangRepository.getKhachHangByEmail(username);
                Optional<NhanVien> staff = nhanVienRepository.getNhanVienByEmail(username);

                if (customer.isPresent()) {
                    System.out.println("Khach Hang: " + username);
                    KhachHang khachHang = customer.get();
                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("CUSTOMER"));
                    return new User(khachHang.getEmail(), khachHang.getPassword(), authorities);
                } else if (staff.isPresent()) {
                    System.out.println("Nhan Vien: " + username);
                    NhanVien nhanVien = staff.get();
                    System.out.println("Mật khẩu của nhân viên: " + nhanVien.getEmail() + " là " + nhanVien.getMatKhau());
                    List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("STAFF"));
                    return new User(nhanVien.getEmail(), nhanVien.getPassword(), authorities);
                }

                throw new UsernameNotFoundException("Tài khoản không tồn tại: " + username);
            }
        };
    }
}
