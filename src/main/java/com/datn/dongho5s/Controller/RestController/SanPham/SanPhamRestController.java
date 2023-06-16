package com.datn.dongho5s.Controller.RestController.SanPham;

import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Response.TimKiemSettingResponse;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/san-pham")
public class SanPhamRestController {
    @Autowired
    SanPhamService sanPhamService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @PostMapping("/tim-kiem")
    public ResponseEntity<?> TimKiemSanPham (@RequestBody TimKiemRequest timKiemRequest){
//        try {
            List<TimKiemResponse> result = sanPhamService.getSanPhamByCondition(timKiemRequest);
            return ResponseEntity.status(HttpStatus.OK).body(result);
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        }
    }

    @GetMapping("/get-setting")
    public ResponseEntity<?> GetSettingTimKiem (){
        TimKiemSettingResponse result = chiTietSanPhamService.getTimKiemSetting();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}