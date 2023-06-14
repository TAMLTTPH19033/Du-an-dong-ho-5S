package com.datn.dongho5s.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TimKiemResponse {
    private Integer sanPhamID;
    private String tenSanPham;
    private Double giaSanPham;
    private String linkAnh;
}
