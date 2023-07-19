package com.datn.dongho5s.Controller.SanPham;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Service.DanhmucService;
import com.datn.dongho5s.Service.SanPhamService;
import com.datn.dongho5s.Service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SanPhamController {
    @Autowired private SanPhamService sanPhamService;
    @Autowired private ThuongHieuService thuongHieuService;
    @Autowired private DanhmucService danhmucService;

    @GetMapping("/products")
    public String listAll(Model model){
        List<SanPham> listSanPhams = sanPhamService.listAll();
        model.addAttribute("listSanPhams",listSanPhams);

        return "sanpham/products";
    }

    @GetMapping("/products/new")
    public String newProduct(Model model){
        List<ThuongHieu> listThuongHieu = thuongHieuService.getAllThuongHieu();
        List<DanhMuc> listDanhMuc = danhmucService.listAll();
        SanPham sanPham = new SanPham();
        sanPham.setTrangThai(1);
        model.addAttribute("sanPham",sanPham);
        model.addAttribute("listThuongHieu",listThuongHieu);
        model.addAttribute("listDanhMuc",listDanhMuc);
        model.addAttribute("pageTitle","Tạo Mới Sản Phẩm");

        return "sanpham/products_form";
    }

    @PostMapping("products/save")
    public String savedProduct(SanPham sanPham, RedirectAttributes ra){
        sanPhamService.save(sanPham);
        ra.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/products";
    }

}
