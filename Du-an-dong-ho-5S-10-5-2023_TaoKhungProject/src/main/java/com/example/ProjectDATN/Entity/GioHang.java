package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime ngayTaoGioHang;

    @Column(name = "TrangThaiGioHang")
    private Integer trangThaiGioHang;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "ThoiGianCapNhapGioHang")
    private LocalDateTime thoiGianCapNhapGioHang;

    @Column(name = "NgaySua")
    private LocalDateTime ngaySua;

}
