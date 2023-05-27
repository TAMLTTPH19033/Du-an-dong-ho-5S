package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "nhanvien")
public class NhanVien {
    @Id
    @Column(name = "IdNhanVien")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNhanVien;

    @Column(name = "MaNhanVien")
    private String maNhanVien;

    @ManyToOne
    @JoinColumn(name = "IdCuaHang")
    private CuaHang cuaHang;

    @ManyToOne
    @JoinColumn(name = "IdChucVu")
    private ChucVu chucVu;

    @Column(name = "Ho")
    private String ho;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @Column(name = "NgaySinh")
    private String ngaySinh;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email")
    private String email;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "Anh")
    private String anh;
}
