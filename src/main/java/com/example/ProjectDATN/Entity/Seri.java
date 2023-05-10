package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "seri")
public class Seri {
    @Id
    @Column(name = "IdSeri")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeri;

    @Column(name = "IdImei")
    private Integer idImei;

    @ManyToOne
    @JoinColumn(name = "ChiTietSanPhamId")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "NgayNhap")
    private java.sql.Timestamp ngayNhap;

    @Column(name = "NgayBan")
    private java.sql.Timestamp ngayBan;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
