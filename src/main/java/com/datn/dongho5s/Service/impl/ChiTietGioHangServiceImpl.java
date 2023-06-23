package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Repository.ChiTietGioHangRepository;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Service.ChiTietGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

    @Autowired
    ChiTietGioHangRepository chiTietGioHangRepository;
    @Override
    public List<ChiTietGioHang> getChiTietGioHang() {
        return chiTietGioHangRepository.giohangChiTiet(1);
    }


    @Override
    public ChiTietGioHang update(ChiTietGioHang chiTietGioHang) {
        return chiTietGioHangRepository.save(chiTietGioHang);
    }

    public void delete(Integer id) {
         chiTietGioHangRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        chiTietGioHangRepository.deleteAll();
    }
}
