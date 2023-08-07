package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
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

//    @Query(value =
//            "SELECT sp FROM SanPham sp " +
//                    "INNER JOIN ThuongHieu th ON sp.thuongHieu.idThuongHieu = th.idThuongHieu " +
//                    "INNER JOIN DanhMuc dm ON sp.danhMuc.id = dm.id " +
//                    "INNER JOIN ChiTietSanPham ctsp ON sp.idSanPham = ctsp.sanPham.idSanPham " +
//                    "INNER JOIN DayDeo dd ON ctsp.dayDeo.idDayDeo = dd.idDayDeo " +
//                    "INNER JOIN KichCo kc ON ctsp.kichCo.idKichCo = kc.idKichCo " +
//                    "INNER JOIN VatLieu vl ON ctsp.vatLieu.idVatLieu = vl.idVatLieu " +
//                    "INNER JOIN MauSac ms ON ctsp.mauSac.idMauSac = ms.idMauSac " +
//                    "WHERE " +
//                    "( (:#{#ths.isEmpty() } ) or sp.thuongHieu.idThuongHieu IN (:#{#ths}) ) " +
//                    "AND ( (:#{#dms.isEmpty()}) or sp.danhMuc.id IN (:#{#dms}) ) " +
//                    "AND ( (:#{#dds.isEmpty() }) or ctsp.dayDeo.idDayDeo IN (:#{#dds}) ) " +
//                    "AND ( (:#{#kcs.isEmpty()}) or ctsp.kichCo.idKichCo IN (:#{#kcs}) ) " +
//                    "AND ( (:#{#vls.isEmpty() }) or ctsp.vatLieu.idVatLieu IN (:#{#vls}) ) " +
//                    "AND ( (:#{#mss.isEmpty() }) or ctsp.mauSac.idMauSac IN (:#{#mss}) ) "
//                    +"AND ( (:#{#ten.isBlank()}) )" )
//    List<SanPham> getListSanPhamByCondition(@Param("ths") List<Integer> ths,
//                                            @Param("dms") List<Integer> dms,
//                                            @Param("dds") List<Integer> dds,
//                                            @Param("kcs") List<Integer> kcs,
//                                            @Param("vls") List<Integer> vls,
//                                            @Param("mss") List<Integer> mss,
//                                            @Param("ten") String ten
////                                            @Param("fromGia") List<Integer> fromGia,
////                                            @Param("toGia") List<Integer> toGia
//    );

    @Query(value = "SELECT  * FROM sanpham ORDER BY sanpham.id_san_pham DESC LIMIT 8", nativeQuery = true)
    List<SanPham> getSPnew();


    @Query(value = "SELECT h.chiTietSanPham.sanPham  FROM HoaDonChiTiet h where h.donHang.trangThaiDonHang = 3 group by h.chiTietSanPham.sanPham   ORDER BY  SUM(h.soLuong) DESC")
    List<SanPham> getSPchay();

    @Query(value = "SELECT s.listChiTietSanPham  FROM SanPham s where s.idSanPham = ?1")
    List<ChiTietSanPham> getCTSP(Integer idSanPham);

    /**
     * @param pageable
     * @return
     * product pagination
     */
//    @Query(value = """
//        SELECT sp
//        FROM SanPham sp
//    """)
//    Page<SanPham> getPageSanPham(Pageable pageable);

    @Query("SELECT sp FROM SanPham  sp WHERE UPPER(CONCAT(sp.idSanPham,' ', sp.tenSanPham, ' ',sp.thuongHieu,' ', sp.danhMuc, ' ',sp.moTaSanPham)) LIKE %?1%")
    public Page<SanPham> findAll(String keyword, Pageable pageable);

    //@Query("SELECT th FROM ThuongHieu th WHERE UPPER(CONCAT(th.idThuongHieu, ' ', th.tenThuongHieu, ' ', th.moTaThuongHieu)) LIKE %?1%")
    //    public Page<ThuongHieu> findAll(String keyword, Pageable pageable);


    public SanPham findByTenSanPham(String tenSanPham);



}
