package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime thoiGianTaoTaiKhoan;

    @Column(name = "TrangThaiTaiKhoan")
    private Integer trangThaiTaiKhoan;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "NgaySua")
    private LocalDateTime ngaySua;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "AnhAvatar")
    private Integer anhAvatar;

    @Column(name = "MoTa")
    private Integer moTa;

}
