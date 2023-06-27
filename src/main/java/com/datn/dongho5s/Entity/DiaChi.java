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
import javax.persistence.Table;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diachi")
public class DiaChi {
    @Id
    @Column(name = "IdDiaChi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDiaChi;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "MaBuuChinh")
    private Integer maBuuChinh;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "TrangThaiMacDinh")
    private Integer trangThaiMacDinh;

}
