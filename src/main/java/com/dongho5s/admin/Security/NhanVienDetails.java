package com.dongho5s.admin.Security;

import com.dongho5s.admin.Entity.ChucVu;
import com.dongho5s.admin.Entity.NhanVien;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class NhanVienDetails implements UserDetails {
    private NhanVien nhanVien;

    public NhanVienDetails(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<ChucVu> chucVu = nhanVien.getChucVu();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(ChucVu cv : chucVu ){
            authorities.add(new SimpleGrantedAuthority(cv.getTenChucVu()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return nhanVien.getMatKhau();
    }

    @Override
    public String getUsername() {
        return nhanVien.getEmail();
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
        return nhanVien.isEnabled();
    }

    public String getFullName(){
        return this.nhanVien.getHo() + " " +this.nhanVien.getTen();
    }

    public void setHo(String ho){
        this.nhanVien.setHo(ho);

    }

    public void setTen(String ten){
        this.nhanVien.setTen(ten);

    }
}
