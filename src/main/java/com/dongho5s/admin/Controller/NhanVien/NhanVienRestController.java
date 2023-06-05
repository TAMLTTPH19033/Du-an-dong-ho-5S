package com.dongho5s.admin.Controller.NhanVien;

import com.dongho5s.admin.Service.Impl.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NhanVienRestController {
    @Autowired
    private NhanVienService service;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id ,@Param("email") String email){
        return service.isEmailUnique(id , email) ? "OK" : "Duplicated";
    }
}
