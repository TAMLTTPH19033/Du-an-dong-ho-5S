package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc,Integer> {
}
