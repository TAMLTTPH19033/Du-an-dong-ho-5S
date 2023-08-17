
package com.datn.dongho5s.Controller.BanHang;

import com.datn.dongho5s.Entity.*;
import com.datn.dongho5s.Export.HoaDonPdf;
import com.datn.dongho5s.Request.HoaDonAdminRequest;
import com.datn.dongho5s.Response.SanPhamAdminResponse;
import com.datn.dongho5s.Service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin/ban-hang")
public class BanHangController {

    @Autowired
    SanPhamService sanPhamService;

    @Autowired
    DonHangService donHangService;

    @Autowired
    HoaDonChiTietService hoaDonChiTietService;

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    KhachHangService khachHangService;

    @GetMapping
    public String getFormBanHang(
        Model model,
        HttpSession httpSession
    ){
        HoaDonAdminRequest hoaDonAdminRequest = new HoaDonAdminRequest();
        model.addAttribute("hoaDonAdminRequest", hoaDonAdminRequest);

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");

        if (httpSession.getAttribute("donHangHienTai")!= null){
            Double tongTien = 0d;
            for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
                if (h.getChiTietSanPham().getKhuyenMai().isEnabled() == true){
                    tongTien += h.getGiaBan() * h.getSoLuong() * h.getChietKhau() / 100;
                } else{
                    tongTien +=  h.getGiaBan() * h.getSoLuong();
                }
            }
            model.addAttribute("hoaDonAdminRequest", HoaDonAdminRequest
                    .builder()
                        .maHoaDon(donHangByMa.getMaDonHang())
                        .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                        .tongTienDonHang(tongTien)
                        .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                        .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                    .build());
        }
        this.getListSanPham(model,1,"",httpSession,hoaDonAdminRequest);
        this.getListHDCT(model,1);
        return "admin/banhang/banhang";
    }

    @GetMapping("/sanpham/page/{pageNum}")
    public String getListSanPham(
        Model model,
        @PathVariable("pageNum") int pageNum,
        @RequestParam("keywork") String keywork,
        HttpSession httpSession,
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1,keywork);

        model.addAttribute("listSanPham", sanPhamList);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", chiTietSanPhamService.getALlChiTietSanPhamPage(pageNum,keywork).getTotalPages());

        return "admin/banhang/banhang";
    }

    @GetMapping("/hoa-don-chi-tiet/page/{pageNum}")
    public String getListHDCT(
            Model model,
            @PathVariable("pageNum") int pageNum
    ) {
        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(null,pageNum);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        return "admin/banhang/banhang";
    }

    @PostMapping("/hoa-don/tao-moi")
    public String taoHoaDon(
            Model model,
            HttpSession httpSession,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        String maDonHangCD = this.generateMaHD();
        KhachHang khachHang = (KhachHang) httpSession.getAttribute("khachHangExist");

        if (khachHang == null){
            khachHang = KhachHang
                    .builder()
                    .tenKhachHang(hoaDonAdminRequest.getTenKhachHang())
                    .soDienThoai(hoaDonAdminRequest.getSdt())
                    .enabled(true)
                    .listDiaChi(null)
                    .email(null)
                    .gioiTinh(null)
                    .password(null)
                    .ngaySinh(null)
                    .ngaySua(new Date())
                    .thoiGianTaoTaiKhoan(null)
                    .build();

            khachHangService.saveKhachHang(khachHang);
        }

        DonHang donHang = DonHang
                .builder()
                .maDonHang(maDonHangCD)
                .trangThaiDonHang(0)
                .tongTien(0d)
                //.nhanVien()0
                .ngayCapNhap(new Date())
                .ngayGiaoHang(new Date())
                .khachHang(khachHang)
                .ngayTao(new Date())
                .build();

        donHangService.save(donHang);

        DonHang donHangByMa = donHangService.findByMaDonHang(maDonHangCD);

        httpSession.setAttribute("donHangHienTai",donHangByMa);

        return "redirect:/admin/ban-hang/hoa-don/" + maDonHangCD;
    }

    @GetMapping("/khach-hang/tim-kiem")
    public String findKHByPfindhoneNumber(
            Model model,
            @RequestParam("phoneNumber") String phoneNumber,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest,
            HttpSession httpSession
    ){
        KhachHang khachHang = khachHangService.findByPhoneNumber(phoneNumber);

        if (khachHang!= null){
            hoaDonAdminRequest = HoaDonAdminRequest
                    .builder()
                    .maHoaDon("")
                    .sdt(khachHang.getSoDienThoai())
                    .tongTienDonHang(0d)
                    .ngayTao(dateParseToString(new Date(),"yyyy-MM-dd"))
                    .tenKhachHang(khachHang.getTenKhachHang())
                    .build();
            model.addAttribute("hoaDonAdminRequest",hoaDonAdminRequest);

            httpSession.setAttribute("khachHangExist",khachHang);
        }

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1,"");

        model.addAttribute("listSanPham",sanPhamList);

        return "admin/banhang/banhang";
    }

    @GetMapping("/khach-hang/api/{phoneNumber}")
    @ResponseBody
    public ResponseEntity<String> findKHByPfindhoneNumber(
        @PathVariable("phoneNumber") String phoneNumber
    ){
        KhachHang khachHang = khachHangService.findByPhoneNumber(phoneNumber);
        if (khachHang!= null) {
            return ResponseEntity.status(HttpStatus.OK).body(khachHang.getSoDienThoai());
        }
        return null;
    }

    @GetMapping("/hoa-don/{maHoaDon}")
    public String chonHoaDon(
        @PathVariable("maHoaDon") String maHoaDon,
        Model model,
        HttpSession httpSession,
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        DonHang donHangByMa = donHangService.findByMaDonHang(maHoaDon);

        httpSession.setAttribute("donHangHienTai",donHangByMa);
        Double tongTien = 0d;
        for (HoaDonChiTiet h: donHangByMa.getListHoaDonChiTiet()) {
            if(h.getChiTietSanPham().getKhuyenMai() == null){
                tongTien += h.getGiaBan() * h.getSoLuong();
            }else {
                if (h.getChiTietSanPham().getKhuyenMai().isEnabled() == true) {
                    tongTien += h.getGiaBan() * h.getSoLuong() * h.getChietKhau() / 100;
                } else {
                    tongTien += h.getGiaBan() * h.getSoLuong();
                }
            }
        }

        hoaDonAdminRequest = HoaDonAdminRequest
                            .builder()
                                .maHoaDon(donHangByMa.getMaDonHang())
                                .sdt(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getSoDienThoai())
                                .tongTienDonHang(tongTien)
                                .ngayTao(dateParseToString(donHangByMa.getNgayTao(),"yyyy-MM-dd"))
                                .tenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang())
                            .build();
        model.addAttribute("hoaDonAdminRequest",hoaDonAdminRequest);

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1,"");

        model.addAttribute("listSanPham",sanPhamList);

        //set list hdct

        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(donHangByMa.getMaDonHang(),1);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        //set list ctsp

        model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1,""));

        return "admin/banhang/banhang";
    }

    @PostMapping("/them/{maCTSP}/{soLuong}")
    public String themSoLuongSanPham(
        @PathVariable("maCTSP") String maCTSP,
        @PathVariable("soLuong") int soLuong,
        HttpSession httpSession,
        Model model
    ){
        // them san pham hoac cap nhat san pham neu ctsp đã tồn tại
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findByMaChiTietSanPham(maCTSP);

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");

        hoaDonChiTietService.themSoLuongSanPham(soLuong,chiTietSanPham,donHangByMa);

        return "redirect:/admin/ban-hang/hoa-don/" + donHangByMa.getMaDonHang();
    }

    @PostMapping("/hoa-don-chi-tiet/{idHDCT}")
    public String xoaHDCT(
            @PathVariable("idHDCT") int idHDCT,
            HttpSession httpSession,
            Model model
    ){
        // xoa hoa don chi tiet
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.findHoaDonChiTietById(idHDCT);
        hoaDonChiTietService.xoaHDCT(hoaDonChiTiet);

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");

        return "redirect:/admin/ban-hang/hoa-don/" + donHangByMa.getMaDonHang();
    }

    @PostMapping("/hoa-don-chi-tiet/sua/{idHD}/so-luong/{soLuong}")
    public String updateHDCT(
            @PathVariable("idHD") int idHDCT,
            @PathVariable("soLuong") int soLuong,
            HttpSession httpSession,
            Model model
    ){
        int soLuongCapNhat = soLuong;
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.findHoaDonChiTietById(idHDCT);

        if (soLuongCapNhat<=0){
            // xoa hoa don chi tiet
            hoaDonChiTietService.xoaHDCT(hoaDonChiTiet);

        } else{
            // Cap nhat lai so luong
            hoaDonChiTietService.updateSoLuongInHDCT(hoaDonChiTiet,soLuongCapNhat);
        }

        DonHang donHangByMa = (DonHang) httpSession.getAttribute("donHangHienTai");
        return "redirect:/admin/ban-hang/hoa-don/" + donHangByMa.getMaDonHang();
    }

    @PostMapping("/hoa-don/thanh-toan")
    public String thanhToan(
        HttpSession httpSession
    ){
        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");
        // chuyen trang thai hoan thanh don hang
        donHang.setTrangThaiDonHang(3);
        //thanh toan
        donHangService.thanhToanAdmin(donHang);

        httpSession.removeAttribute("donHangHienTai");
        return "redirect:/admin/ban-hang";
    }

    @PostMapping("/hoa-don/xuat-hoa-don")
    public String xuatHoaDon(
            HttpSession httpSession,
            HttpServletResponse response
    ) throws Exception {
        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");
        // chuyen trang thai hoan thanh don hang
        donHang.setTrangThaiDonHang(3);
        //thanh toan
        donHangService.thanhToanAdmin(donHang);

        //xuat
//        List<HoaDonChiTiet> lst = hoaDonChiTietService.getByIdDonHang(donHang.getIdDonHang());
//        HoaDonPdf hoaDonPdf = new HoaDonPdf();
//        hoaDonPdf.exportToPDF(response, lst, donHang);

        httpSession.removeAttribute("donHangHienTai");
        return "redirect:/admin/ban-hang";
    }

    @PostMapping("/hoa-don/huy")
    public String huyHoaDon(
        HttpSession httpSession
    ){

        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");
        // huy hoa don
        donHangService.xoaDonHangAdmin(donHang);

        httpSession.removeAttribute("donHangHienTai");
        return "redirect:/admin/ban-hang";
    }

    public String generateMaHD(){
        return "HD" +
                new Date().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]", "") +
                UUID.randomUUID().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]", "");
    }

    public Date stringParseToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String dateParseToString(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
