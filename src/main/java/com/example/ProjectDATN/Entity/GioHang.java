package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "giohang")
public class GioHang {
    @Id
    @Column(name = "IdGioHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGioHang;

    @ManyToOne
    @JoinColumn(name = "IdNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "IdKhachHang")
    private KhachHang khachHang;

    @Column(name = "NgayTaoGioHang")
    private java.sql.Timestamp ngayTaoGioHang;

    @Column(name = "TrangThaiGioHang")
    private Integer trangThaiGioHang;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "ThoiGianCapNhapGioHang")
    private java.sql.Timestamp thoiGianCapNhapGioHang;

    @Column(name = "NgaySua")
    private java.sql.Date ngaySua;

}
