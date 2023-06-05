package com.dongho5s.admin.Service.Impl;

import com.dongho5s.admin.Entity.DanhMuc;
import com.dongho5s.admin.Repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucService {
    @Autowired
    private DanhMucRepository repo;

    public List<DanhMuc> listAll(){
        return (List<DanhMuc>) repo.findAll();
    }
}
