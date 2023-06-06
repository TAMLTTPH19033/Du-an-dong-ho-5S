package com.datn.dongho5s.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "chitietgiohang")
public class ChiTietGioHang {
    @Id
    @Column(name = "IdChiTietGioHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChiTietGioHang;

    @ManyToOne
    @JoinColumn(name = "IdDonHang")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "IdGioHang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "IdChiTietSanPham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "SoLuongSanPham")
    private Integer soLuongSanPham;

    @Column(name = "GiaBan")
    private Double giaBan;

    @Column(name = "ThanhTien")
    private Double thanhTien;

    @Column(name = "NgayTao")
    private java.sql.Timestamp ngayTao;

    @Column(name = "GhiChu")
    private String ghiChu;


}
