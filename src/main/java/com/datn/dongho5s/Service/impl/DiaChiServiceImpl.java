package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Service.DiaChiService;
import com.datn.dongho5s.Service.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DiaChiServiceImpl implements DiaChiService {

    private final DiaChiRepository diaChiRepository;
    @Override
    public DiaChi createDiaChi(DiaChiRequest diaChiRequest) {
        return diaChiRepository.save(
                DiaChi.builder()
                .diaChi(diaChiRequest.getDiaChi())
                .ghiChu(diaChiRequest.getGhiChu())
                .soDienThoai(diaChiRequest.getSoDienThoai())
                .maBuuChinh(diaChiRequest.getMaBuuChinh())
                .trangThaiMacDinh(diaChiRequest.getTrangThaiMacDinh())
                .build()
        );
    }

    @Override
    public List<DiaChi> getAllDiaChi() {
        return diaChiRepository.findAll();
    }

    @Override
    public List<DiaChi> getAllDiaChiByKhachHang(KhachHang khachHang) {
        return diaChiRepository.findByKhachHang(khachHang);
    }
}
