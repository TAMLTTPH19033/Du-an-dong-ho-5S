package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Response.SanPhamAdminResponse;
import com.datn.dongho5s.Response.TimKiemSettingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChiTietSanPhamService {
    TimKiemSettingResponse getTimKiemSetting ();
    ChiTietSanPham getChiTietSanPhamById(Integer id);
    ChiTietSanPham update(ChiTietSanPham chiTietSanPham);
    Page<ChiTietSanPham> findByMaSP(String maSanPham, int pageNum);

    Page<ChiTietSanPham> getALlChiTietSanPhamPage(int pageNum, String keyword);

    List<SanPhamAdminResponse> getAllSanPhamAminResponse(int pageNum, String keyword);

    ChiTietSanPham findByMaChiTietSanPham(String maChimaTietSanPham);
    ChiTietSanPham getChiTietSanPhamByMa (String ma);
}
