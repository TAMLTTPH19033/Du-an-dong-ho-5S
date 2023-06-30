package com.datn.dongho5s.Controller.RestController.GioHang;

import com.datn.dongho5s.Request.ChiTietGioHangRequest;
import com.datn.dongho5s.Request.CartRequest;
import com.datn.dongho5s.Response.ChiTietGioHangResponse;
import com.datn.dongho5s.Service.ChiTietGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/giohang")
public class GioHangRestController {
    @Autowired
    ChiTietGioHangService chiTietGioHangService;

    @GetMapping("")
    public ResponseEntity<?> getchiTiet(){
        List<ChiTietGioHangResponse> chiTietGioHangList = chiTietGioHangService.getChiTietGioHang();
        return ResponseEntity.status(HttpStatus.OK).body(chiTietGioHangList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateGioHang(@PathVariable("id") Integer idGioHangChiTiet, @RequestBody ChiTietGioHangRequest chiTietGioHang){
        return ResponseEntity.status(HttpStatus.OK).body(chiTietGioHangService.update(chiTietGioHang));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGioHang(@PathVariable("id") Integer idGioHangChiTiet){
        chiTietGioHangService.delete(idGioHangChiTiet);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllGioHang(){
        chiTietGioHangService.deleteAll();
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addGioHang(@RequestBody CartRequest cartResponse){
        System.out.println(cartResponse + "chiTiet");
        return ResponseEntity.status(HttpStatus.OK).body(chiTietGioHangService.addToCart(cartResponse.getChiTietSanPham(), cartResponse.getSoLuong()));
    }

}
