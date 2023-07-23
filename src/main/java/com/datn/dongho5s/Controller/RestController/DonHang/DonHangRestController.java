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
import com.datn.dongho5s.Response.PaymentResponse;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @PostMapping("/them-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest){
        try {
            KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
            DonHang donHang = DonHang.builder()
                    .khachHang(khachHang)
                    .ngayTao(new Timestamp(System.currentTimeMillis()))
                    .trangThaiDonHang(1)
                    .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                    .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                    .diaChi(themDonHangRequest.getDiaChi())
                    .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                    .ghiChu(themDonHangRequest.getGhiChu())
                    .build();
            DonHang savedDonHang = donHangService.save(donHang);
            List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
            hdctService.saveAll(listHoaDonChiTiet);
            TaoDonHangRequestGHN requestGHN = TaoDonHangRequestGHN.builder()
                    .note(themDonHangRequest.getGhiChu())
                    .toName(khachHang.getTenKhachHang())
                    .toPhone(khachHang.getSoDienThoai())
                    .toAddress(themDonHangRequest.getDiaChi())
                    .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                    .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                    .soLuongSanPham(themDonHangRequest.getSoLuongSanPham())
                    .listItems(toListChiTietItem(listHoaDonChiTiet))
                    .build();
            ThemDonHangResponseGHN responseGHN = DonHangAPI.createOrder(requestGHN);
            return ResponseEntity.status(HttpStatus.OK).body(responseGHN);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

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

    private HashMap<Integer,String> getListTP (){
         return DiaChiCache.hashMapTinhThanh;
    }

    private HashMap<Integer,String> getListPX (Integer idTP) throws Exception {
        return DiaChiAPI.callGetPhuongXaAPI(idTP);
    }

    @GetMapping("/thanh-toan-vnpay")
    public ResponseEntity<PaymentResponse> thanhToanVNPAY(@RequestBody ThemDonHangRequest themDonHangRequest) throws IOException {

        KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
        DonHang donHang = DonHang.builder()
                .khachHang(khachHang)
                .ngayTao(new Timestamp(System.currentTimeMillis()))
                .trangThaiDonHang(0)
                .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                .diaChi(themDonHangRequest.getDiaChi())
                .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                .ghiChu(themDonHangRequest.getGhiChu())
                .build();
        DonHang savedDonHang = donHangService.save(donHang);
        List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
        hdctService.saveAll(listHoaDonChiTiet);

        Double amount = hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest());
        String vnp_Version = VNPayConfig.version;
        String vnp_Command = VNPayConfig.command;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
//        long amount = Integer.parseInt(request.getParameter("amount"))*100;
        String currCode = VNPayConfig.curr_code;
        String bank_code = VNPayConfig.bank_code;
        String vnp_TxnRef = String.valueOf(savedDonHang.getIdDonHang());
//        String vnp_OrderInfo =request.getParameter("vnp_OrderInfo");
        String vnp_OrderInfo = "vnp_OrderInfo";
        String orderType = request.getParameter(VNPayConfig.order_type);
        String location = VNPayConfig.location;
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_ReturnUrl = VNPayConfig.vnp_Returnurl;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount.intValue()));
        vnp_Params.put("vnp_CurrCode", currCode);
        vnp_Params.put("vnp_BankCode", bank_code);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", location);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("khachHangId", String.valueOf(themDonHangRequest.getKhachHangId()));
        vnp_Params.put("idQuanHuyen", String.valueOf(themDonHangRequest.getIdQuanHuyen()));
        vnp_Params.put("idPhuongXa", String.valueOf(themDonHangRequest.getIdPhuongXa()));
        vnp_Params.put("diaChi", themDonHangRequest.getDiaChi());
        vnp_Params.put("ghiChu", themDonHangRequest.getGhiChu());
        vnp_Params.put("soLuongSanPham", String.valueOf(themDonHangRequest.getSoLuongSanPham()));
        vnp_Params.put("phiVanChuyen", String.valueOf(themDonHangRequest.getPhiVanChuyen()));
        List<HoaDonChiTietRequest> list = themDonHangRequest.getListHoaDonChiTietRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        vnp_Params.put("listHoaDonChiTietRequest", jsonString);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return ResponseEntity.status(HttpStatus.OK).body(new PaymentResponse(paymentUrl));
    }

    @GetMapping("thong-tin-thanh-toan")
    public ResponseEntity<?> thongTinThanhToan() {
        try {

	/*  IPN URL: Record payment results from VNPAY
	Implementation steps:
	Check checksum
	Find transactions (vnp_TxnRef) in the database (checkOrderId)
	Check the payment status of transactions before updating (checkOrderStatus)
	Check the amount (vnp_Amount) of transactions before updating (checkAmount)
	Update results to Database
	Return recorded results to VNPAY
	*/

            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = (String) params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }

            // Check checksum
            String signValue = VNPayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                DonHang donhang = donHangService.getById(Integer.valueOf(request.getParameter("vnp_TxnRef")));
                if (donhang!= null) {
                            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                                donhang.setTrangThaiDonHang(1);
                                donHangService.save(donhang);
                                //TODO xử lý trừ số luojwgn sản phẩm
                                return ResponseEntity.status(HttpStatus.OK).body(new String("Thanh toán thành công"));
                            } else {
                                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Thanh toán không thành công"));
                            }
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new String("Không tìm thấy order của bạn"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Lỗi không xác định"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Lỗi không xác định"));
        }
    }

}
