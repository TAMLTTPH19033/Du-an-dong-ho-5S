package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.Seri;
import com.datn.dongho5s.Repository.SeriRepository;
import com.datn.dongho5s.Service.SeriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriServiceImpl implements SeriService {
    @Autowired
    SeriRepository repo;
    @Override
    public Seri save(Seri seri) {
        return repo.save(seri);
    }

    @Override
    public List<Seri> saveMany(List<Seri> seri) {
        return repo.saveAll(seri);
    }

    @Override
    public Seri get(Integer id) {
        if (repo.findById(id)!=null){
            return repo.findById(id).get();
        }
        return null;
    }

    @Override
    public Page<Seri> searchSeri(int pageNumber,int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        repo.findByIdImeiLike(keyword,pageable).getContent().forEach(item->{
            System.out.println(item.toString());
        });
        return repo.findByIdImeiLike(keyword,pageable);
    }
}
