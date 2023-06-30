package com.datn.dongho5s.Service;

import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;

import java.util.List;

public interface ChiTietGioHangService {

    List<ChiTietGioHangResponse> getChiTietGioHang();

    ChiTietGioHangResponse update(ChiTietGioHangRequest chiTietGioHangRequest);

    void delete(Integer id);
    void deleteAll();
    ChiTietGioHangResponse add(ChiTietSanPhamRequest sanPhamDetailRequest, Integer  soLuong);

    ChiTietGioHangResponse addToCart(ChiTietSanPhamRequest sanPhamDetailRequest, Integer  soLuong);
}
