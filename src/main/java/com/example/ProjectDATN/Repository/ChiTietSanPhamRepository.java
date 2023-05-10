package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Integer>{
}
