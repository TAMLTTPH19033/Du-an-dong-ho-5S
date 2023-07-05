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
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "donhang")
public class DonHang {
    @Id
    @Column(name = "id_don_hang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDonHang;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Column(name = "ngay_tao")
    private Date ngayTao;

    @Column(name = "ngay_giao_hang")
    private Date ngayGiaoHang;

    @Column(name = "tong_tien")
    private Double tongTien;

    @Column(name = "trang_thai_don_hang")
    private Integer trangThaiDonHang;

    @Column(name = "dia_chi_giao_hang")
    private String diaChiGiaoHang;

    @Column(name = "phi_van_chuyen")
    private Double phiVanChuyen;

    @Column(name = "ghi_chu")
    private String ghiChu;

}
