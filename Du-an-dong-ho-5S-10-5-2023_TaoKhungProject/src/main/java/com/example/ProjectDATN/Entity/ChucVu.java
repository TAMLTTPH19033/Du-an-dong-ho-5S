package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "chucvu")
public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdChucVu")
    private Integer idChucVu;

    @Column(name = "MaChucVu")
    private String maChucVu;

    @Column(name = "TenChucVu")
    private String tenChucVu;

    @Column(name = "TrangThai")
    private Integer trangThai;
}
