package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Repository.SanPhamRepository;
import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;
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

    private TimKiemResponse toTimKiemResponse (SanPham sp) {
        TimKiemResponse result = new TimKiemResponse();
        result.setSanPhamID(sp.getIdSanPham());
        result.setTenSanPham(sp.getTenSanPham());
        result.setGiaSanPham(sp.getGiaSanPham());
        result.setLinkAnh(sp.getAnhSanPham().getLink());
        return result;
    }
    @Override
    public List<SanPham> getSPnew() {
        return sanPhamRepository.getSPnew();
    }

    @Override
    public List<SanPham> getSPchay() {
        return sanPhamRepository.getSPchay();

    }
    @Override
    public SanPhamDetailResponse getDetailSanPhamById(Integer sanPhamId) {
        SanPham sanPham = sanPhamRepository.findById(sanPhamId).get();
        return toSanPhamRepository(sanPham);
    }

    private SanPhamDetailResponse toSanPhamRepository(SanPham sp){
        return SanPhamDetailResponse.builder()
                .idSanPham(sp.getIdSanPham())
                .anhSanPham(sp.getAnhSanPham())
                .giaSanPham(sp.getGiaSanPham())
                .moTaSanPham(sp.getMoTaSanPham())
                .tenSanPham(sp.getMoTaSanPham())
                .danhMuc(sp.getDanhMuc())
                .listChiTietSanPham(sp.getListChiTietSanPham())
                .thuongHieu(sp.getThuongHieu())
                .trangThai(sp.getTrangThai())
                .build();
    }
}
