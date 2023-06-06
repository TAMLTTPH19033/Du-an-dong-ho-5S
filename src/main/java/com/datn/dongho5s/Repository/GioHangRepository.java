package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Integer> {
}
