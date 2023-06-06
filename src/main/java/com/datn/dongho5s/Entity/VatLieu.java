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

}
