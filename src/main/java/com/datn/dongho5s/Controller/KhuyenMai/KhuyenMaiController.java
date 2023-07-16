package com.datn.dongho5s.Controller.KhuyenMai;

import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Exception.KhuyenMaiNotFoundException;
import com.datn.dongho5s.Service.KhuyenMaiService;
import com.datn.dongho5s.Service.impl.KhuyenMaiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class KhuyenMaiController {
    @Autowired
    KhuyenMaiService service;

    @GetMapping("/discounts")
    public String listFirstPage(Model model){
        return listByPage(1,model,"tenKhuyenMai","asc",null);
    }

    @GetMapping("/discounts/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField,@Param("sortDir")String sortDir,
                             @Param("keyword")String keyword) {
        Page<KhuyenMai> page = service.listByPage(pageNum,sortField,sortDir,keyword);
        List<KhuyenMai> listKhuyenMai = page.getContent();
        long startCount = (pageNum-1) * KhuyenMaiServiceImpl.DISCOUNT_PER_PAGE +1;
        long endCount = startCount + KhuyenMaiServiceImpl.DISCOUNT_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listKhuyenMai",listKhuyenMai);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "khuyenmai/discounts";

    }
    @GetMapping("/discounts/new")
    public String newKhuyenMai(Model model){
        model.addAttribute("khuyenMai", new KhuyenMai());
        model.addAttribute("pageTitle", "Tạo Mới Khuyến Mãi");
        return "khuyenmai/discounts_form";
    }

    @PostMapping("/discounts/save")
    public String saveKhuyenMai(KhuyenMai khuyenMai, RedirectAttributes redirectAttributes){
        System.out.println(khuyenMai);
        System.out.println("controller");
        service.save(khuyenMai);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/discounts";
    }

    @GetMapping("/discounts/edit/{id}")
    public String editKhuyenMai(@PathVariable(name = "id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes){
        try{
            KhuyenMai khuyenMai = service.get(id);
            model.addAttribute("khuyenMai",khuyenMai);
            model.addAttribute("pageTitle","Update Khuyến Mãi (ID : " + id + ")");
            return "khuyenmai/discounts_form";
        }catch (KhuyenMaiNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/discounts";
        }catch (Exception ex){
            redirectAttributes.addFlashAttribute("error","Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }
}