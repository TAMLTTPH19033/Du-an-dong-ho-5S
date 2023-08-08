
package com.datn.dongho5s.Controller.BanHang;

import com.datn.dongho5s.Entity.*;
import com.datn.dongho5s.Request.HoaDonAdminRequest;
import com.datn.dongho5s.Response.SanPhamAdminResponse;
import com.datn.dongho5s.Service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        Model model
    ){
        model.addAttribute("hoaDonAdminRequest",new HoaDonAdminRequest());
        this.getListSanPham(model,1);
        this.getListHDCT(model,1);
        this.getAllHD(model,1);
        return "admin/banhang/banhang";
    }

    @GetMapping("/sanpham/page/{pageNum}")
    public String getListSanPham(
            Model model,
            @PathVariable("pageNum") int pageNum
    ){

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);
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

    @GetMapping("/hoa-don/page/{pageNum}")
    public String getAllHD(
        Model model,
        @PathVariable("pageNum") int pageNum
    ){
        List<DonHang> lstHoaDon = donHangService.getAllForBanHang(pageNum).getContent();

        model.addAttribute("lstHoaDon",lstHoaDon);
        return "admin/banhang/banhang";
    }

    @PostMapping("/hoa-don/tao-moi")
    public String taoHoaDon(
            Model model,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest,
            HttpSession httpSession,
            HttpServletRequest httpServletRequest
    ){
        String maDonHang = this.generateMaHD();
        Date ngayTao = new Date();

        KhachHang khachHang = KhachHang
                .builder()
                .tenKhachHang(hoaDonAdminRequest.getTenKhachHang())
                .soDienThoai(hoaDonAdminRequest.getSdt())
                .build();
        khachHangService.saveKhachHang(khachHang);

        DonHang donHang = DonHang
                .builder()
                .maDonHang(maDonHang)
                .trangThaiDonHang(0)
                .tongTien(hoaDonAdminRequest.getTongTien() == null ? 0d : hoaDonAdminRequest.getTongTien())
                //.nhanVien()
                .khachHang(khachHang)
                .ngayTao(ngayTao)
                .build();

        donHangService.save(donHang);

        DonHang donHangByMa = donHangService.findByMaDonHang(maDonHang);

        httpSession.setAttribute("donHangHienTai",donHangByMa);

        hoaDonAdminRequest.setIdHoaDon(donHangByMa.getIdDonHang());
        hoaDonAdminRequest.setTongTien(donHangByMa.getTongTien() == null ? 0 : donHangByMa.getTongTien());
        hoaDonAdminRequest.setTongTienDonHang((donHangByMa.getTongTien() == null ? 0 : donHangByMa.getTongTien()) + (donHangByMa.getPhiVanChuyen() == null ? 0 : donHangByMa.getPhiVanChuyen()));
        hoaDonAdminRequest.setTenKhachHang(donHangByMa.getKhachHang() == null ? "" : donHangByMa.getKhachHang().getTenKhachHang());
        hoaDonAdminRequest.setSdt(donHangByMa.getKhachHang()== null ? "" : donHangByMa.getKhachHang().getSoDienThoai());
        hoaDonAdminRequest.setMaHoaDon(donHangByMa.getMaDonHang());
        hoaDonAdminRequest.setNgayTao(donHangByMa.getNgayTao());
        hoaDonAdminRequest.setGiamGia(0d);

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);

        //set list hdct

        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(maDonHang,1);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        // set list hoa don
        List<DonHang> lstHoaDon = donHangService.getAllForBanHang(1).getContent();

        model.addAttribute("lstHoaDon",lstHoaDon);

        ///set list ctsp

        model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1));

        return "redirect:/admin/ban-hang/hoa-don/" + maDonHang;
    }

    @GetMapping("/hoa-don/{maHoaDon}")
    public String chonHoaDon(
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest,
        @PathVariable("maHoaDon") String maHoaDon,
        Model model,
        HttpSession httpSession,
        HttpServletRequest httpServletRequest
    ){
        DonHang donHang = donHangService.findByMaDonHang(maHoaDon);

        httpSession.setAttribute("donHangHienTai",donHang);

        hoaDonAdminRequest.setIdHoaDon(donHang.getIdDonHang());
        hoaDonAdminRequest.setTongTien(donHang.getTongTien() == null ? 0 : donHang.getTongTien());
        hoaDonAdminRequest.setTongTienDonHang((donHang.getTongTien()== null ? 0 : donHang.getTongTien()) + (donHang.getPhiVanChuyen() == null ? 0 : donHang.getPhiVanChuyen()));
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getTenKhachHang());
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getSoDienThoai());

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);

        //set list hdct

        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(donHang.getMaDonHang(),1);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        // set list hoa don
        List<DonHang> lstHoaDon = donHangService.getAllForBanHang(1).getContent();

        model.addAttribute("lstHoaDon",lstHoaDon);

        //set list ctsp

        model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1));


        return "admin/banhang/banhang";
    }

    @PostMapping("/them/{maCTSP}/{soLuong}")
    public String themSoLuongSanPham(
        @PathVariable("maCTSP") String maCTSP,
        @PathVariable("soLuong") int soLuong,
        HttpServletRequest httpServletRequest,
        HttpSession httpSession,
        Model model,
        @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        // them san pham hoac cap nhat san pham neu ctsp đã tồn tại
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findByMaChiTietSanPham(maCTSP);
        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");

        hoaDonChiTietService.themSoLuongSanPham(soLuong,chiTietSanPham,donHang);

        hoaDonAdminRequest.setIdHoaDon(donHang.getIdDonHang());
        hoaDonAdminRequest.setTongTien(donHang.getTongTien() == null ? 0 : donHang.getTongTien());
        hoaDonAdminRequest.setTongTienDonHang((donHang.getTongTien()== null ? 0 : donHang.getTongTien()) + (donHang.getPhiVanChuyen() == null ? 0 : donHang.getPhiVanChuyen()));
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getTenKhachHang());
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getSoDienThoai());

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);

        //set list hdct

        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(donHang.getMaDonHang(),1);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        // set list hoa don
        List<DonHang> lstHoaDon = donHangService.getAllForBanHang(1).getContent();

        model.addAttribute("lstHoaDon",lstHoaDon);

        //set list ctsp

        model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1));

        return "redirect:/admin/ban-hang/hoa-don/" + donHang.getMaDonHang();
    }

    @PostMapping("/hoa-don-chi-tiet/{idHDCT}")
    public String xoaHDCT(
            @PathVariable("idHDCT") int idHDCT,
            HttpServletRequest httpServletRequest,
            HttpSession httpSession,
            Model model,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        // xoa hoa don chi tiet
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.findHoaDonChiTietById(idHDCT);
        hoaDonChiTietService.xoaHDCT(hoaDonChiTiet);

        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");

        hoaDonAdminRequest.setIdHoaDon(donHang.getIdDonHang());
        hoaDonAdminRequest.setTongTien(donHang.getTongTien() == null ? 0 : donHang.getTongTien());
        hoaDonAdminRequest.setTongTienDonHang((donHang.getTongTien()== null ? 0 : donHang.getTongTien()) + (donHang.getPhiVanChuyen() == null ? 0 : donHang.getPhiVanChuyen()));
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getTenKhachHang());
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getSoDienThoai());

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);

        //set list hdct

        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(donHang.getMaDonHang(),1);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        // set list hoa don
        List<DonHang> lstHoaDon = donHangService.getAllForBanHang(1).getContent();

        model.addAttribute("lstHoaDon",lstHoaDon);

        //set list ctsp

        model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1));

        return "redirect:/admin/ban-hang/hoa-don/" + donHang.getMaDonHang();
    }
    @PostMapping("/hoa-don-chi-tiet/sua/{idHDCT}")
    public String updateHDCT(
            @PathVariable("idHD") int idHDCT,
            HttpServletRequest httpServletRequest,
            HttpSession httpSession,
            Model model,
            @ModelAttribute("hoaDonAdminRequest") HoaDonAdminRequest hoaDonAdminRequest
    ){
        int soLuongCapNhat = Integer.parseInt(httpServletRequest.getParameter("soLuongCapNhat"));
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietService.findHoaDonChiTietById(idHDCT);

        if (soLuongCapNhat<=0){
            // xoa hoa don chi tiet
            hoaDonChiTietService.xoaHDCT(hoaDonChiTiet);
        } else{
            // Cap nhat lai so luong
            hoaDonChiTietService.updateSoLuongInHDCT(hoaDonChiTiet,soLuongCapNhat);
        }

        DonHang donHang = (DonHang) httpSession.getAttribute("donHangHienTai");

        hoaDonAdminRequest.setIdHoaDon(donHang.getIdDonHang());
        hoaDonAdminRequest.setTongTien(donHang.getTongTien() == null ? 0 : donHang.getTongTien());
        hoaDonAdminRequest.setTongTienDonHang((donHang.getTongTien()== null ? 0 : donHang.getTongTien()) + (donHang.getPhiVanChuyen() == null ? 0 : donHang.getPhiVanChuyen()));
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getTenKhachHang());
        hoaDonAdminRequest.setSdt(donHang.getKhachHang()== null ? "" : donHang.getKhachHang().getSoDienThoai());

        // set list san pham
        List<SanPhamAdminResponse> sanPhamList = chiTietSanPhamService.getAllSanPhamAminResponse(1);

        model.addAttribute("listSanPham",sanPhamList);

        //set list hdct

        Page<HoaDonChiTiet> lstHDCTPage = hoaDonChiTietService.getHDCTByMaDonHang(donHang.getMaDonHang(),1);

        List<HoaDonChiTiet> lstHDCT = lstHDCTPage.getContent();

        model.addAttribute("lstHDCT",lstHDCT);

        // set list hoa don
        List<DonHang> lstHoaDon = donHangService.getAllForBanHang(1).getContent();

        model.addAttribute("lstHoaDon",lstHoaDon);

        //set list ctsp

        model.addAttribute("lstCTSP",chiTietSanPhamService.getAllSanPhamAminResponse(1));

        return "redirect:/admin/ban-hang/hoa-don/" + donHang.getMaDonHang();
    }




    public String generateMaHD(){
        return "HD" +
                new Date().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]", "") +
                UUID.randomUUID().toString().toUpperCase().replaceAll("[^a-zA-Z0-9]", "");
    }
}
