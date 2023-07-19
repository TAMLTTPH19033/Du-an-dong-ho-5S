package com.datn.dongho5s.Controller.SanPham;

import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SanPhamCheckRestController {

    @Autowired
    SanPhamService service;

    @PostMapping("/products/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return service.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}
