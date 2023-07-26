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

    @PostMapping("/them-don-hang")
    public ResponseEntity<?> taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest) {
        try {
            System.out.println(themDonHangRequest.toString());
            KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
            DonHang donHang = DonHang.builder()
                    .khachHang(khachHang)
                    .ngayTao(new Timestamp(System.currentTimeMillis()))
                    .trangThaiDonHang(1)
                    .idTinhThanh(themDonHangRequest.getIdTinhThanh())
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
            System.out.println(responseGHN.toString());
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

    private HashMap<Integer, String> getListTP() {
        return DiaChiCache.hashMapTinhThanh;
    }

    private HashMap<String, String> getListPX(Integer idTP) throws Exception {
        return DiaChiAPI.callGetPhuongXaAPI(idTP);
    }

    @PostMapping("/thanh-toan-vnpay")
    public ResponseEntity<VNPayUrlResponse> thanhToanVNPAY(@RequestBody ThemDonHangRequest themDonHangRequest) throws IOException {

        KhachHang khachHang = khachHangService.findKhachHangById(themDonHangRequest.getKhachHangId());
        DonHang donHang = DonHang.builder()
                .khachHang(khachHang)
                .ngayTao(new Timestamp(System.currentTimeMillis()))
                .trangThaiDonHang(0)
                .idTinhThanh(themDonHangRequest.getIdTinhThanh())
                .idQuanHuyen(themDonHangRequest.getIdQuanHuyen())
                .idPhuongXa(themDonHangRequest.getIdPhuongXa())
                .diaChi(themDonHangRequest.getDiaChi())
                .phiVanChuyen(themDonHangRequest.getPhiVanChuyen())
                .ghiChu(themDonHangRequest.getGhiChu())
                .build();
        DonHang savedDonHang = donHangService.save(donHang);
        List<HoaDonChiTiet> listHoaDonChiTiet = hdctService.convertToListHoaDonChiTiet(themDonHangRequest.getListHoaDonChiTietRequest(), savedDonHang.getIdDonHang());
        hdctService.saveAll(listHoaDonChiTiet);

        Double amount = hdctService.getTongGia(themDonHangRequest.getListHoaDonChiTietRequest()) * 100;
        String vnp_Version = VNPayConfig.version;
        String vnp_Command = VNPayConfig.command;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        String currCode = VNPayConfig.curr_code;
        String bank_code = VNPayConfig.bank_code;
        String vnp_TxnRef = String.valueOf(savedDonHang.getIdDonHang());
//        String vnp_OrderInfo = "Thanh Toán đơn hàng: "+vnp_TxnRef;
//        String orderType = request.getParameter(VNPayConfig.order_type);
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
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", location);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

//        String fullName = (khachHang.getTenKhachHang()).trim();
//        if (fullName != null && !fullName.isEmpty()) {
//            int idx = fullName.indexOf(' ');
//            if(idx ==-1){
//                vnp_Params.put("vnp_Bill_LastName", fullName);
//            }else {
//                String firstName = fullName.substring(0, idx);
//                String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
//                vnp_Params.put("vnp_Bill_FirstName", firstName);
//                vnp_Params.put("vnp_Bill_LastName", lastName);
//            }
//        }
//        vnp_Params.put("vnp_Inv_Phone", khachHang.getSoDienThoai());
//        vnp_Params.put("khachHangId", String.valueOf(themDonHangRequest.getKhachHangId()));
//        vnp_Params.put("vnp_Bill_Country", String.valueOf(themDonHangRequest.getIdQuanHuyen()));
//        vnp_Params.put("vnp_Bill_City", String.valueOf(themDonHangRequest.getIdPhuongXa()));
//        vnp_Params.put("vnp_Bill_Address", themDonHangRequest.getDiaChi());
//        vnp_Params.put("ghiChu", themDonHangRequest.getGhiChu());
//        vnp_Params.put("soLuongSanPham", String.valueOf(themDonHangRequest.getSoLuongSanPham()));
//        vnp_Params.put("phiVanChuyen", String.valueOf(themDonHangRequest.getPhiVanChuyen()));
//        List<HoaDonChiTietRequest> list = themDonHangRequest.getListHoaDonChiTietRequest();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonString = null;
//        try {
//            jsonString = objectMapper.writeValueAsString(list);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        vnp_Params.put("listHoaDonChiTietRequest", jsonString);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//        cld.add(Calendar.MINUTE, 15);
//        String vnp_ExpireDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
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
        System.out.println(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(new VNPayUrlResponse(paymentUrl));
    }

    @GetMapping("/thong-tin-thanh-toan")
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
            System.out.println(request.getParameter("vnp_ReturnUrl"));
            fields.forEach((key, value) -> System.out.printf("%s: %s%n", key, value));
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            DonHang donhang = donHangService.getById(Integer.valueOf(request.getParameter("vnp_TxnRef")));
            if (donhang != null) {
                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                    System.out.println("Thanh toán thành công nè");
                    donhang.setTrangThaiDonHang(1);
                    donHangService.save(donhang);
                    TaoDonHangRequestGHN donHangRequestGHN = createGHNRequest(donhang);
                    ThemDonHangResponseGHN responseGHN = DonHangAPI.createOrder(donHangRequestGHN);
                    System.out.println(responseGHN);
//                                //TODO xử lý trừ số luojwgn sản phẩm
                    return ResponseEntity.status(HttpStatus.OK).body(responseGHN);
                } else {
                    System.out.println("Không thành công");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Thanh toán không thành công"));
                }
            } else {
                System.out.println("Không tìm thấy order");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Không tìm thấy order của bạn"));
            }
        } catch (Exception e) {
            System.out.println("k xdc");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String("Lỗi không xác định"));
        }
    }

    private TaoDonHangRequestGHN createGHNRequest(DonHang donhang) {
        try {
            List<HoaDonChiTiet> listHDCT = hdctService.getByHoaDonId(donhang);
            Integer soLuong = 0;
            for (HoaDonChiTiet item : listHDCT) {
                soLuong += item.getSoLuong();
            }
            TaoDonHangRequestGHN requestGHN = TaoDonHangRequestGHN.builder()
                    .note(donhang.getGhiChu())
                    .toName(donhang.getKhachHang().getTenKhachHang())
                    .toPhone(donhang.getKhachHang().getSoDienThoai())
                    .toAddress(donhang.getDiaChi())
                    .idQuanHuyen(donhang.getIdQuanHuyen())
                    .idPhuongXa(donhang.getIdPhuongXa())
                    .soLuongSanPham(soLuong)
                    .listItems(toListChiTietItem(listHDCT))
                    .build();
            return requestGHN;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
