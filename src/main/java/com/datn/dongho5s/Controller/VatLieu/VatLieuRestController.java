//package com.datn.dongho5s.Controller.VatLieu;
//
//import com.datn.dongho5s.Service.VatLieuService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class VatLieuRestController {
//    @Autowired
//    private VatLieuService service;
//
//    @PostMapping("/materials/check_name")
//    public String checkDuplicateTen(@Param("id") Integer id , @Param("ten") String ten) {
//        return service.checkUnique(id, ten) ? "OK" : "Duplicated";
//    }
//}
