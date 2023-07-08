package com.datn.dongho5s.Security;


import com.datn.dongho5s.Entity.KhachHang;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class KhachHangDetails implements UserDetails {

    private KhachHang khachHang;

    public KhachHangDetails(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return khachHang.getMatKhau();
    }

    @Override
    public String getUsername() {
        return khachHang.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return khachHang.getTrangThai() == 1;
    }
}