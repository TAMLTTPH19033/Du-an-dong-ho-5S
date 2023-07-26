package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query(value = """
                SELECT h
                FROM HoaDonChiTiet h JOIN DonHang dh ON h.donHang.idDonHang = dh.idDonHang
                WHERE h.donHang.idDonHang = ?1
            """)
    public List<HoaDonChiTiet> findHDCTBYIdDonHang(int id);

    List<HoaDonChiTiet> findByDonHang(DonHang donHang);
}
