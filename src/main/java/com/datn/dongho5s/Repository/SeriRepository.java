package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.Seri;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriRepository extends JpaRepository<Seri,Integer> {

    @Query("SELECT s FROM Seri s WHERE s.idImei LIKE %:keyword%")
    Page<Seri> findByIdImeiLike(@Param("keyword") String keyword, Pageable pageable);
}
