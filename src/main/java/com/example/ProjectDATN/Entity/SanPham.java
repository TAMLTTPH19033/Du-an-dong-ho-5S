package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sanpham")
public class SanPham {
    @Id
    @Column(name = "IdSanPham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSanPham;

    @ManyToOne
    @JoinColumn(name = "IdThuongHieu")
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "IdDanhMuc")
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "IdAnhSanPham")
    private AnhSanPham anhSanPham;

    @Column(name = "TenSanPham")
    private String tenSanPham;

    @Column(name = "MoTaSanPham")
    private String moTaSanPham;

    @Column(name = "GiaSanPham")
    private Double giaSanPham;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
