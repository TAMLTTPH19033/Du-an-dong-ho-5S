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
@Table(name = "mausac")
public class MauSac {
    @Id
    @Column(name = "IdMauSac")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMauSac;

    @Column(name = "TenMauSac")
    private String tenMauSac;

}
