package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.Seri;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SeriRepository extends JpaRepository<Seri,Integer> {
    @Query("SELECT s FROM Seri s WHERE s.idImei LIKE %:keyword%")
    Page<Seri> findByIdImeiLike(@Param("keyword") String keyword, Pageable pageable);
    @Query(value = "select count(s) from Seri s where s.trangThai = 1 and s.chiTietSanPham.idChiTietSanPham = ?1")
    Integer countSeri (Integer chiTietSanPham);
    @Query("""
        SELECT COUNT(sr.idSeri)
        FROM Seri sr
        WHERE   sr.chiTietSanPham.idChiTietSanPham = ?1
                AND sr.trangThai = 1
                AND sr.hoaDonChiTiet is null
    """)
    int countByIdCTSPEnabled (int idCTSP);


    List<Seri> findByChiTietSanPhamAndTrangThai(ChiTietSanPham chiTietSanPham,Integer trangThai, Pageable pageable);

    @Query(nativeQuery = true, value = """
        WITH soLuong AS
            (
                SELECT s.id_seri s
                FROM seri s
                WHERE s.trang_thai = 1
                AND   s.id_chi_tiet_san_pham = :idChiTietSanPham
                LIMIT :soLuong
            )
       UPDATE seri s1
       SET s1.trang_thai = 3,
           s1.id_hoa_don_chi_tiet = :idHoaDonChiTiet,
           s1.ngay_ban = CURRENT_TIMESTAMP()
       WHERE s1.id_seri IN (
                                SELECT *
                                FROM soLuong
                           )
    """)
    @Modifying
    @Transactional
    void themSoLuongAdmin(
        @Param("idHoaDonChiTiet") int idHoaDonChiTiet,
        @Param("soLuong") int soLuong,
        @Param("idChiTietSanPham") int idChiTietSanPham
    );

    @Modifying
    @Transactional
    @Query("""
        UPDATE Seri s
        SET s.hoaDonChiTiet = null,
            s.ngayBan = null,
            s.trangThai = 1
        WHERE s.hoaDonChiTiet.idHoaDonChiTiet = ?1
    """)
    void xoaSoLuongSanPham(int idHDCT);

}
