package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.DayDeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayDeoRepository extends JpaRepository<DayDeo,Integer> {
}
