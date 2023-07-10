package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThemDonHangRequest {
    private Integer khachHangId;
    private Date ngayTao;
    private List<ChiTietSanPhamRequest> listChiTietSanPhamRequest;
    private String diaChiGiaoHang;
    private String ghiChu;
}
