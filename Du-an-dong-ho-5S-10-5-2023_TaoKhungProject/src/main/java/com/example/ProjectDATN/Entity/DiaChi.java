package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "diachi")
public class DiaChi{
    @Id
    @Column(name = "IdDiaChi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDiaChi;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "MaBuuChinh")
    private Integer maBuuChinh;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
