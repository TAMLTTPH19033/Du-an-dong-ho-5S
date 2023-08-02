package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.DonHangResponse;
import com.datn.dongho5s.Response.GiohangResponse;
import lombok.Data;

@Data
public class DonHangMapping {

    public  static DonHangResponse mapEntitytoResponse(DonHang donHang){
        DonHangResponse donHangResponse =  DonHangResponse.builder()
                .idDonHang(donHang.getIdDonHang())
                .maDonHang(donHang.getMaDonHang())
                .diaChi(donHang.getDiaChi())
                .ghiChu(donHang.getGhiChu())
                .idPhuongXa(donHang.getIdPhuongXa())
                .idQuanHuyen(donHang.getIdQuanHuyen())
                .idTinhThanh(donHang.getIdTinhThanh())
                .ngayTao(donHang.getNgayTao())
                .ngayGiaoHang(donHang.getNgayGiaoHang())
                .khachHang(donHang.getKhachHang())
                .phiVanChuyen(donHang.getPhiVanChuyen())
                .trangThaiDonHang(donHang.getTrangThaiDonHang())
                .tongTien(donHang.getTongTien())
                .hoaDonChiTiets(donHang.getListHoaDonChiTiet())
                .lyDo(donHang.getLyDo())
                .build();
        return donHangResponse;
    }


}
