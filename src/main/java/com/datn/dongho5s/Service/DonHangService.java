package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Request.DonHangRequest;
import com.datn.dongho5s.Response.DonHangResponse;
import com.datn.dongho5s.Response.HoaDonChiTietResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DonHangService {
    DonHang save(DonHang donHang);

    DonHang getById(Integer idDonHang);

    public Page<DonHang> getAll(int pageNumber);

    List<DonHang> findByNgayTao(Date dateStart, Date dateEnd);

    public Double tongTien(int id);

    public DonHang findById(int id);

    public void updateTrangThaiDonHang(DonHang donHang);

    List<HoaDonChiTietResponse> findHDCTbyDH(Integer idDonhang);

    List<DonHangResponse> findAllHD(Integer idKhachHang);

    List<DonHangResponse> findHDByStatus(Integer idKhachHang, Integer trangThaiDonHang);

    public DonHangResponse updateDH(DonHangRequest donHangRequest);

    List<DonHang> findByTrangThaiDonHang(int trangThai);

    Page<DonHang> getAllForBanHang(int pageNum);

    public DonHang findByMaDonHang(String maDonHang);
}
