package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "khachhang")
public class KhachHang {
    @Id
    @Column(name = "IdKhachHang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKhachHang;

    @ManyToOne
    @JoinColumn(name = "IdDiaChi")
    private DiaChi diaChi;

    @Column(name = "TenKhachHang")
    private String tenKhachHang;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email")
    private String email;

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @Column(name = "ThoiGianTaoTaiKhoan")
    private Timestamp thoiGianTaoTaiKhoan;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "NgaySua")
    private Date ngaySua;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
