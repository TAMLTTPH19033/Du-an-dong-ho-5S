package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class HoaDonAdminRequest {
    int idHoaDon;
    String maHoaDon;
    String sdt;
    String tenKhachHang;
    Date ngayTao;
    Double tongTien;
    Double giamGia;
    Double tongTienDonHang;
}
