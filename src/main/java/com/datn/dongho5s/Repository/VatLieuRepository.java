package com.datn.dongho5s.Repository;

import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Entity.VatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VatLieuRepository extends JpaRepository<VatLieu,Integer> {
    public Long countById(Integer id);

    @Query("SELECT vl FROM VatLieu vl WHERE UPPER(CONCAT(vl.id, ' ', vl.ten, ' ', vl.moTa) )LIKE %?1%")
    public Page<VatLieu> findAll(String keyword, Pageable pageable);

    @Query("UPDATE VatLieu vl SET vl.enabled = ?2 WHERE vl.id = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);
}
