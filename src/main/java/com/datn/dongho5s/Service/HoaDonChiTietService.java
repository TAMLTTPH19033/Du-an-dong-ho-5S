package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;

import java.util.List;

public interface HoaDonChiTietService {

    HoaDonChiTiet save (HoaDonChiTiet hdct);
    List<HoaDonChiTiet> convertToListHoaDonChiTiet (List<ChiTietSanPhamRequest> list,Integer idDonHang);
}
