package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.ChiTietGioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang,Integer> {
}
