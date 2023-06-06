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
import java.sql.Date;
import java.sql.Timestamp;

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
    private Timestamp ngayTao;

    @Column(name = "NgayDatHang")
    private Date ngayDatHang;

    @Column(name = "NgayGiaoHang")
    private Timestamp ngayGiaoHang;

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
