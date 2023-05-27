package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "danhmuc")
public class DanhMuc {
    @Id
    @Column(name = "IdDanhMuc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDanhMuc;

    @Column(name = "TenDanhMuc")
    private String tenDanhMuc;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "SoLuotTim")
    private String soLuotTim;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
