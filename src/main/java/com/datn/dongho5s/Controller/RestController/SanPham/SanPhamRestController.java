package com.datn.dongho5s.Controller.RestController.SanPham;

import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/san-pham")
public class SanPhamRestController {
    @Autowired
    SanPhamService sanPhamService;

    @PostMapping("/tim-kiem/page={page}/size={size}")
    public ResponseEntity<?> TimKiemSanPham (@RequestBody TimKiemRequest timKiemRequest,
    @PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("aaa");
//        try {
            List<TimKiemResponse> result = sanPhamService.getSanPhamByCondition(timKiemRequest, page, size);
            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        }
    }

}
