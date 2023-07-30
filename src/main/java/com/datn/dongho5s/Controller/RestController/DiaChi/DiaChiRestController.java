package com.datn.dongho5s.Controller.RestController.DiaChi;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Service.DiaChiService;
import com.datn.dongho5s.Service.impl.DiaChiServiceImpl;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/dia-chi")
public class DiaChiRestController {
    private final DiaChiServiceImpl diaChiServiceImpl;
    private final DiaChiService diaChiService;

    @GetMapping("/find-all")
    public ResponseEntity<List<DiaChi>> getAllDiaChi(){
        List<DiaChi> lstDiaChi = diaChiServiceImpl.getAllDiaChi();
        return ResponseEntity.status(HttpStatus.OK).body(lstDiaChi);
    }

    @PostMapping("/them-dia-chi")
    public ResponseEntity<DiaChi> createDiaChi (@RequestBody DiaChiRequest diaChiRequest) {

        DiaChi result = diaChiServiceImpl.createDiaChi(diaChiRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/get-default-dia-chi/khach-hang={idKhachHang}")
    public ResponseEntity<List<DiaChi>> getDefaultDiaChi (@PathVariable(value = "idKhachHang") KhachHang khachHang){
        List<DiaChi> result = diaChiService.getAllDiaChiByKhachHang(khachHang);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/get-tinh-thanh")
    public ResponseEntity<HashMap<Integer,String>> getThanhPho (){
        return ResponseEntity.ok(DiaChiCache.hashMapTinhThanh);
    }

    @GetMapping("/get-quan-huyen/{idThanhPho}")
    public ResponseEntity<HashMap<Integer,String>> getQuanHuyen (@PathVariable ("idThanhPho") Integer id){
        try {
            return ResponseEntity.ok(DiaChiAPI.callGetQuanHuyenAPI(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get-phuong-xa/{idQuanHuyen}")
    public ResponseEntity<HashMap<String,String>> getPhuongXa (@PathVariable ("idQuanHuyen") Integer id){
        try {
            return ResponseEntity.ok(DiaChiAPI.callGetPhuongXaAPI(id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
