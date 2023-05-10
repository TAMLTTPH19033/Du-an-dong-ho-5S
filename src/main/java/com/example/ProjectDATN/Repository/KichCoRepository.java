package com.example.ProjectDATN.Repository;

import com.example.ProjectDATN.Entity.KichCo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo,Integer> {
}
