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
@Table(name = "phanhoi")
public class PhanHoi {
    @Id
    @Column(name = "IdPhanHoi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPhanHoi;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "IdGioHang")
    private GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "IdChiTietSanPham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "NoiDungPhanHoi")
    private String noiDungPhanHoi;

    @Column(name = "ThoiGianPhanHoi")
    private java.sql.Timestamp thoiGianPhanHoi;

    @Column(name = "TrangThaiPhanHoi")
    private Integer trangThaiPhanHoi;

    @Column(name = "DanhGia")
    private Integer danhGia;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "NgayTao")
    private java.sql.Timestamp ngayTao;

    @Column(name = "NgaySua")
    private java.sql.Timestamp ngaySua;

}
