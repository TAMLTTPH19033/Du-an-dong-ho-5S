package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "donhang")
public class DonHang {
    @Id
    @Column(name = "IdDonHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDonHang;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    private KhachHang khachHang;

    @Column(name = "NgayTao")
    private java.sql.Timestamp ngayTao;

    @Column(name = "NgayDatHang")
    private LocalDateTime ngayDatHang;

    @Column(name = "NgayGiaoHang")
    private LocalDateTime ngayGiaoHang;

    @Column(name = "TongTien")
    private Double tongTien;

    @Column(name = "TrangThaiDonHang")
    private Integer trangThaiDonHang;

    @Column(name = "DiaChiGiaoHang")
    private String diaChiGiaoHang;

    @Column(name = "PhiVanChuyen")
    private Double phiVanChuyen;

    @Column(name = "TenKhachHang")
    private Double tenKhachHang;

    @Column(name = "SoDienThoai")
    private Double soDienThoai;

    @Column(name = "GhiChu")
    private String ghiChu;

}
