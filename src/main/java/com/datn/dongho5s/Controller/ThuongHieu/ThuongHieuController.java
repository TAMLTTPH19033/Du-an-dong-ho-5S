package com.datn.dongho5s.Controller.ThuongHieu;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Exception.ThuongHieuNotFoundException;
import com.datn.dongho5s.Export.DanhMucCsvExporter;
import com.datn.dongho5s.Export.DanhMucExcelExporter;
import com.datn.dongho5s.Export.ThuongHieuCsvExporter;
import com.datn.dongho5s.Export.ThuongHieuExcelExporter;
import com.datn.dongho5s.Service.ThuongHieuService;
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
public class ThuongHieuController {
    @Autowired
    private ThuongHieuService service;

    @GetMapping("/brands")
    public String listFirstPage(Model model){
        return listByPage(1,model,"ten","asc",null);
    }

    @GetMapping("/brands/page/{pageNum}")
    private String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                              @Param("sortField") String sortField,@Param("sortDir") String sortDir,
                              @Param("keyword") String keyword){

        Page<ThuongHieu> page = service.listByPage(pageNum,sortField,sortDir,keyword);
        List<ThuongHieu> listThuongHieu = page.getContent();

        long startCount = (pageNum -1) * ThuongHieuService.BRANDS_PER_PAGE +1;
        long endCount = startCount + ThuongHieuService.BRANDS_PER_PAGE-1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listThuongHieu",listThuongHieu);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir",sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword",keyword);
        return "thuonghieu/brands";

    }

    @GetMapping("brands/{id}/enabled/{status}")
    public String updateThuongHieuEnabledStatus(@PathVariable("id") Integer id,
                                                @PathVariable("status")boolean enabled,
                                                RedirectAttributes redirectAttributes){
        service.updateThuongHieuEnabledStatus(id,enabled);
        String status = enabled ? "online" : "offline";
        String message = "Thương Hiệu có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/brands";
    }

    @GetMapping("/brands/new")
    public String newThuongHieu(Model model){
        model.addAttribute("thuongHieu",new ThuongHieu());
        model.addAttribute("pageTitle","Tạo Mới Thương Hiệu");
        return "thuonghieu/brands_form";
    }

    @PostMapping("/brands/save")
    public String saveThuongHieu(ThuongHieu thuongHieu, RedirectAttributes redirectAttributes){
        service.save(thuongHieu);
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/brands";
    }

    @GetMapping("/brands/edit/{id}")
    public String editThuongHieu(@PathVariable(name = "id") Integer id,
                                 Model model,
                                 RedirectAttributes redirectAttributes){
        try {
            ThuongHieu thuongHieu = service.get(id);
            model.addAttribute("thuongHieu", thuongHieu);
            model.addAttribute("pageTitle", "Update Thương Hiệu (ID: " + id + ")");
            return "thuonghieu/brands_form";
        } catch (ThuongHieuNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/brands";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý. Vui lòng thử lại sau.");
            return "redirect:/error";
        }
    }

    @GetMapping("/brands/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<ThuongHieu> listThuongHieu = service.getAllThuongHieu();
        ThuongHieuCsvExporter exporter = new ThuongHieuCsvExporter();
        exporter.export(listThuongHieu,response);
    }

    @GetMapping("/brands/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ThuongHieu> listThuongHieu = service.getAllThuongHieu();
        ThuongHieuExcelExporter exporter = new ThuongHieuExcelExporter();
        exporter.export(listThuongHieu,response);

    }


}
