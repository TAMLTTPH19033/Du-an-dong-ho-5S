package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham,Integer> {
    @Query(value = "SELECT  * FROM sanpham ORDER BY sanpham.id_san_pham DESC LIMIT 8",nativeQuery = true)
    List<SanPham> getSPnew();


    @Query(value = "SELECT h.chiTietSanPham.sanPham  FROM HoaDonChiTiet h " +
            "group by h.chiTietSanPham.sanPham  ORDER BY  SUM(h.soLuong) DESC")
    List<SanPham> getSPchay();
}
