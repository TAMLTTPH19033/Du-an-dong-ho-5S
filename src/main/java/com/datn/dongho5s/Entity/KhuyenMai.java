package com.datn.dongho5s.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "khuyenmai")
public class KhuyenMai {
    @Id
    @Column(name = "IdKhuyenMai")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKhuyenMai;

    @Column(name = "TenKhuyenMai")
    private String tenKhuyenMai;

    @Column(name = "MoTaKhuyenMai")
    private String moTaKhuyenMai;

    @Column(name = "NgayBatDau")
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "NgaySua")
    private Date ngaySua;

}
