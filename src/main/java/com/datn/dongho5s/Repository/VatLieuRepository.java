package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.VatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VatLieuRepository extends JpaRepository<VatLieu,Integer> {
}
