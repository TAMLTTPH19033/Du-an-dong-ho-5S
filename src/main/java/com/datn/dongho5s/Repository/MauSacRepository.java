package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac,Integer> {
}
