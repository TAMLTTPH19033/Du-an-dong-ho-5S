package com.datn.dongho5s.Controller.DanhMuc;

import com.datn.dongho5s.Entity.ChucVu;
import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Exception.DanhMucNotFoundException;
import com.datn.dongho5s.Exception.NhanVienNotFoundException;
import com.datn.dongho5s.Service.impl.DanhMucService;
import com.datn.dongho5s.Service.impl.NhanVienService;
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
public class DanhMucController {
    @Autowired
    private DanhMucService service;

    @GetMapping("/categories")
    public String listFirstPage(Model model){
        return listByPage(1,model,"ten","asc",null);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField , @Param("sortDir")String sortDir,
                             @Param("keyword")String keyword
    ){
        System.out.println("SortField: " + sortField);
        System.out.println("sortOrder: " + sortDir);
        Page<DanhMuc> page = service.listByPage(pageNum, sortField, sortDir,keyword);
        List<DanhMuc> listDanhMuc = page.getContent();

        long startCount = (pageNum -1) * DanhMucService.CATEGORIES_PER_PAGE + 1;
        long endCount = startCount + DanhMucService.CATEGORIES_PER_PAGE-1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listDanhMuc",listDanhMuc);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "danhmuc/categories";

    }

    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateDanhMucEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes){
        service.updateDanhMucEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "Danh Mục có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/categories";
    }

    @GetMapping("/categories/new")
    public String newDanhMuc(Model model){
        model.addAttribute("danhMuc", new DanhMuc());
        model.addAttribute("pageTitle","Tạo Mới Danh Mục");
        return "danhmuc/categories_form";
    }

    @PostMapping("/categories/save")
    public String saveDanhMuc(DanhMuc danhMuc, RedirectAttributes redirectAttributes){
         service.save(danhMuc);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            DanhMuc danhMuc = service.get(id);
            model.addAttribute("danhMuc", danhMuc);
            model.addAttribute("pageTitle","Update Danh Mục (ID : " + id + ")");
            return "danhmuc/categories_form";
        }catch (DanhMucNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/categories";
        }

    }
}
