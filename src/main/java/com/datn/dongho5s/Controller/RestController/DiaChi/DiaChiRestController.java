package com.datn.dongho5s.Controller.RestController.DiaChi;

import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Request.DiaChiRequest;
import com.datn.dongho5s.Service.impl.DiaChiServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/dia-chi")
public class DiaChiRestController {
    private final DiaChiServiceImpl diaChiServiceImpl;

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
}
