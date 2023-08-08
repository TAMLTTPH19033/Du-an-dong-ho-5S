package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import lombok.Data;

@Data
public class SanPhamMapping {
    public  static SanPhamDetailResponse mapEntitytoResponse(SanPham sp){
        SanPhamDetailResponse sanPhamDetailResponse =  SanPhamDetailResponse.builder()
                .idSanPham(sp.getIdSanPham())
                .listAnhSanPham(sp.getListAnhSanPham())
                .giaSanPham(sp.getGiaSanPham())
                .moTaSanPham(sp.getMoTaSanPham())
                .tenSanPham(sp.getTenSanPham())
                .danhMuc(sp.getDanhMuc())
                .listChiTietSanPham(sp.getListChiTietSanPham())
                .thuongHieu(sp.getThuongHieu())
                .trangThai(sp.getTrangThai())
                .build();
        return sanPhamDetailResponse;
    }


}
