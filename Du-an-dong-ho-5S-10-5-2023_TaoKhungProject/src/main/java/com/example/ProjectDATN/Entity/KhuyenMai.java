package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @Column(name = "IdKhuyenMai")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKhuyenMai;

    @Column(name = "TenKhuyenMai")
    private String tenKhuyenMai;

    @Column(name = "MaKhuyenMai")
    private String maKhuyenMai;

    @Column(name = "ChietKhau")
    private Double chietKhau;

    @Column(name = "MoTaKhuyenMai")
    private String moTaKhuyenMai;

    @Column(name = "NgayBatDau")
    private java.sql.Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private java.sql.Date ngayKetThuc;

    @Column(name = "NgaySua")
    private java.sql.Date ngaySua;

}
