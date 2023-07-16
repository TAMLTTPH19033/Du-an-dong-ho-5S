package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThemDonHangRequest {
    private Integer khachHangId;
    private List<HoaDonChiTietRequest> listHoaDonChiTietRequest;
    private Integer idQuanHuyen;
    private Integer idPhuongXa;
    private String diaChi;
    private String ghiChu;
    private Integer soLuongSanPham;
}
