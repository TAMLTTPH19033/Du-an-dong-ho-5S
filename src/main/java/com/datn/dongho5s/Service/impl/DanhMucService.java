package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Repository.DanhMucRepository;
import com.datn.dongho5s.Service.DanhmucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucService implements DanhmucService {
    @Autowired
    private DanhMucRepository repo;

    public List<DanhMuc> listAll(){
        return (List<DanhMuc>) repo.findAll();
    }
}
