package com.datn.dongho5s.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "khachhang")
public class KhachHang {
    @Id
    @Column(name = "id_khach_hang")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idKhachHang;

    @OneToMany( fetch = FetchType.EAGER,mappedBy = "khachHang", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<DiaChi> listDiaChi ;

    @Column(name = "Tten_khach_hang")
    private String tenKhachHang;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "email")
    private String email;

    @Column(name = "ngay_sinh")
    private Date ngaySinh;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh;

    @Column(name = "thoi_gian_tao_tai_khoan")
    private Timestamp thoiGianTaoTaiKhoan;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ngay_sua")
    private Date ngaySua;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
