package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "kichco")
public class KichCo {
    @Id
    @Column(name = "IdKichCo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKichCo;

    @Column(name = "MaKichCo")
    private String maKichCo;

    @Column(name = "TenKichCo")
    private String tenKichCo;

    @Column(name = "MoTaKichCo")
    private String moTaKichCo;

    @Column(name = "NgayTaoKichCo")
    private LocalDateTime ngayTaoKichCo;

    @Column(name = "NgayCapNhapKichCo")
    private LocalDateTime ngayCapNhapKichCo;

    @Column(name = "TrangThai")
    private Integer TrangThai;

}
