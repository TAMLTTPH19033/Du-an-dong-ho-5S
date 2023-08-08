package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import com.datn.dongho5s.Response.ThongTinToCheckoutResponse;
import com.datn.dongho5s.Service.KhachHangService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    private final KhachHangRepository khachHangRepository;

    private final DiaChiRepository diaChiRepository;
    @Autowired
    private KhachHangRepository khachHangRepo;
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
//                .diaChi(diaChiRepository.findByDiaChi(thongTinCaNhanResponse.getDiaChi()))
                .build());
    }

    @Override
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id) {

        KhachHang khachHangExist = this.findKhachHangById(id);

        if (khachHangExist == null) return null;

        return ThongTinCaNhanResponse.builder()
                .id(khachHangExist.getIdKhachHang())
                .gioiTinh(khachHangExist.getGioiTinh())
//                .diaChi(khachHangExist.getDiaChi().getDiaChi())
                .ngaySinh(khachHangExist.getNgaySinh())
                .soDienThoai(khachHangExist.getSoDienThoai())
                .email(khachHangExist.getEmail())
                .tenKhachHang(khachHangExist.getTenKhachHang())
                .build();
    }

    @Override
    public ThongTinToCheckoutResponse getThongTinToCheckout(Integer id) {
        System.out.println(id);
        KhachHang khachHangExist = khachHangRepo.findById(id).get();

        if (khachHangExist == null) return null;

        return ThongTinToCheckoutResponse.builder()
                .id(khachHangExist.getIdKhachHang())
                .listDiaChi(khachHangExist.getListDiaChi())
                .soDienThoai(khachHangExist.getSoDienThoai())
                .tenKhachHang(khachHangExist.getTenKhachHang())
                .build();
    }

    @Override
    public void saveKhachHang(KhachHang khachHang) {
        khachHangRepo.save(khachHang);
    }
}
