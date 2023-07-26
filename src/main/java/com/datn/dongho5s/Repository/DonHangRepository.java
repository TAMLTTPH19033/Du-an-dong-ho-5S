package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.PhanHoi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {

    @Query(value = "select hdct.donHang from HoaDonChiTiet hdct where hdct.donHang.khachHang.idKhachHang = ?1 and hdct.chiTietSanPham.sanPham.idSanPham = ?2")
    Optional<List<DonHang>> findDonHang(Integer idKhachHang, Integer idSanPham);

    @Query(value = """
                SELECT d
                FROM DonHang d
            """)
    Page<DonHang> findAll(Pageable pageable);

    @Query(value = """
                SELECT d
                FROM DonHang d
                WHERE d.ngayTao  BETWEEN :dateStart AND :dateEnd
            """)
    List<DonHang> findByNgayTao(
            @Param("dateStart") Date dateStart,
            @Param("dateEnd") Date dateEnd
    );

    @Query(value = """
                SELECT d.tongTien
                FROM DonHang d
                WHERE d.idDonHang = ?1
            """)
    Double findTongTienByIdDonHang(int id);

    DonHang findByIdDonHang(int id);

    @Transactional
    @Modifying
    @Query(value = """
                UPDATE DonHang d
                SET d.trangThaiDonHang = :#{#donHang.trangThaiDonHang}
                WHERE d.idDonHang = :#{#donHang.idDonHang}
            """)
    void updateTrangThaiDonHang(@Param("donHang") DonHang donHang);
}
