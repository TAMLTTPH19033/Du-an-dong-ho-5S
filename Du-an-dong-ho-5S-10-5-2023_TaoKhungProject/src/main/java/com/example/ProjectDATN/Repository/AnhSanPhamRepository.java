package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham,Integer> {
}
