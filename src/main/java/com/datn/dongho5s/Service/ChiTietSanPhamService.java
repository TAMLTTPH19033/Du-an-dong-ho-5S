package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Response.TimKiemSettingResponse;

public interface ChiTietSanPhamService {
    TimKiemSettingResponse getTimKiemSetting ();
    ChiTietSanPham getChiTietSanPhamById(Integer id);

    ChiTietSanPham update(ChiTietSanPham chiTietSanPham);
    ChiTietSanPham getChiTietSanPhamByMa (String ma);
}
