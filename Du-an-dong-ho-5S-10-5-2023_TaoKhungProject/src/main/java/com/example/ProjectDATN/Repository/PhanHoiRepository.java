package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.PhanHoi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhanHoiRepository extends JpaRepository<PhanHoi,Integer> {
}
