package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Repository.NhanVienRepository;
import com.datn.dongho5s.Security.NhanVienDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NhanVienDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        NhanVien nhanVien =  nhanVienRepository.findNhanVienByEmail(email);
        if(nhanVien!= null){
            return new NhanVienDetails(nhanVien);
        }
        throw new UsernameNotFoundException("Can not find Employee by email is: " + email);
    }
}
