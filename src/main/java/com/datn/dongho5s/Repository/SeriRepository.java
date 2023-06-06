package com.datn.dongho5s.Repository;

import com.example.ProjectDATN.Entity.Seri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriRepository extends JpaRepository<Seri,Integer> {
}
