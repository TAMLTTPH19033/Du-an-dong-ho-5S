//package com.datn.dongho5s.Controller.BanHang;
//
//import com.datn.dongho5s.Entity.SanPham;
//import com.datn.dongho5s.Service.SanPhamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/ban-hang")
//public class BanHangController {
//
//    @Autowired
//    SanPhamService sanPhamService;
//
//    @GetMapping
//    public String getFormBanHang(
//        Model model
//    ){
//        this.getListSanPham(model,1);
//        this.getListGioHang(model,1);
//        this.getListHoaDon(model,1);
//
//        return "banhang/banhang";
//    }
//
//    @GetMapping("/sanpham/page/{pageNum}")
//    public String getListSanPham(
//            Model model,
//            @PathVariable("pageNum") int pageNum
//    ){
//        Page<SanPham> sanPhamPage = sanPhamService.getPageSanPham(pageNum);
//
//        List<SanPham> sanPhamList = sanPhamPage.getContent();
//
//        model.addAttribute("listSanPham",sanPhamList);
//        return "banhang/banhang";
//    }
//
//    @GetMapping("/gio-hang/page/{pageNum}")
//    public String getListGioHang(Model model,
//                                 @PathVariable("pageNum") int pageNum
//    ) {
//        Page<SanPham> sanPhamPage = sanPhamService.getPageSanPham(pageNum);
//
//        List<SanPham> sanPhamList = sanPhamPage.getContent();
//
//        model.addAttribute("lstGioHang",sanPhamList);
//
//        return "banhang/banhang";
//    }
//
//    @GetMapping("/hoa-don/page/{pageNum}")
//    public String getListHoaDon(
//        Model model,
//        @PathVariable("pageNum") int pageNum
//    ) {
//        Page<SanPham> sanPhamPage = sanPhamService.getPageSanPham(pageNum);
//
//        List<SanPham> sanPhamList = sanPhamPage.getContent();
//
//        model.addAttribute("lstHoaDon",sanPhamList);
//
//        return "banhang/banhang";
//    }
//
//}
