package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Repository.SanPhamRepository;
import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    SanPhamRepository sanPhamRepository;

    @Override
    public List<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest) {

        List<SanPham> listSanPham = sanPhamRepository.getListSanPhamByCondition(timKiemRequest);
        List<TimKiemResponse> result = new ArrayList<>();
        listSanPham.forEach(sanPham -> result.add(toTimKiemResponse(sanPham)));
        return result;
    }

    private TimKiemResponse toTimKiemResponse (SanPham sp){
        TimKiemResponse result = new TimKiemResponse();
        result.setSanPhamID(sp.getIdSanPham());
        result.setTenSanPham(sp.getTenSanPham());
        result.setGiaSanPham(sp.getGiaSanPham());
        result.setLinkAnh(sp.getAnhSanPham().getLink());
        return result;
    }
}
