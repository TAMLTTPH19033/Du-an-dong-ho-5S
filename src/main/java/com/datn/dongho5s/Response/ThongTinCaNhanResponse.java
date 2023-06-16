package com.datn.dongho5s.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ThongTinCaNhanResponse {
    private Integer id;
    private String tenKhachHang;
    private String soDienThoai;
    private String email;
    private Integer gioiTinh;
    private String diaChi;
    private Date ngaySinh;
}
