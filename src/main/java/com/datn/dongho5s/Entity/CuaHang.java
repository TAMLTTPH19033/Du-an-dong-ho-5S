package com.datn.dongho5s.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
