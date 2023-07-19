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
import com.datn.dongho5s.Request.ThemDonHangRequest;
import com.datn.dongho5s.Response.PaymentResponse;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
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
            ThemDonHangResponseGHN responseGHN = DonHangAPI.createOrder(requestGHN) ;
            return ResponseEntity.status(HttpStatus.OK).body(responseGHN);
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

    private List<ChiTietItemRequestGHN> toListChiTietItem (List<HoaDonChiTiet> listHDCT){
        List<ChiTietItemRequestGHN> result = new ArrayList<>();
        listHDCT.forEach(hdct->{
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
    public ResponseEntity<PaymentResponse> test() throws IOException {

        String vnp_Version = VNPayConfig.version;
        String vnp_Command = VNPayConfig.command;
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
//        long amount = Integer.parseInt(request.getParameter("amount"))*100;
        long amount = 1000000;
        String currCode = VNPayConfig.curr_code;
        String bank_code = VNPayConfig.bank_code;
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
//        String vnp_OrderInfo =request.getParameter("vnp_OrderInfo");
        String vnp_OrderInfo ="vnp_OrderInfo";
        String orderType = request.getParameter(VNPayConfig.order_type);
        String location = VNPayConfig.location;
        String vnp_IpAddr = VNPayConfig.getIpAddress(request);
        String vnp_ReturnUrl = VNPayConfig.vnp_Returnurl;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", currCode);
//        vnp_Params.put("vnp_BankCode", bank_code);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo + vnp_TxnRef);
//        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", location);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
//        vnp_Params.put("vnp_Bill_Mobile", request.getParameter("txt_billing_mobile"));
//        vnp_Params.put("vnp_Bill_Email", request.getParameter("txt_billing_email"));
//        String fullName = (request.getParameter("txt_billing_fullname")).trim();
//        if (fullName != null && !fullName.isEmpty()) {
//            int idx = fullName.indexOf(' ');
//            String firstName = fullName.substring(0, idx);
//            String lastName = fullName.substring(fullName.lastIndexOf(' ') + 1);
//            vnp_Params.put("vnp_Bill_FirstName", firstName);
//            vnp_Params.put("vnp_Bill_LastName", lastName);
//
//        }
//        vnp_Params.put("vnp_Bill_Address", request.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Bill_City", request.getParameter("txt_bill_city"));
//        vnp_Params.put("vnp_Bill_Country", request.getParameter("txt_bill_country"));
//        if (request.getParameter("txt_bill_state") != null && !request.getParameter("txt_bill_state").isEmpty()) {
//            vnp_Params.put("vnp_Bill_State", request.getParameter("txt_bill_state"));
//        }
//
//        // Invoice
//        vnp_Params.put("vnp_Inv_Phone", request.getParameter("txt_inv_mobile"));
//        vnp_Params.put("vnp_Inv_Email", request.getParameter("txt_inv_email"));
//        vnp_Params.put("vnp_Inv_Customer", request.getParameter("txt_inv_customer"));
//        vnp_Params.put("vnp_Inv_Address", request.getParameter("txt_inv_addr1"));
//        vnp_Params.put("vnp_Inv_Company", request.getParameter("txt_inv_company"));
//        vnp_Params.put("vnp_Inv_Taxcode", request.getParameter("txt_inv_taxcode"));
//        vnp_Params.put("vnp_Inv_Type", request.getParameter("cbo_inv_type"));
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
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

//        Mã kiểm tra (checksum) để đảm bảo dữ liệu của giao dịch không bị thay đổi trong quá trình chuyển từ VNPAY về Website TMĐT.
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return ResponseEntity.status(HttpStatus.OK).body(new PaymentResponse(paymentUrl));
    }

}
