package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DonHang;

import java.util.List;

public interface DonHangService {
    DonHang save (DonHang donHang);
    DonHang getById (Integer idDonHang);

    public List<DonHang> getAll(int pageNumber);

    public Double tongTien(int id);

    public DonHang findById(int id);
    public void updateTrangThaiDonHang(DonHang donHang);
}
