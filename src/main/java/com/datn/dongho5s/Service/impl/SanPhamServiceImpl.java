package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Repository.SanPhamRepository;
import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.ChiTietSanPhamResponse;
import com.datn.dongho5s.Response.SanPhamDetailResponse;
import com.datn.dongho5s.Response.TimKiemResponse;
import com.datn.dongho5s.Service.SanPhamService;
import com.datn.dongho5s.mapper.ChiTietSanPhamMapping;
import com.datn.dongho5s.mapper.SanPhamMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public Set<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest) {
        List<SanPham> listSanPham = sanPhamRepository.getListSanPhamByCondition(timKiemRequest);
        Set<TimKiemResponse> result = new HashSet<>();
        listSanPham.forEach(sanPham -> result.add(toTimKiemResponse(sanPham)));
        return result;
    }

    private TimKiemResponse toTimKiemResponse (SanPham sp) {
        TimKiemResponse result = new TimKiemResponse();
        result.setSanPhamID(sp.getIdSanPham());
        result.setTenSanPham(sp.getTenSanPham());
//        result.setGiaSanPham(sp.getGiaSanPham());
        result.setLinkAnh(sp.getListAnhSanPham().get(0).getLink());
        return result;
    }
    @Override
    public List<SanPham> getSPnew() {
         sanPhamRepository.getSPnew();
        return  sanPhamRepository.getSPnew();
    }

    @Override
    public List<SanPhamDetailResponse> getSPchay() {
        List<SanPham> listSanPham =  sanPhamRepository.getSPchay();
        List<SanPhamDetailResponse> responseList = listSanPham.stream().map(SanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;

    }
    @Override
    public SanPhamDetailResponse getDetailSanPhamById(Integer sanPhamId) {
        SanPham sanPham = sanPhamRepository.findById(sanPhamId).get();
        return toSanPhamRepository(sanPham);
    }

    private SanPhamDetailResponse toSanPhamRepository(SanPham sp){
        return SanPhamDetailResponse.builder()
                .idSanPham(sp.getIdSanPham())
                .listAnhSanPham(sp.getListAnhSanPham())
                .giaSanPham(sp.getGiaSanPham())
                .moTaSanPham(sp.getMoTaSanPham())
                .tenSanPham(sp.getMoTaSanPham())
                .danhMuc(sp.getDanhMuc())
                .listChiTietSanPham(sp.getListChiTietSanPham())
                .thuongHieu(sp.getThuongHieu())
                .trangThai(sp.getTrangThai())
                .build();
    }
    public List<ChiTietSanPhamResponse> getSPchayKM(Integer idChiTietSanPham) {
        List<ChiTietSanPham> chiTietSanPhamList = new ArrayList<>();
        List<ChiTietSanPham> chiTietSanPhams = sanPhamRepository.getCTSP(idChiTietSanPham);
        for (ChiTietSanPham chiTietSanPham : chiTietSanPhams) {
            if(chiTietSanPham.getKhuyenMai() == null || chiTietSanPham.getKhuyenMai().isEnabled() == false ){
                chiTietSanPhamList.add(chiTietSanPham);
            }else{
                chiTietSanPham.setGiaSanPham(chiTietSanPham.getGiaSanPham() - chiTietSanPham.getGiaSanPham() * chiTietSanPham.getKhuyenMai().getChietKhau() /100);
                chiTietSanPhamList.add(chiTietSanPham);
            }
        }
        return chiTietSanPhamList.stream().map(ChiTietSanPhamMapping::mapEntitytoResponse).collect(Collectors.toList());

    }
}
