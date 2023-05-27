package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

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

    @Column(name = "MaMau")
    private String maMau;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name = "Anh")
    private String anh;

}
