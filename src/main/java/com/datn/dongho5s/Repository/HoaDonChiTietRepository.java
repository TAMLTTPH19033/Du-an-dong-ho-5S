package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet,Integer> {
}
