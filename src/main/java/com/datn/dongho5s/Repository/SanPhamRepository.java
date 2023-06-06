package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham,Integer> {
}
