package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi,Integer> {
    public DiaChi findByDiaChi(String diaChi);
    List<DiaChi> findByKhachHang(KhachHang khachHang);
}
