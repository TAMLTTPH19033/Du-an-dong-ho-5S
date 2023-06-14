package com.datn.dongho5s.Repository;


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
public interface SanPhamRepository extends JpaRepository<SanPham,Integer> {

    @Query(value = """
            SELECT sp FROM SanPham sp
                INNER JOIN ThuongHieu th ON sp.thuongHieu.idThuongHieu = th.idThuongHieu
                INNER JOIN DanhMuc dm ON sp.danhMuc.id = dm.id
                INNER JOIN ChiTietSanPham ctsp ON sp.idSanPham = ctsp.sanPham.idSanPham
                INNER JOIN DayDeo dd ON ctsp.dayDeo.idDayDeo = dd.idDayDeo
                INNER JOIN KichCo kc ON ctsp.kichCo.idKichCo = kc.idKichCo
                INNER JOIN VatLieu vl ON ctsp.vatLieu.idVatLieu = vl.idVatLieu
                INNER JOIN MauSac ms ON ctsp.mauSac.idMauSac = ms.idMauSac
                    WHERE (:#{#req.thuongHieuId} is null or :#{#req.thuongHieuId}= sp.thuongHieu.idThuongHieu)
                    AND (:#{#req.danhMucId} is null or :#{#req.danhMucId}= sp.danhMuc.id)
                    AND (:#{#req.dayDeoId} is null or :#{#req.dayDeoId}= ctsp.dayDeo.idDayDeo)
                    AND (:#{#req.sizeId} is null or :#{#req.sizeId}= ctsp.kichCo.idKichCo)
                    AND (:#{#req.vatLieuId} is null or :#{#req.vatLieuId}= ctsp.vatLieu.idVatLieu)
                    AND (:#{#req.mauSacId} is null or :#{#req.mauSacId}= ctsp.mauSac.idMauSac)
                    AND (:#{#req.giaSanPham} is null or :#{#req.giaSanPham}>= sp.giaSanPham)
                    AND (:#{#req.tenSanPham} is null or :#{#req.tenSanPham} like sp.tenSanPham )
            """)
    List<SanPham> getListSanPhamByCondition(@Param("req") TimKiemRequest req);
           
    @Query(value = "SELECT  * FROM sanpham ORDER BY sanpham.id_san_pham DESC LIMIT 8",nativeQuery = true)
    List<SanPham> getSPnew();


    @Query(value = "SELECT h.chiTietSanPham.sanPham  FROM HoaDonChiTiet h " +
            "group by h.chiTietSanPham.sanPham  ORDER BY  SUM(h.soLuong) DESC")
    List<SanPham> getSPchay();

}
