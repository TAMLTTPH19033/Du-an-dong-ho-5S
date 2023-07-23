package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Repository.DonHangRepository;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    DonHangRepository donHangRepository;
    @Override
    public DonHang save(DonHang donHang) {
        return donHangRepository.save(donHang);
    }

    @Override
    public DonHang getById(Integer idDonHang) {
        Optional<DonHang> optionalDonHang = donHangRepository.findById(idDonHang);
        if (optionalDonHang.isPresent()) {
            return optionalDonHang.get();
        } else {
            return null;
        }
    }
}
