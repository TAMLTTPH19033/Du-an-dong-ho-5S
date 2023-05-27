package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "chitietsanpham")
public class ChiTietSanPham {
    @Id
    @Column(name = "IdChiTietSanPham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChiTietSanPham;

    @ManyToOne
    @JoinColumn(name = "IdSanPham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IdDayDeo")
    private DayDeo dayDeo;

    @ManyToOne
    @JoinColumn(name = "IdKhuyenMai")
    private KhuyenMai khuyenMai;

    @ManyToOne
    @JoinColumn(name = "IdMauSac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "IdVatLieu")
    private VatLieu vatLieu;

    @ManyToOne
    @JoinColumn(name = "IdKichCo")
    private KichCo kichCo;

    @Column(name = "ChieuDaiDayDeo")
    private Double chieuDaiDayDeo;

    @Column(name = "DuongKinhMatDongHo")
    private Double duongKinhMatDongHo;

    @Column(name = "DoDayMatDongHo")
    private Double doDayMatDongHo;

    @Column(name = "DoChiuNuoc")
    private Integer doChiuNuoc;

    @Column(name = "SoKim")
    private Integer soKim;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
