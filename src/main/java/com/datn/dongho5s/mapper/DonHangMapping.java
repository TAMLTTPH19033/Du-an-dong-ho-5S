package com.datn.dongho5s.mapper;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.GioHang;
import com.datn.dongho5s.Request.GioHangRequest;
import com.datn.dongho5s.Response.DonHangResponse;
import com.datn.dongho5s.Response.GiohangResponse;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Data
public class DonHangMapping {

    public  static DonHangResponse mapEntitytoResponse(DonHang donHang){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);

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
                .ngayCapNhap(df.format(donHang.getNgayCapNhap()))
                .build();
        return donHangResponse;
    }


}
