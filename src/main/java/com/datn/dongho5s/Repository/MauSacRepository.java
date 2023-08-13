package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {

    @Query(nativeQuery = true, value = "SELECT ms.* FROM mausac ms WHERE UPPER(CONCAT(ms.id_mau_sac, ' ', ms.ten_mau_sac)) LIKE %?1%")
    public Page<MauSac> findAll(String keyword, Pageable pageable);

    @Query(nativeQuery = true, value = "UPDATE mausac ms SET ms.enabled = ?2 WHERE ms.id_mau_sac = ?1")
    @Modifying
    public void updateEnabledStatus(Integer id, boolean enabled);

}
