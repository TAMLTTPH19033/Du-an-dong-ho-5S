package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DayDeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayDeoRepository extends JpaRepository<DayDeo,Integer> {
}
