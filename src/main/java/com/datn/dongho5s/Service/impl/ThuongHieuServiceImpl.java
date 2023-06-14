package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Repository.ThuongHieuRepository;
import com.datn.dongho5s.Service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {
    @Autowired
    ThuongHieuRepository thuongHieuRepository;
    @Override
    public List<ThuongHieu> getAllThuongHieu() {
        return thuongHieuRepository.findAll();
    }
}
