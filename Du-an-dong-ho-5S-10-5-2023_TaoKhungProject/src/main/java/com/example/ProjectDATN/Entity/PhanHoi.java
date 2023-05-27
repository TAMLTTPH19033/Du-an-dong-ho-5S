package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "NoiDung")
    private String noiDung;

    @Column(name = "ThoiGian")
    private LocalDateTime thoiGian;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "DanhGia")
    private Integer danhGia;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NgaySua")
    private LocalDateTime ngaySua;

}
