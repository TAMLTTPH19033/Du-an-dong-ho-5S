package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Repository.HoaDonChiTietRepository;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Request.HoaDonChiTietRequest;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    DonHangService donHangService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;
    @Override
    public HoaDonChiTiet save(HoaDonChiTiet hdct) {
        return hoaDonChiTietRepository.save(hdct);
    }

    @Override
    public List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang) {
        List<HoaDonChiTiet> result = new ArrayList<>();
        list.forEach(item->{
            ChiTietSanPham ctsp = chiTietSanPhamService.getChiTietSanPhamById(item.getIdChiTietSanPham());
            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .donHang(donHangService.getById(idDonHang))
                    .chiTietSanPham(ctsp)
//                    .ngayTao()
                    .soLuong(item.getSoLuong())
                    .giaBan(ctsp.getGiaSanPham())
                    .build();
            result.add(hdct);
        });
        return result;
    }

    @Override
    public List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT) {
        hoaDonChiTietRepository.saveAll(listHDCT);
        return null;
    }

    @Override
    public Double getTongGia(List<HoaDonChiTietRequest> list) {
        Double result = 0D;
        for (int i=0;i<list.size();i++){
            Double giaBan = chiTietSanPhamService.getChiTietSanPhamById(list.get(i).getIdChiTietSanPham()).getGiaSanPham();
            result += list.get(i).getSoLuong()*giaBan;
        }
        return result;
    }

}
