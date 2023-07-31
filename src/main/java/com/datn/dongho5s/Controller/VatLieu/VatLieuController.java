package com.datn.dongho5s.Controller.VatLieu;

import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Exception.VatLieuNotFoundException;
import com.datn.dongho5s.Export.VatLieuCsvExporter;
import com.datn.dongho5s.Export.VatLieuExcelExporter;
import com.datn.dongho5s.Service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class VatLieuController {
    @Autowired
    private VatLieuService service;

    @GetMapping("/materials")
    public String listFirstPage(Model model){
        return listByPage(1,model,"tenVatLieu","asc",null);
    }

    @GetMapping("/materials/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField,@Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){

        Page<VatLieu> page = service.listByPage(pageNum,sortField,sortDir,keyword);
        List<VatLieu> listVatLieu = page.getContent();

        long startCount = (pageNum -1) * VatLieuService.MATERIALS_PER_PAGE +1;
        long endCount = startCount + VatLieuService.MATERIALS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listVatLieu",listVatLieu);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "admin/vatlieu/materials";

    }

    @GetMapping("materials/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                                @PathVariable("status")boolean enabled,
                                                RedirectAttributes redirectAttributes){
        service.updateVatLieuEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Vật liệu có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/materials";
    }

    @GetMapping("/materials/new")
    public String newVatLieu(Model model){
        model.addAttribute("vatLieu",new VatLieu());
        model.addAttribute("pageTitle","Tạo Mới Vật Liệu");
        return "admin/vatlieu/material_form";
    }

    @PostMapping("materials/save")
    public String saveVatLieu(VatLieu vatLieu, RedirectAttributes redirectAttributes){
        service.save(vatLieu);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/materials";
    }

    @GetMapping("materials/edit/{id}")
    public String editVatLieu(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        try {
            VatLieu vatLieu = service.get(id);
            model.addAttribute("vatLieu", vatLieu);
            model.addAttribute("pageTitle", "Update Vật Liệu (ID: " + id + ")");
            return "admin/vatlieu/material_form";
        } catch (VatLieuNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/materials";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/materials/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<VatLieu> listVatLieu = service.getAllVatLieu();
        VatLieuCsvExporter exporter = new VatLieuCsvExporter();
        exporter.export(listVatLieu,response);
    }

    @GetMapping("/materials/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<VatLieu> listVatLieu = service.getAllVatLieu();
        VatLieuExcelExporter exporter = new VatLieuExcelExporter();
        exporter.export(listVatLieu,response);

    }


}
