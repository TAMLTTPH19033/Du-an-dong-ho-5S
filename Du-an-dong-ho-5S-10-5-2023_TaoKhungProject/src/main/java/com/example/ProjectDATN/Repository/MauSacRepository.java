package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac,Integer> {
}
