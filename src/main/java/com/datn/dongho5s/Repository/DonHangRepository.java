package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang,Integer> {
}
