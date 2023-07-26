package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.PhanHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoi,Integer> {

    @Query(value = "select ph from PhanHoi ph where ph.chiTietSanPham.idChiTietSanPham = ?1")
    List<PhanHoi> findAll(Integer idSanPham);
    @Query(value = "select ph from PhanHoi ph where ph.khachHang.idKhachHang = ?1 and ph.chiTietSanPham.idChiTietSanPham = ?2 ")
    Optional<PhanHoi> findPhanHoi(Integer idKhachHang, Integer idSanPham);

    @Query(value = "select count(ph) from PhanHoi ph where ph.khachHang.idKhachHang = ?1 and ph.chiTietSanPham.idChiTietSanPham = ?2")
    PhanHoi countPhanHoi(Integer idKhachHang, Integer idChiTietSanPham);





}
