package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {
    @Id
    @Column(name = "IdHoaDonChiTiet")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHoaDonChiTiet;

    @ManyToOne
    @JoinColumn(name = "IdDonHang")
    private DonHang donHang;

    @ManyToOne
    @JoinColumn(name = "IdChiTietSanPham")
    private ChiTietSanPham chiTietSanPham;

    @Column(name = "NgayTao")
    private LocalDateTime ngayTao;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "GiaBan")
    private Integer giaBan;

    @Column(name = "ThanhTien")
    private Double thanhTien;

}
