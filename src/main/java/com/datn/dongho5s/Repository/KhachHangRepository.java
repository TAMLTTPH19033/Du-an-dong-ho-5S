package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {
    public KhachHang findKhachHangByEmail(String email);
}
