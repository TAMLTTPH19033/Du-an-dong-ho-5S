package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import com.datn.dongho5s.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    DiaChiRepository diaChiRepository;

    @Override
    public KhachHang findKhachHangById(Integer id) {

        if (id == null) return null;
        KhachHang khachHang = khachHangRepository.findById(id).get();
        System.out.println(khachHang.getIdKhachHang());
        System.out.println(khachHang.getTenKhachHang());
        System.out.println(khachHangRepository.findById(id).get().getTenKhachHang());
        return khachHang;
    }

    @Override
    public ThongTinCaNhanResponse updateThongTinCaNhan(ThongTinCaNhanResponse thongTinCaNhanResponse) {

        KhachHang khachHangExist = this.findKhachHangById(thongTinCaNhanResponse.getId());
        if (khachHangExist == null) {
            return null;
        }

        khachHangExist.setGioiTinh(thongTinCaNhanResponse.getGioiTinh());
        khachHangExist.setEmail(thongTinCaNhanResponse.getEmail());
        khachHangExist.setTenKhachHang(thongTinCaNhanResponse.getTenKhachHang());
        khachHangExist.setNgaySinh(thongTinCaNhanResponse.getNgaySinh());
        khachHangExist.setSoDienThoai(thongTinCaNhanResponse.getSoDienThoai());
        khachHangExist.setIdKhachHang(thongTinCaNhanResponse.getId());
//        khachHangExist.setDiaChi(diaChiRepository.findDiaChiByDiaChi(thongTinCaNhanResponse.getDiaChi()));

        khachHangRepository.save(khachHangExist);

        return thongTinCaNhanResponse;
    }

    @Override
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id) {

        KhachHang khachHangExist = this.findKhachHangById(id);

        if (khachHangExist == null) return null;

        ThongTinCaNhanResponse thongTinCaNhanResponse = new ThongTinCaNhanResponse();

        thongTinCaNhanResponse.setId(khachHangExist.getIdKhachHang());
        thongTinCaNhanResponse.setGioiTinh(khachHangExist.getGioiTinh());
        thongTinCaNhanResponse.setDiaChi(khachHangExist.getDiaChi().getDiaChi());
        thongTinCaNhanResponse.setNgaySinh(khachHangExist.getNgaySinh());
        thongTinCaNhanResponse.setSoDienThoai(khachHangExist.getSoDienThoai());
        thongTinCaNhanResponse.setEmail(khachHangExist.getEmail());
        thongTinCaNhanResponse.setTenKhachHang(khachHangExist.getTenKhachHang());

        return thongTinCaNhanResponse;
    }


}
