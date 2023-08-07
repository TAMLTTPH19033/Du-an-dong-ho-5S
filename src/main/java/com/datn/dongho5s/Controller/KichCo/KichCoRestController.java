package com.datn.dongho5s.Controller.KichCo;

import com.datn.dongho5s.Service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KichCoRestController {
    @Autowired
    private KichCoService service;

    @PostMapping("/admin/sizes/check_name")
    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
        return service.checkUnique(id, ten) ? "OK" : "Duplicated";
    }
}
