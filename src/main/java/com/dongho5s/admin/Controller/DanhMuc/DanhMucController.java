package com.dongho5s.admin.Controller.DanhMuc;

import com.dongho5s.admin.Entity.DanhMuc;
import com.dongho5s.admin.Service.Impl.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
