package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Response.ThongTinCaNhanResponse;
import org.springframework.stereotype.Service;

public interface KhachHangService {
    public KhachHang findKhachHangById(Integer id);
    public KhachHang updateThongTinCaNhan(ThongTinCaNhanResponse thongTinCaNhanResponse);
    public ThongTinCaNhanResponse getThongTinCaNhanById(Integer id);

}
