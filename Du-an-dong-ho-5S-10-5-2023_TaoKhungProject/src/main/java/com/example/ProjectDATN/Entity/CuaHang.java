package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cuahang")
public class CuaHang {
    @Id
    @Column(name = "IdCuahang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCuahang;

    @Column(name = "MaCuaHang")
    private String maCuaHang;

    @Column(name = "TenCuaHang")
    private String tenCuaHang;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "ThanhPho")
    private String thanhPho;

    @Column(name = "QuocGia")
    private String quocGia;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
