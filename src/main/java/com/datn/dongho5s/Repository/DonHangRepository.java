package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.PhanHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang,Integer> {

    @Query(value = "select hdct from HoaDonChiTiet hdct where hdct.donHang.khachHang.idKhachHang = ?1 and hdct.chiTietSanPham.idChiTietSanPham = ?2")
    Optional<List<HoaDonChiTiet>> findHDDonHang(Integer idKhachHang, Integer idSanPham);

}
