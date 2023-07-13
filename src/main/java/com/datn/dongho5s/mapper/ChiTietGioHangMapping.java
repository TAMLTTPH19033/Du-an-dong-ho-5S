package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import lombok.Data;

@Data
public class ChiTietGioHangMapping {
    public  static ChiTietGioHangResponse mapEntitytoResponse(ChiTietGioHang chiTietGioHang){
        ChiTietGioHangResponse chiTietGioHangResponse =  ChiTietGioHangResponse.builder()
                .idChiTietGioHang(chiTietGioHang.getIdChiTietGioHang())
                .chiTietSanPham(chiTietGioHang.getChiTietSanPham())
                .gioHang(chiTietGioHang.getGioHang())
                .ghiChu(chiTietGioHang.getGhiChu())
                .giaBan(chiTietGioHang.getGiaBan())
                .ngayTao(chiTietGioHang.getNgayTao())
                .soLuongSanPham(chiTietGioHang.getSoLuongSanPham())
//                .thanhTien(chiTietGioHang.getThanhTien())
                .build();
        return chiTietGioHangResponse;
    }

    public  static ChiTietGioHang mapRequesttoEntity(ChiTietGioHangRequest chiTietGioHangRequest){
        ChiTietGioHang chiTietGioHang =  ChiTietGioHang.builder()
                .idChiTietGioHang(chiTietGioHangRequest.getIdChiTietGioHang())
                .chiTietSanPham(chiTietGioHangRequest.getChiTietSanPham())
                .gioHang(chiTietGioHangRequest.getGioHang())
                .ghiChu(chiTietGioHangRequest.getGhiChu())
                .giaBan(chiTietGioHangRequest.getGiaBan())
                .ngayTao(chiTietGioHangRequest.getNgayTao())
                .soLuongSanPham(chiTietGioHangRequest.getSoLuongSanPham())
//                .thanhTien(chiTietGioHangRequest.getThanhTien())
                .build();
        return chiTietGioHang;
    }
}
