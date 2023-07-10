package com.datn.dongho5s.Controller.RestController.DonHang;

import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.DonHangAPI;
import com.datn.dongho5s.GiaoHangNhanhService.Request.PhiVanChuyenRequest;
import com.datn.dongho5s.Request.ThemDonHangRequest;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/don-hang")
public class DonHangRestController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    DonHangService donHangService;
    @Autowired
    HoaDonChiTietService hdctService;

    @PostMapping("/them-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest){
        try {
            KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
            DonHang donHang = DonHang.builder()
                    .khachHang(khachHang)
                    .ngayTao(new Timestamp(System.currentTimeMillis()))
                    .trangThaiDonHang(1)
                    .diaChiGiaoHang(themDonHangRequest.getDiaChiGiaoHang())
                    .phiVanChuyen((double) 0)
                    .ghiChu(themDonHangRequest.getGhiChu())
                    .build();
            DonHang savedDonHang = donHangService.save(donHang);
            hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListChiTietSanPhamRequest(), savedDonHang.getIdDonHang());
            return ResponseEntity.status(HttpStatus.OK).body(savedDonHang.getNgayTao());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/tinh-phi-van-chuyen")
    public ResponseEntity<?> getPhiVanChuyen(@RequestBody PhiVanChuyenRequest phiVanChuyenRequest){
        try {
            Integer fee = DonHangAPI.getFee(phiVanChuyenRequest);
            return ResponseEntity.status(HttpStatus.OK).body(fee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
