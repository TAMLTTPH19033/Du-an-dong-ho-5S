package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.VatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatLieuRepository extends JpaRepository<VatLieu,Integer> {
}
