package com.datn.dongho5s.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimKiemRequest{
    @JsonProperty("thuongHieuId")
    private Integer thuongHieuId;
    @JsonProperty("danhMucId")
    private Integer danhMucId;
    @JsonProperty("sizeId")
    private Integer sizeId;
    @JsonProperty("mauSacId")
    private Integer mauSacId;
    @JsonProperty("vatLieuId")
    private Integer vatLieuId;
    @JsonProperty("dayDeoId")
    private Integer dayDeoId;
    @JsonProperty("giaSanPham")
    private Double giaSanPham;
}
