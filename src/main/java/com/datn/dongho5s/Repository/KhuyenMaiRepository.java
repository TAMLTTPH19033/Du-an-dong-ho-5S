package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai,Integer> {

//    @Query("UPDATE DanhMuc dm SET dm.enabled = ?2 WHERE dm.id = ?1")
//    @Modifying
//    void updateEnabledStatus(Integer id, boolean enabled);
//
//
//    @Query("SELECT dm FROM DanhMuc dm WHERE UPPER(CONCAT(dm.id, ' ', dm.ten)) LIKE %?1%")
//    public Page<DanhMuc> findAll(String keyword, Pageable pageable);
//
//    public DanhMuc findByTen(String ten);
    @Query("SELECT km FROM KhuyenMai km WHERE UPPER(CONCAT(km.idKhuyenMai,' ', km.tenKhuyenMai,' ',km.moTaKhuyenMai,' ', km.ngayBatDau,' ', km.ngayKetThuc,' ',km.enabled))LIKE %?1%")
    public Page<KhuyenMai> findAll(String keyword, Pageable pageable);

    public KhuyenMai findByTenKhuyenMai(String ten);

}
