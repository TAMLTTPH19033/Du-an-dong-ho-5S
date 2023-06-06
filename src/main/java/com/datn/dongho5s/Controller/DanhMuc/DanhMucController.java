package com.datn.dongho5s.Controller.DanhMuc;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Service.impl.DanhMucService;
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
