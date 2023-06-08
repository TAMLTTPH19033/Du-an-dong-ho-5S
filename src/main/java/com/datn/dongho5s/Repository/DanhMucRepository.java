package com.datn.dongho5s.Repository;

import com.datn.dongho5s.Entity.DanhMuc;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DanhMucRepository extends PagingAndSortingRepository<DanhMuc,Integer> {
    @Query("SELECT dm FROM DanhMuc dm WHERE dm.cha.id is NULL ")
    public List<DanhMuc> findRootDanhMuc();

//    @Query("UPDATE DanhMuc dm SET dm.enabled = ?2 WHERE dm.id = ?1")
//    @Modifying
//    public void updateEnabledStatus(Integer id, boolean enabled);




}
