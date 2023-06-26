package com.datn.dongho5s.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ChiTietSanPham> listChiTietSanPham ;

}
