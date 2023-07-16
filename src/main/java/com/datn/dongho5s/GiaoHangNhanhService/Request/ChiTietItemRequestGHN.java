package com.datn.dongho5s.GiaoHangNhanhService.Request;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietItemRequestGHN {
    private ChiTietSanPham ctsp;
    private Integer soLuong;
    private Double giaBan;
}
