package com.datn.dongho5s.Controller.RestController.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Configure.VNPayConfig;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.ThemDonHangResponseGHN;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.GiaoHangNhanhService.DonHangAPI;
import com.datn.dongho5s.GiaoHangNhanhService.Request.ChiTietItemRequestGHN;
import com.datn.dongho5s.GiaoHangNhanhService.Request.PhiVanChuyenRequest;
import com.datn.dongho5s.GiaoHangNhanhService.Request.TaoDonHangRequestGHN;
import com.datn.dongho5s.Request.HoaDonChiTietRequest;
import com.datn.dongho5s.Request.ThemDonHangRequest;
import com.datn.dongho5s.Response.VNPayUrlResponse;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

@RestController
@RequestMapping("/don-hang")
public class DonHangRestController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    DonHangService donHangService;
    @Autowired
    HoaDonChiTietService hdctService;
    @Autowired
    HttpServletRequest request;

//    @PostMapping("/them-don-hang")
//    public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest) {
//        try {
//            System.out.println(themDonHangRequest.toString());
//            KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
//            DonHang donHang = DonHang.builder()
//                    .khachHang(khachHang)
//                    .ngayTao(new Timestamp(System.currentTimeMillis()))
//                    .trangThaiDonHang(1)
//                    .idTinhThanh(themDonHangRequest.getIdTinhThanh())
//                    .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
//                    .idPhuongXa(themDonHangRequest.getIdPhuongXa())
//                    .diaChi(themDonHangRequest.getDiaChi())
//                    .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
//                    .ghiChu(themDonHangRequest.getGhiChu())
//                    .build();
//            DonHang savedDonHang = donHangService.save(donHang);
//            List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
//            hdctService.saveAll(listHoaDonChiTiet);
//            TaoDonHangRequestGHN requestGHN = TaoDonHangRequestGHN.builder()
//                    .note(themDonHangRequest.getGhiChu())
//                    .toName(khachHang.getTenKhachHang())
//                    .toPhone(khachHang.getSoDienThoai())
//                    .toAddress(themDonHangRequest.getDiaChi())
//                    .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
//                    .idPhuongXa(themDonHangRequest.getIdPhuongXa())
//                    .soLuongSanPham(themDonHangRequest.getSoLuongSanPham())
//                    .listItems(toListChiTietItem(listHoaDonChiTiet))
//                    .build();
//            ThemDonHangResponseGHN responseGHN = DonHangAPI.createOrder(requestGHN);
//            System.out.println(responseGHN.toString());
//            return ResponseEntity.status(HttpStatus.OK).body(responseGHN);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @PostMapping("/tinh-phi-van-chuyen")
    public ResponseEntity<?> getPhiVanChuyen(@RequestBody PhiVanChuyenRequest phiVanChuyenRequest) {
        try {
            Integer fee = DonHangAPI.getFee(phiVanChuyenRequest);
            return ResponseEntity.status(HttpStatus.OK).body(fee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private List<ChiTietItemRequestGHN> toListChiTietItem(List<HoaDonChiTiet> listHDCT) {
        List<ChiTietItemRequestGHN> result = new ArrayList<>();
        listHDCT.forEach(hdct -> {
            ChiTietItemRequestGHN item = ChiTietItemRequestGHN.builder()
                    .giaBan(hdct.getGiaBan())
                    .soLuong(hdct.getSoLuong())
                    .ctsp(hdct.getChiTietSanPham())
                    .name(hdct.getChiTietSanPham().getSanPham().getTenSanPham())
                    .build();
            result.add(item);
        });
        return result;
    }

}
