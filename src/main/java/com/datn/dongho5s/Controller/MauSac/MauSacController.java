//package com.datn.dongho5s.Controller.MauSac;
//
//import com.datn.dongho5s.Entity.MauSac;
//import com.datn.dongho5s.Exception.MauSacNotFoundException;
//import com.datn.dongho5s.Service.MauSacService;
//import com.datn.dongho5s.Service.impl.MauSacServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.List;
//
//@Controller
//public class MauSacController {
//
//    @Autowired
//    MauSacService mauSacService;
//    @GetMapping("/colors")
//    public String listFirstPage(Model model){
//        return listByPage(1,model,"tenMauSac","asc",null);
//    }
//
//    @GetMapping("/colors/page/{pageNum}")
//    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
//                             @Param("sortField")String sortField , @Param("sortDir")String sortDir,
//                             @Param("keyword")String keyword
//    ){
//        Page<MauSac> page = mauSacService.listByPage(pageNum, sortField, sortDir,keyword);
//        List<MauSac> listMauSac = page.getContent();
//
//        long startCount = (pageNum -1) * MauSacServiceImpl.COLORS_PER_PAGE + 1;
//        long endCount = startCount + MauSacServiceImpl.COLORS_PER_PAGE - 1;
//
//        if(endCount > page.getTotalElements()){
//            endCount = page.getTotalElements();
//        }
//
//        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
//
//        model.addAttribute("currentPage",pageNum);
//        model.addAttribute("totalPages",page.getTotalPages());
//        model.addAttribute("startCount",startCount);
//        model.addAttribute("endCount",endCount);
//        model.addAttribute("totalItem",page.getTotalElements());
//        model.addAttribute("listMauSac",listMauSac);
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDir", sortDir);
//        model.addAttribute("reverseSortDir",reverseSortDir);
//        model.addAttribute("keyword", keyword);
//        return "mausac/colors";
//    }
//
//    @GetMapping("/colors/{id}/enabled/{status}")
//    public String updateMauSacEnabledStatus(@PathVariable("id") Integer id,
//                                             @PathVariable("status") boolean enabled,
//                                             RedirectAttributes redirectAttributes){
//        mauSacService.updateMauSacEnabledStatus(id, enabled);
//        String status = enabled ? "online" : "offline";
//        String message = "Màu sắc có id " + id + " thay đổi trạng thái thành " + status;
//        redirectAttributes.addFlashAttribute("message",message);
//        return "redirect:/colors";
//    }
//
//    @GetMapping("/colors/new")
//    public String newDanhMuc(Model model){
//        model.addAttribute("mauSac", new MauSac());
//        model.addAttribute("pageTitle","Tạo Mới Màu Sắc");
//        return "mausac/colors_form";
//    }
//
//    @PostMapping("/colors/save")
//    public String saveDanhMuc(MauSac mauSac, RedirectAttributes redirectAttributes){
//        mauSacService.save(mauSac);
//        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
//        return "redirect:/colors";
//    }
//
//    @GetMapping("/colors/edit/{id}")
//    public String editUser(@PathVariable(name = "id") Integer id,
//                           Model model,
//                           RedirectAttributes redirectAttributes){
//        try{
//            MauSac mauSac = mauSacService.get(id);
//            model.addAttribute("mauSac", mauSac);
//            model.addAttribute("pageTitle","Update Màu Sắc (ID : " + id + ")");
//            return "mausac/colors_form";
//        } catch (MauSacNotFoundException ex){
//            redirectAttributes.addFlashAttribute("message",ex.getMessage());
//            return "redirect:/colors";
//        } catch (Exception ex) {
//            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
//            return "redirect:/error";
//        }
//    }
//}
