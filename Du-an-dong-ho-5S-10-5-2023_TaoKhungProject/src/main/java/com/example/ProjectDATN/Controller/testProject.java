package com.example.ProjectDATN.Controller;

import com.example.ProjectDATN.Entity.NhanVien;
import com.example.ProjectDATN.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class testProject {

    @Autowired
    NhanVienRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<?> getNV(@PathVariable("id") Integer idNv){
        NhanVien nv =  repo.findById(idNv).get();
        return ResponseEntity.ok(nv);
    }
}
