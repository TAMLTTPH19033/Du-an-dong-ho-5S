package com.datn.dongho5s.Controller.SanPham;

import com.datn.dongho5s.Service.DanhmucService;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SanPhamUniqueRestController {
    @Autowired
    private SanPhamService service;

    @PostMapping("/admin/products/check_name")
    public String checkDuplicateTen(@Param("ten") String ten) {
        return service.checkUnique(ten) ? "OK" : "Duplicated";
    }
}
