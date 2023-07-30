package com.datn.dongho5s.Controller.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Configure.VNPayConfig;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.APIResponseEntity.ThemDonHangResponseGHN;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.GiaoHangNhanhService.DonHangAPI;
import com.datn.dongho5s.GiaoHangNhanhService.Request.ChiTietItemRequestGHN;
import com.datn.dongho5s.GiaoHangNhanhService.Request.TaoDonHangRequestGHN;
import com.datn.dongho5s.Request.ThemDonHangRequest;
import com.datn.dongho5s.Response.VNPayUrlResponse;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RequestMapping("/don-hang")
@Controller
public class DonHangController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    HoaDonChiTietService hdctService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    HttpServletRequest request;

    //
//    @GetMapping
//    public String getForm(Model model,
//                          HttpSession httpSession) {
//        return findAll(1, model,httpSession);
//    }
//
//    @GetMapping("/page/{pageNum}")
//    public String findAll(
//            @PathVariable("pageNum") int pageNum,
//            Model model,
//            HttpSession httpSession
//    ) {
//        Page<DonHang> donHangs = donHangService.getAll(pageNum);
//
//        model.addAttribute("list", donHangs.getContent());
//
//        model.addAttribute("diaChiCache", new DiaChiCache());
//        model.addAttribute("diaChiAPI", new DiaChiAPI());
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", donHangs.getTotalPages());
//
//        httpSession.setAttribute("listDonHang",donHangs.getContent());
//
//        return "donhang/donhang";
//    }
//
//    @PostMapping("/search/date")
//    public String searchByDateStartanDateEnd(
//        HttpSession httpSession,
//        Model model,
//        HttpServletRequest httpServletRequest
//    ) {
//        String dateStart = httpServletRequest.getParameter("dateStart");
//        String dateEnd = httpServletRequest.getParameter("dateEnd");
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date dateStartParse = null;
//        Date dateEndParse = null;
//
//        try {
//            dateStartParse = format.parse(dateStart);
//            dateEndParse = format.parse(dateEnd);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        List<DonHang> lst = donHangService.findByNgayTao(dateStartParse,dateEndParse);
//
//        System.out.println("SO luong la:"+ lst.size());
//
//        httpSession.setAttribute("list",lst);
//        return "redirect:/don-hang";
//    }
//
////    @GetMapping("/get/{id}")
////    public void getById(
////            @PathVariable("id") int id,
////            HttpSession session
////    ) {
////        System.out.println(id);
////        session.setAttribute("donHang", donHangService.findById(id));
////    }
////
////    @PutMapping("/update/{trangThai}")
////    public String updateStatusDonHang(
////            HttpSession session,
////            @PathVariable("trangThai") int trangThai
////    ) {
////        DonHang donHang = (DonHang) session.getAttribute("donHang");
////        donHang.setTrangThaiDonHang(trangThai);
////        donHangService.updateTrangThaiDonHang(donHang);
////        return "donhang/donhang";
////    }
//}
    @PostMapping("/them-don-hang")
    public RedirectView taoDonHang(@RequestBody ThemDonHangRequest themDonHangRequest) {
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
            return new RedirectView("http://localhost:8080/index#/success");
        } catch (Exception e) {
            return new RedirectView("http://localhost:8080/index#/fail");
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
    public RedirectView thongTinThanhToan() {
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
                    return new RedirectView("http://localhost:8080/index#/success");
                } else {
                    System.out.println("Không thành công");
                    return new RedirectView("http://localhost:8080/index#/fail");
                }
            } else {
                System.out.println("Không tìm thấy order");
                return new RedirectView("http://localhost:8080/index#/fail");
            }
        } catch (Exception e) {
            System.out.println("k xdc");
            return new RedirectView("http://localhost:8080/index#/fail");
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