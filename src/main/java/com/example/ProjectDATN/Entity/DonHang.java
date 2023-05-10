package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

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
    private java.sql.Timestamp ngayDatHang;

    @Column(name = "NgayGiaoHang")
    private java.sql.Timestamp ngayGiaoHang;

    @Column(name = "TongTien")
    private Double tongTien;

    @Column(name = "TrangThaiDonHang")
    private Integer trangThaiDonHang;

    @Column(name = "DiaChiGiaoHang")
    private String diaChiGiaoHang;

    @Column(name = "PhiVanChuyen")
    private Double phiVanChuyen;

    @Column(name = "GhiChu")
    private String ghiChu;

}
