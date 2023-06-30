package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Repository.ChiTietGioHangRepository;
import com.datn.dongho5s.Repository.GioHangRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Service.ChiTietGioHangService;
import com.datn.dongho5s.mapper.ChiTietGioHangMapping;
import com.datn.dongho5s.mapper.ChiTietSanPhamMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

    @Autowired
    ChiTietGioHangRepository chiTietGioHangRepository;
    @Autowired
    GioHangRepository gioHangRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Override
    public List<ChiTietGioHangResponse> getChiTietGioHang() {
        List<ChiTietGioHang> chiTietGioHangList = chiTietGioHangRepository.giohangChiTiet(1);
        List<ChiTietGioHangResponse> responseList = chiTietGioHangList.stream().map(ChiTietGioHangMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }


    @Override
    public ChiTietGioHangResponse update(ChiTietGioHangRequest chiTietGioHangRequest) {
        ChiTietGioHang chiTietGioHang = ChiTietGioHangMapping.mapRequesttoEntity(chiTietGioHangRequest);
        ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
        GioHang gioHang = chiTietGioHangResponse.getGioHang();
        gioHang.setThoiGianCapNhapGioHang(new Timestamp(new Date().getTime()));
        chiTietGioHangResponse.setGioHang(gioHang);
        return chiTietGioHangResponse;
    }

    @Override
    public ChiTietGioHangResponse add(ChiTietSanPhamRequest chiTietSanPhamRequest,Integer soLuong) {
        KhachHang khachHang = khachHangRepository.findById(1).get();
        GioHang gioHang = GioHang.builder()
                .idGioHang(1)
                .ngayTaoGioHang(new Date())
                .trangThaiGioHang(1)
                .khachHang(khachHang)
                .thoiGianCapNhapGioHang(new Timestamp(new Date().getTime()))
                .build();
        gioHangRepository.save(gioHang);
        ChiTietSanPham chiTietSanPham = ChiTietSanPhamMapping.mapRequestToEntity(chiTietSanPhamRequest);
        ChiTietGioHang chiTietGioHang = ChiTietGioHang.builder()
                .idChiTietGioHang(1)
                .chiTietSanPham(chiTietSanPham)
                .gioHang(gioHang)
                .ghiChu("")
                .giaBan(chiTietSanPham.getGiaSanPham())
                .ngayTao(new Date())
                .soLuongSanPham(soLuong)
                .thanhTien(null)
                .build();
        ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
        return chiTietGioHangResponse;
    }

    @Override
    public ChiTietGioHangResponse addToCart(ChiTietSanPhamRequest chiTietSanPhamRequest, Integer  soLuong) {
        try {
            GioHang gioHang = gioHangRepository.findGioHang(1);
            if (gioHang == null) {
                return add(chiTietSanPhamRequest,soLuong);
            } else {
                ChiTietSanPham chiTietSanPham = ChiTietSanPhamMapping.mapRequestToEntity(chiTietSanPhamRequest);
                ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findChiTietGioHangByCTSP(chiTietSanPhamRequest.getIdChiTietSanPham());
                if (chiTietGioHang == null) {
                    ChiTietGioHang chiTietGioHang1 = ChiTietGioHang.builder()
                            .idChiTietGioHang(null)
                            .chiTietSanPham(chiTietSanPham)
                            .gioHang(gioHang)
                            .ghiChu("")
                            .giaBan(chiTietSanPham.getGiaSanPham())
                            .ngayTao(new Date())
                            .soLuongSanPham(soLuong)
                            .thanhTien(null)
                            .build();
                    ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang1));
                    return chiTietGioHangResponse;
                } else {
                    chiTietGioHang.setSoLuongSanPham(chiTietGioHang.getSoLuongSanPham()+soLuong);
                    chiTietGioHang.setChiTietSanPham(chiTietSanPham);
                    ChiTietGioHangResponse chiTietGioHangResponse = ChiTietGioHangMapping.mapEntitytoResponse(chiTietGioHangRepository.save(chiTietGioHang));
                    return chiTietGioHangResponse;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("lỗi");
            return null;
        }
    }





    public void delete(Integer id) {
        chiTietGioHangRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        chiTietGioHangRepository.deleteAll();
    }
}
