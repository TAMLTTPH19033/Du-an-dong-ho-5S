package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;

import java.util.List;

public interface SanPhamService {
    List<SanPham> getSPnew();

    List<SanPham> getSPchay();
}
