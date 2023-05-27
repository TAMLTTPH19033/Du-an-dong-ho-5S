package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "anhsanpham")
public class AnhSanPham {
    @Id
    @Column(name = "IdAnhSanPham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnhSanPham;

    @Column(name = "LinkAnh")
    private String linkAnh;

    @Column(name = "TenAnh")
    private String tenAnh;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
