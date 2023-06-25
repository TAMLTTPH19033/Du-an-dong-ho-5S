package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import com.datn.dongho5s.Service.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachHangRepository;

    private final DiaChiRepository diaChiRepository;

    @Override
    public KhachHang findKhachHangById(Integer id) {
        if (id == null) return null;
        return khachHangRepository.findById(id).get();
    }

    @Override
    public KhachHang updateThongTinCaNhan(ThongTinCaNhanResponse thongTinCaNhanResponse) {
        KhachHang khachHangExist = this.findKhachHangById(thongTinCaNhanResponse.getId());
        if (khachHangExist == null) {
            return null;
        }
        return khachHangRepository.save(khachHangExist.builder()
                .gioiTinh(thongTinCaNhanResponse.getGioiTinh())
                .email(thongTinCaNhanResponse.getEmail())
                .tenKhachHang(thongTinCaNhanResponse.getTenKhachHang())
                .ngaySinh(thongTinCaNhanResponse.getNgaySinh())
                .soDienThoai(thongTinCaNhanResponse.getSoDienThoai())
                .idKhachHang(thongTinCaNhanResponse.getId())
                .diaChi(diaChiRepository.findByDiaChi(thongTinCaNhanResponse.getDiaChi()))
                .build());
    }

    @Override
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id) {

        KhachHang khachHangExist = this.findKhachHangById(id);

        if (khachHangExist == null) return null;

        return ThongTinCaNhanResponse.builder()
                .id(khachHangExist.getIdKhachHang())
                .gioiTinh(khachHangExist.getGioiTinh())
                .diaChi(khachHangExist.getDiaChi().getDiaChi())
                .ngaySinh(khachHangExist.getNgaySinh())
                .soDienThoai(khachHangExist.getSoDienThoai())
                .email(khachHangExist.getEmail())
                .tenKhachHang(khachHangExist.getTenKhachHang())
                .build();
    }
}
