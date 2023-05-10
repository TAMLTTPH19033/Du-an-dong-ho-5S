package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.CuaHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang,Integer> {
}
