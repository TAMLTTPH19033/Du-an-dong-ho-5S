package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Integer>{

    @Query(nativeQuery = true,value = """
        SELECT *
        FROM chitietsanpham c JOIN sanpham sp ON c.id_san_pham = sp.id_san_pham
        WHERE sp.ma_san_pham = ?1
    """)
    public Page<ChiTietSanPham> findByMaSP(String maSanPham, Pageable pageable);

    @Query("""
        SELECT ctsp
        FROM ChiTietSanPham ctsp
    """)
    public Page<ChiTietSanPham> findAllHung(Pageable pageable);
           
    @Query("SELECT ctsp FROM ChiTietSanPham  ctsp WHERE UPPER(CONCAT(ctsp.idChiTietSanPham,' ', ctsp.maChiTietSanPham, ' ',ctsp.dayDeo,' ', ctsp.khuyenMai,' ', ctsp.mauSac,' ',ctsp.sanPham)) LIKE %?1%")
    public Page<ChiTietSanPham> findAll(String keyword,Pageable pageable);

    @Query(value = """
        UPDATE ChiTietSanPham c
        SET c.soLuong = c.soLuong - ?1
        WHERE c.idChiTietSanPham = ?2
    """)
    @Modifying
    public void updateSoLuongCTSPById(int soLuong,int id);
    @Query(value = """
        UPDATE ChiTietSanPham c
        SET c.soLuong = c.soLuong + ?1
        WHERE c.idChiTietSanPham = ?2
    """)
    @Modifying
    public void updateSoLuongFromHDCT(int soLuong,int id);

    ChiTietSanPham findByMaChiTietSanPham(String ma);


    ChiTietSanPham findBySanPham_TenSanPhamAndDayDeo_TenDayDeoAndMauSac_TenMauSacAndKichCo_TenKichCoAndVatLieu_TenVatLieu(
            String tenSanPham, String tenDayDeo, String tenMauSac, String tenKichCo, String tenVatLieu);

    ChiTietSanPham findByIdChiTietSanPham(Integer idChiTietSanPham);
}
