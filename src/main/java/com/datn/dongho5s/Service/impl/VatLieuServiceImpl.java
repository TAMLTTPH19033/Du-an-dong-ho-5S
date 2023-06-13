package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Repository.VatLieuRepository;
import com.datn.dongho5s.Service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VatLieuServiceImpl implements VatLieuService {
    @Autowired
    VatLieuRepository vatLieuRepository;
    @Override
    public List<VatLieu> getAllVatLieu() {
        return vatLieuRepository.findAll();
    }
}
