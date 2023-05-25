package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang,Integer> {
}
