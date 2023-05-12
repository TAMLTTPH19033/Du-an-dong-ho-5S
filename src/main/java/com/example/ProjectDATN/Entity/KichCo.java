package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "kichco")
public class KichCo {
    @Id
    @Column(name = "IdKichCo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKichCo;

    @Column(name = "TenKichCo")
    private String tenKichCo;

    @Column(name = "MoTaKichCo")
    private String moTaKichCo;

    @Column(name = "NgayTaoKichCo")
    private java.sql.Date ngayTaoKichCo;

    @Column(name = "NgayCapNhapKichCo")
    private java.sql.Date ngayCapNhapKichCo;

}
