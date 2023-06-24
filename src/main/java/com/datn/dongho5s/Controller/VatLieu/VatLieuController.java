package com.datn.dongho5s.Controller.VatLieu;

import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Exception.MaterialNotFoundException;
import com.datn.dongho5s.Service.VatLieuService;
import org.dom4j.rule.Mode;
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
public class VatLieuController {
    @Autowired private VatLieuService service;

    @GetMapping("/materials")
    public String listFirstPage(Model model){
        return listByPage(1,model,"ten","asc",null);
    }

    @GetMapping("/materials/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword){
        Page<VatLieu> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<VatLieu> listVatLieu = page.getContent();

        long startCount = (pageNum - 1) * service.MATERIALS_PER_PAGE + 1;
        long endCount = startCount + service.MATERIALS_PER_PAGE - 1;

        if (endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc": "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItem", page.getTotalElements());
        model.addAttribute("listVatLieu", listVatLieu);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "vatlieu/materials";
    }

    @GetMapping("/materials/new")
    public String showNewForm(Model model){
        VatLieu vatLieu = new VatLieu();
        vatLieu.setEnabled(true);
        model.addAttribute("vatLieu", vatLieu);
        model.addAttribute("pageTitle", "Thêm mới Vật Liệu");
        return "vatlieu/material_form";
    }

    @PostMapping("/materials/save")
    public String saveMaterial(VatLieu material, RedirectAttributes ra){
        service.save(material);
        ra.addFlashAttribute("message", "The material has been saved successfully");
        return "redirect:/materials";
    }

    @GetMapping("materials/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            VatLieu vatLieu = service.get(id);
            model.addAttribute("vatLieu", vatLieu);
            model.addAttribute("pageTitle", "Edit Vat Lieu (ID: " + id +")");
            return "vatlieu/material_form";
        } catch (MaterialNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/materials";
        }
    }


    @GetMapping("/materials/{id}/enabled/{status}")
    public String updateVatLieuEnabledStatus(@PathVariable("id") Integer id,
                                             @PathVariable("status") boolean enabled,
                                             RedirectAttributes ra){
        service.updateVatLieuEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        ra.addFlashAttribute("message", "The material ID "+ id+ " has been successfully changed his status to " +status + "!");
        return "redirect:/materials";
    }
}
