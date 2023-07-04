package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.KhuyenMai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai,Integer> {

    @Query("SELECT km FROM KhuyenMai km WHERE UPPER(CONCAT(km.idKhuyenMai,' ', km.maKhuyenMai,' ', km.tenKhuyenMai,' ',km.moTaKhuyenMai,' ', km.ngayBatDau,' ', km.ngayKetThuc,' ',km.enabled))LIKE %?1%")
    public Page<KhuyenMai> findAll(String keyword, Pageable pageable);

    public KhuyenMai findByTenKhuyenMai(String ten);

    KhuyenMai findByMaKhuyenMai(String ma);
}
