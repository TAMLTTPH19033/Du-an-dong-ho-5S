package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.PhanHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoi,Integer> {
}