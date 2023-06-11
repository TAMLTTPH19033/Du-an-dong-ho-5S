package com.datn.dongho5s.Controller;

import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Response.TrangChuResponse;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@CrossOrigin("*")
@Controller
@RequestMapping("/api/index")
public class TrangChuRestController {
    @Autowired
    SanPhamService sanPhamService;

    @ResponseBody
    @GetMapping("")
   public ResponseEntity<?> home(){
        System.out.println("aaa");
         List<SanPham> listSPbanChay = sanPhamService.getSPchay();
         List<SanPham> listSPmoiNhat = sanPhamService.getSPnew();
        TrangChuResponse trangChuResponse = new TrangChuResponse(listSPbanChay,listSPmoiNhat);
        return ResponseEntity.status(HttpStatus.OK).body(trangChuResponse);
    }
}
