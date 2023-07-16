package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {
    @Id
    @Column(name = "id_hoa_don_chi_tiet")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHoaDonChiTiet;

    @ManyToOne
    @JoinColumn(name = "id_don_hang")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "id_chi_tiet_san_pham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "ngay_tao")
    private Timestamp ngayTao;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Column(name = "gia_ban")
    private Integer giaBan;


}
