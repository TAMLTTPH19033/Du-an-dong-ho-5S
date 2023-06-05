package com.dongho5s.admin.Controller.NhanVien;

import com.dongho5s.admin.Entity.ChucVu;
import com.dongho5s.admin.Entity.NhanVien;
import com.dongho5s.admin.Exception.NhanVienNotFoundException;
import com.dongho5s.admin.Export.NhanVienCsvExporter;
import com.dongho5s.admin.Export.NhanVienExcelExporter;
import com.dongho5s.admin.Export.NhanVienPdfExporter;
import com.dongho5s.admin.Service.Impl.NhanVienService;
import com.dongho5s.admin.UploadFile.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class NhanVienController {

    @Autowired
    private NhanVienService service;

    @GetMapping("/users")
    public String listFirstPage(Model model){
        return listByPage(1,model,"email","asc",null);
    }

    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField")String sortField , @Param("sortDir")String sortDir,
                             @Param("keyword")String keyword
                             ){
        System.out.println("SortField: " + sortField);
        System.out.println("sortOrder: " + sortDir);
        Page<NhanVien> page = service.listByPage(pageNum, sortField, sortDir,keyword);
        List<NhanVien> listNhanVien = page.getContent();

        long startCount = (pageNum -1) * NhanVienService.USERS_PER_PAGE + 1;
        long endCount = startCount + NhanVienService.USERS_PER_PAGE-1;

        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount",endCount);
        model.addAttribute("totalItem",page.getTotalElements());
        model.addAttribute("listNhanVien",listNhanVien);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir",reverseSortDir);
        model.addAttribute("keyword", keyword);
        return "nhanvien/users";

    }

    @GetMapping("/users/new")
    public String newUser(Model model){
        List<ChucVu> listChucVu = service.listChucVu();
        NhanVien nhanVien = new NhanVien();
        nhanVien.setEnabled(true);
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("listChucVu",listChucVu);
        model.addAttribute("pageTitle","Thêm Mới Nhân Viên");
        return "nhanvien/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(NhanVien nhanVien, RedirectAttributes redirectAttributes,
                           @RequestParam("image")MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            nhanVien.setAnh(fileName);
            NhanVien savedNhanVien = service.save(nhanVien);
            String uploadDir = "user-photos/" + savedNhanVien.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }else{
            if(nhanVien.getAnh().isEmpty()) nhanVien.setAnh(null);
            service.save(nhanVien);
        }
        redirectAttributes.addFlashAttribute("message","Thay Đổi Thành Công");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            NhanVien nhanVien = service.get(id);
            List<ChucVu> listChucVu = service.listChucVu();
            model.addAttribute("nhanVien", nhanVien);
            model.addAttribute("pageTitle","Update Nhân Viên (ID : " + id + ")");
            model.addAttribute("listChucVu",listChucVu);
            return "nhanvien/user_form";
        }catch (NhanVienNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
            return "redirect:/users";
        }

    }


    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes){
        try{
            service.delete(id);
            redirectAttributes.addFlashAttribute("message","Người dùng ID" + id + "đã xóa thành công");
        }catch (NhanVienNotFoundException ex){
            redirectAttributes.addFlashAttribute("message",ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateNhanVienEnabledStatus(@PathVariable("id") Integer id,
                                              @PathVariable("status") boolean enabled,
                                              RedirectAttributes redirectAttributes){
        service.updateNhanVienEnabledStatus(id, enabled);
        String status = enabled ? "online" : "offline";
        String message = "Nhân viên có id " + id + " thay đổi trạng thái thành " + status;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/users";
    }

    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<NhanVien> listNhanVien = service.listAll();
        NhanVienCsvExporter exporter = new NhanVienCsvExporter();
        exporter.export(listNhanVien,response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<NhanVien> listNhanVien = service.listAll();
        NhanVienExcelExporter exporter = new NhanVienExcelExporter();
        exporter.export(listNhanVien,response);

    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<NhanVien> listNhanVien = service.listAll();
        NhanVienPdfExporter exporter = new NhanVienPdfExporter();
        exporter.export(listNhanVien,response);

    }
}
