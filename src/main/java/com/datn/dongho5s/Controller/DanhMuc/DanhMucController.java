package com.datn.dongho5s.Controller.DanhMuc;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Exception.CustomException.DanhMucNotFoundException;
import com.datn.dongho5s.Service.impl.DanhMucService;
import com.datn.dongho5s.UploadFile.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class DanhMucController {
    @Autowired
    private DanhMucService service;

    @GetMapping("/categories")
    public String listAll(Model model){
        List<DanhMuc> listDanhMuc = service.listAll();
        model.addAttribute("listDanhMuc",listDanhMuc);
        return "danhmuc/categories";
    }

    @GetMapping("/categories/new")
    public String newDanhMuc(Model model){
        List<DanhMuc> listDanhMucs = service.listDanhMucUsedInForm();

        model.addAttribute("danhMuc",new DanhMuc());
        model.addAttribute("listDanhMucs",listDanhMucs);
        model.addAttribute("pageTitle","Tạo mới danh mục");
        return "danhmuc/categories_form";
    }

    @PostMapping("/categories/save")
    public String saveDanhMuc(DanhMuc danhMuc,
                              @RequestParam("fileImage")MultipartFile multipartFile,
                              RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            danhMuc.setAnh(fileName);
            DanhMuc savedDanhMuc = service.save(danhMuc);
            String uploadDir = "category-images/" + savedDanhMuc.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

        }else {
            service.save(danhMuc);
        }

        ra.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String editDanhMuc(@PathVariable(name = "id") Integer id, Model model,
                              RedirectAttributes ra){
        try{
            DanhMuc danhMuc = service.get(id);
            List<DanhMuc> listDanhMucs = service.listDanhMucUsedInForm();
            model.addAttribute("danhMuc",danhMuc);
            model.addAttribute("listDanhMucs",listDanhMucs);
            model.addAttribute("pageTitle","Edit Danh Mục (ID: " + id + ")");
            return "danhmuc/categories_form";
        }catch (DanhMucNotFoundException ex){
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
    }

//    @GetMapping("/categories/{id}/enabled/{status}")
//    public String updateDanhMucEnabledStatus(@PathVariable("id") Integer id,
//                                              @PathVariable("status") boolean enabled,
//                                              RedirectAttributes redirectAttributes){
//        service.updateDanhMucEnabledStatus(id, enabled);
//        String status = enabled ? "online" : "offline";
//        String message = "Danh Mục có id " + id + " thay đổi trạng thái thành " + status;
//        redirectAttributes.addFlashAttribute("message",message);
//        return "redirect:/categories";
//    }
}
