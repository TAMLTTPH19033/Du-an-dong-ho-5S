package com.datn.dongho5s.Service;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.ChiTietSanPhamResponse;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.util.List;
import java.util.Set;

public interface SanPhamService {

    Set<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest);

    List<SanPham> getSPnew();

    List<SanPhamDetailResponse> getSPchay();

    SanPhamDetailResponse getDetailSanPhamById(Integer sanPhamId);

    List<ChiTietSanPhamResponse> getSPchayKM(Integer idChiTietSanPham);

    public Page<SanPham> getPageSanPham(int pageNumber);
}
