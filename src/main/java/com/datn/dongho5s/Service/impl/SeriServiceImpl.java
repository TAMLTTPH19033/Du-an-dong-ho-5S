package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.Seri;
import com.datn.dongho5s.Repository.SeriRepository;
import com.datn.dongho5s.Service.SeriService;
import com.datn.dongho5s.Utils.TrangThaiImei;
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
        });
        return repo.findByIdImeiLike(keyword,pageable);
    }

    @Override
    public Integer countSeri(Integer idChiTietSanPham) {
        return repo.countSeri(idChiTietSanPham);
    }

    @Override
    public List<Seri> findByChiTietSanPham(ChiTietSanPham chiTietSanPham, Integer soLuong) {
        Pageable pageable = PageRequest.of(0, soLuong);
        return repo.findByChiTietSanPhamAndTrangThai(chiTietSanPham, TrangThaiImei.CHUA_BAN,pageable);
    }

}
