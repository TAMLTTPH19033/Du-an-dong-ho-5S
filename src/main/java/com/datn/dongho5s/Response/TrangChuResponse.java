package com.datn.dongho5s.Response;

import com.datn.dongho5s.Entity.SanPham;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TrangChuResponse {
    private List<SanPham> listSPbanChay;
    private List<SanPham> listSPmoiNhat;
}