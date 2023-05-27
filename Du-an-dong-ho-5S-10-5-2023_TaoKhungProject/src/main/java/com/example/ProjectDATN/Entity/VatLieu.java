package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "vatlieu")
public class VatLieu {
    @Id
    @Column(name = "IdVatLieu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVatLieu;

    @Column(name = "TenVatLieu")
    private String tenVatLieu;

    @Column(name = "MoTaVatLieu")
    private String moTaVatLieu;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "NgayCapNhap")
    private LocalDateTime ngayCapNhap;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
