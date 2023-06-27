package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietGioHang;
import com.datn.dongho5s.Request.ChiTietGioHangRequest;

import java.util.List;

public interface ChiTietGioHangService {

    List<ChiTietGioHang> getChiTietGioHang();

    ChiTietGioHang update(ChiTietGioHang chiTietGioHang);
    void delete(Integer id);
    void deleteAll();
}
