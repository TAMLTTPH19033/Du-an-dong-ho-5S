package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Request.TimKiemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query(value =
            "SELECT sp FROM SanPham sp " +
                    "INNER JOIN ThuongHieu th ON sp.thuongHieu.idThuongHieu = th.idThuongHieu " +
                    "INNER JOIN DanhMuc dm ON sp.danhMuc.id = dm.id " +
                    "INNER JOIN ChiTietSanPham ctsp ON sp.idSanPham = ctsp.sanPham.idSanPham " +
                    "INNER JOIN DayDeo dd ON ctsp.dayDeo.idDayDeo = dd.idDayDeo " +
                    "INNER JOIN KichCo kc ON ctsp.kichCo.idKichCo = kc.idKichCo " +
                    "INNER JOIN VatLieu vl ON ctsp.vatLieu.idVatLieu = vl.idVatLieu " +
                    "INNER JOIN MauSac ms ON ctsp.mauSac.idMauSac = ms.idMauSac " +
                    "WHERE (:#{#req.thuongHieuId} is null or sp.thuongHieu.idThuongHieu IN (:#{#req.thuongHieuId}) ) " +
                    "AND (:#{#req.danhMucId} is null or sp.danhMuc.id IN (:#{#req.danhMucId}) ) " +
                    "AND (:#{#req.dayDeoId} is null or ctsp.dayDeo.idDayDeo IN (:#{#req.dayDeoId}) ) " +
                    "AND (:#{#req.sizeId} is null or ctsp.kichCo.idKichCo IN (:#{#req.sizeId}) ) " +
                    "AND (:#{#req.vatLieuId} is null or ctsp.vatLieu.idVatLieu IN (:#{#req.vatLieuId}) ) " +
                    "AND (:#{#req.mauSacId} is null or ctsp.mauSac.idMauSac IN (:#{#req.mauSacId}) ) " +
                    "AND (:#{#req.tenSanPham} is null or :#{#req.tenSanPham} like '%' || :#{#req.tenSanPham} || '%')" )
    List<SanPham> getListSanPhamByCondition(@Param("req") TimKiemRequest req);

    @Query(value = "SELECT  * FROM sanpham ORDER BY sanpham.id_san_pham DESC LIMIT 8", nativeQuery = true)
    List<SanPham> getSPnew();


    @Query(value = "SELECT h.chiTietSanPham.sanPham  FROM HoaDonChiTiet h " +
            "group by h.chiTietSanPham.sanPham  ORDER BY  SUM(h.soLuong) DESC")
    List<SanPham> getSPchay();

    @Query(value = "SELECT s.listChiTietSanPham  FROM SanPham s where s.idSanPham = ?1")
    List<ChiTietSanPham> getCTSP(Integer idSanPham);

    /**
     * @param pageable
     * @return
     * product pagination
     */
    @Query(value = """
        SELECT sp
        FROM SanPham sp
    """)
    Page<SanPham> getPageSanPham(Pageable pageable);

}
