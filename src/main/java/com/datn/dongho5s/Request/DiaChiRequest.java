package com.datn.dongho5s.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaChiRequest {
    private Integer idDiaChi;
    private String diaChi;
    private Integer maBuuChinh;
    private String soDienThoai;
    private String ghiChu;
    private Integer trangThaiMacDinh;
}
