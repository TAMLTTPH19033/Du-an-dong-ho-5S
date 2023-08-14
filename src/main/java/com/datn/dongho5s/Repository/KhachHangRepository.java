package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {

    @Query("SELECT kh FROM KhachHang kh WHERE kh.email = :email")
    public KhachHang getKhachHangByEmail(@Param("email") String email);

    @Query("SELECT kh FROM KhachHang kh WHERE UPPER(CONCAT(kh.idKhachHang, ' ', kh.tenKhachHang, ' ', kh.email, '', kh.soDienThoai)) LIKE %?1%")
    public Page<KhachHang> findAll(String keyword, Pageable pageable);

    public KhachHang findByTenKhachHang(String ten);

    public KhachHang findBySoDienThoai(String phoneNumber);
}
