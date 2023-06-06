package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.Seri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriRepository extends JpaRepository<Seri,Integer> {
}
