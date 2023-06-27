package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi,Integer> {
    public DiaChi findByDiaChi(String diaChi);
}
