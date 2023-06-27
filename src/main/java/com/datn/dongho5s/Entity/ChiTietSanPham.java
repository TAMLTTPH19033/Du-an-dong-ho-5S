package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "GiaSanPham")
    private Double giaSanPham;

    @Column(name = "SoLuong")
    private Integer soLuong;
}
