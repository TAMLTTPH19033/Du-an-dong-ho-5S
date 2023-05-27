package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "thuonghieu")
public class ThuongHieu {
    @Id
    @Column(name = "IdThuongHieu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThuongHieu;

    @Column(name = "TenThuonghieu")
    private String tenThuonghieu;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "SoLuotTim")
    private String soLuotTim;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
