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
@Table(name = "khachhang")
public class KhachHang {
    @Id
    @Column(name = "IdKhachHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKhachHang;

    @ManyToOne
    @JoinColumn(name = "IdDiaChi")
    private DiaChi diaChi;

    @Column(name = "TenKhachHang")
    private String tenKhachHang;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email")
    private String email;

    @Column(name = "NgaySinh")
    private String ngaySinh;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @Column(name = "SoTienDaChi")
    private Double soTienDaChi;

    @Column(name = "ThoiGianTaoTaiKhoan")
    private Timestamp thoiGianTaoTaiKhoan;

    @Column(name = "TrangThaiTaiKhoan")
    private Integer trangThaiTaiKhoan;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "NgaySua")
    private Date ngaySua;

    @Column(name = "TrangThai")
    private Integer trangThai;

}