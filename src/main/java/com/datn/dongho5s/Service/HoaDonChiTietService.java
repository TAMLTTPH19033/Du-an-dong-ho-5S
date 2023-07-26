package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Request.ChiTietSanPhamRequest;
import com.datn.dongho5s.Request.HoaDonChiTietRequest;

import java.util.List;

public interface HoaDonChiTietService {


    HoaDonChiTiet save(HoaDonChiTiet hdct);

    List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang);

    List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT);

    Double getTongGia(List<HoaDonChiTietRequest> list);

    List<HoaDonChiTiet> getByIdDonHang(int id);

    List<HoaDonChiTiet> getByHoaDonId(DonHang donHang);

}
