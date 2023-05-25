package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.Pin;
import com.example.ProjectDATN.Entity.VatLieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PinRepository extends JpaRepository<Pin,Integer> {
}
