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
