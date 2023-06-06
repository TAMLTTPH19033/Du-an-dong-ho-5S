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
@Table(name = "thuonghieu")
public class ThuongHieu {
    @Id
    @Column(name = "IdThuongHieu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idThuongHieu;

    @Column(name = "TenThuonghieu")
    private String tenThuonghieu;

    @Column(name = "MoTaThuongHieu")
    private String moTaThuongHieu;

}
