package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "NgayTao")
    private LocalDateTime NgayTao;

    @Column(name = "NgayBatDau")
    private LocalDateTime ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "NgaySua")
    private LocalDateTime ngaySua;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
