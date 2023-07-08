package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Security.KhachHangDetails;
import com.datn.dongho5s.Service.KhachHangDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class KhachHangDetailsServiceImpl implements KhachHangDetailsService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        KhachHang khachHang =  khachHangRepository.findKhachHangByEmail(email);
        if(khachHang!= null){
            return new KhachHangDetails(khachHang);
        }
        throw new UsernameNotFoundException("Can not find Employee by email is: " + email);
    }
}
