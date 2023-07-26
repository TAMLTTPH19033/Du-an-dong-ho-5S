package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DonHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
