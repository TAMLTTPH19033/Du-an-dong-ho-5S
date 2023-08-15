package com.datn.dongho5s.Controller.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Configure.VNPayConfig;
import com.datn.dongho5s.Entity.ChiTietSanPham;
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
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import com.datn.dongho5s.Service.KhachHangService;
import com.datn.dongho5s.Utils.TrangThaiDonHang;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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


@RequestMapping("/admin/don-hang")
@Controller
@Slf4j
public class DonHangController {

    @Autowired
    KhachHangService khachHangService;
    @Autowired
    HoaDonChiTietService hdctService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ChiTietSanPhamService ctspService;

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
            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            DonHang donhang = donHangService.getById(Integer.valueOf(request.getParameter("vnp_TxnRef")));
            if (donhang != null) {
                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                    System.out.println("Thanh toán thành công ");
                    donhang.setTrangThaiDonHang(TrangThaiDonHang.CHO_XAC_NHAN);
                    donHangService.save(donhang);
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
                    .phuongThuc(donhang.getPhuongThuc())
                    .thanhTien(donhang.getTongTien())
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

    @GetMapping("/update/{id}/trang-thai/{trangThai}")
    public String updateStatusDonHang(
            HttpSession session,
            @PathVariable("trangThai") int trangThai,
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        DonHang donHang = donHangService.findById(id);
        if(trangThai == TrangThaiDonHang.DANG_CHUAN_BI){
            try {
                TaoDonHangRequestGHN donHangRequestGHN = createGHNRequest(donHang);
                Integer code = DonHangAPI.createOrder(donHangRequestGHN);
                if(code != 200){
                    log.error("Lỗi gửi Giao Hàng nhanh code {}", code);
                    redirectAttributes.addFlashAttribute("error","Lỗi hệ thống giao hàng nhanh");
                    return "redirect:/admin/don-hang";
                }
            } catch (Exception e) {
                log.error("Lỗi gửi Giao Hàng nhanh {}", e);
                redirectAttributes.addFlashAttribute("error","Lỗi request giao hàng nhanh");
                return "redirect:/admin/don-hang";
            }
        }else if(trangThai == TrangThaiDonHang.DANG_GIAO){
            donHang.setTrangThaiDonHang(trangThai);
            donHang.setNgayCapNhap(new Date());
            List<HoaDonChiTiet> listHDCT = donHang.getListHoaDonChiTiet();
            listHDCT.forEach(item->{
                ChiTietSanPham ctsp = item.getChiTietSanPham();
                ctsp.setSoLuong(item.getChiTietSanPham().getSoLuong()-item.getSoLuong());
                ctspService.update(ctsp);
            });
        }


//        TaoDonHangRequestGHN donHangRequestGHN = createGHNRequest(donhang);
//        ThemDonHangResponseGHN responseGHN = DonHangAPI.createOrder(donHangRequestGHN);
//        System.out.println(responseGHN);
        donHangService.updateTrangThaiDonHang(donHang);

        Page<DonHang> donHangs = donHangService.getAll(1);

        model.addAttribute("list", donHangs.getContent());
        return "redirect:/admin/don-hang";
    }


}