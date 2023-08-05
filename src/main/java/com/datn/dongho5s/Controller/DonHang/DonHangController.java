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
import javax.servlet.http.HttpServletResponse;
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


    @GetMapping
    public String getForm(Model model,
                          HttpSession httpSession) {
        return findAll(1, model,httpSession);
    }

    @GetMapping("/page/{pageNum}")
    public String findAll(
            @PathVariable("pageNum") int pageNum,
            Model model,
            HttpSession httpSession
    ) {
        Page<DonHang> donHangs = donHangService.getAll(pageNum);

        model.addAttribute("list", donHangs.getContent());

        model.addAttribute("diaChiCache", new DiaChiCache());
        model.addAttribute("diaChiAPI", new DiaChiAPI());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", donHangs.getTotalPages());

//        httpSession.setAttribute("listDonHang",donHangs.getContent());

        return "admin/donhang/donhang";
    }

    @GetMapping("/search/date")
    public void searchByDateStartanDateEnd(
        HttpSession httpSession,
        Model model,
        HttpServletRequest httpServletRequest
    ) {
        String dateStart = httpServletRequest.getParameter("dateStart");
        String dateEnd = httpServletRequest.getParameter("dateEnd");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateStartParse = null;
        Date dateEndParse = null;

        try {
            dateStartParse = format.parse(dateStart);
            dateEndParse = format.parse(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<DonHang> lst = donHangService.findByNgayTao(dateStartParse,dateEndParse);

        model.addAttribute("list",lst);
//        return "admin/donhang/donhang";
    }

    @PostMapping("/update/{id}/trang-thai/{trangThai}")
    public String updateStatusDonHang(
            HttpSession session,
            @PathVariable("trangThai") int trangThai,
            @PathVariable("id") int id
    ) {
        DonHang donHang = donHangService.findById(id);
        donHang.setTrangThaiDonHang(trangThai);

        donHangService.updateTrangThaiDonHang(donHang);
        return "/don-hang";
    }
}

