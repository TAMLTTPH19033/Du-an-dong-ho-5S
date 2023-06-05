package com.dongho5s.admin.Service.Impl;

import com.dongho5s.admin.Entity.NhanVien;
import com.dongho5s.admin.Exception.NhanVienNotFoundException;
import com.dongho5s.admin.Repository.NhanVienRepository;
import com.dongho5s.admin.Security.NhanVienDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NhanVienDetailsService implements UserDetailsService {
    @Autowired
    private NhanVienRepository nhanVienRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NhanVien nhanVien =  nhanVienRepo.getNhanVienByEmail(email);
        if(nhanVien!= null){
            return new NhanVienDetails(nhanVien);
        }
        throw new UsernameNotFoundException("không tìm thấy nhân viên theo email: " + email);
    }
}
