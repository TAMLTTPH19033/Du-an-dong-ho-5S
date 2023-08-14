package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.SanPham;
import com.datn.dongho5s.Exception.ChiTietSanPhamNotFountException;
import com.datn.dongho5s.Exception.SanPhamNotFountException;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;
import com.datn.dongho5s.Repository.SeriRepository;
import com.datn.dongho5s.Response.SanPhamAdminResponse;
import com.datn.dongho5s.Response.TimKiemSettingResponse;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.DanhmucService;
import com.datn.dongho5s.Service.DayDeoService;
import com.datn.dongho5s.Service.KichCoService;
import com.datn.dongho5s.Service.MauSacService;
import com.datn.dongho5s.Service.ThuongHieuService;
import com.datn.dongho5s.Service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    @Autowired
    DanhmucService danhmucService;
    @Autowired
    DayDeoService dayDeoService;
    @Autowired
    KichCoService kichCoService;
    @Autowired
    MauSacService mauSacService;
    @Autowired
    ThuongHieuService thuongHieuService;
    @Autowired
    VatLieuService vatLieuService;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    SeriRepository seriRepository;

    @Override
    public TimKiemSettingResponse getTimKiemSetting() {
        TimKiemSettingResponse result = new TimKiemSettingResponse();
        result.setListDanhMuc(danhmucService.listAll());
        result.setListDayDeo(dayDeoService.getAllDayDeo());
        result.setListKichCo(kichCoService.getAllKichCo());
        result.setListMauSac(mauSacService.getAllMauSac());
        result.setListThuongHieu(thuongHieuService.getAllThuongHieu());
        result.setListVatLieu(vatLieuService.getAllVatLieu());
        return result;
    }

    @Override
    public ChiTietSanPham getChiTietSanPhamById(Integer id) {
        return chiTietSanPhamRepository.findById(id).get();
    }

    @Override
    public ChiTietSanPham update(ChiTietSanPham chiTietSanPham) {

        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public Page<ChiTietSanPham> findByMaSP(String maSanPham, int pageNum) {
        return chiTietSanPhamRepository.findByMaSP(maSanPham,PageRequest.of(pageNum - 1, 5));
    }
    @Override
    public Page<ChiTietSanPham> getALlChiTietSanPhamPage(int pageNum, String keyword){
        return chiTietSanPhamRepository.findAllHung(keyword,PageRequest.of(pageNum - 1, 5));
    }

    @Override
    public List<SanPhamAdminResponse> getAllSanPhamAminResponse(int pageNum,String keyword) {
        List<ChiTietSanPham> lstChiTietSanPhams = getALlChiTietSanPhamPage(pageNum,keyword).getContent();

        List<SanPhamAdminResponse> lst = lstChiTietSanPhams
                .stream()
                .map(lstSP -> SanPhamAdminResponse
                        .builder()
                        .idChiTietSanPham(lstSP.getIdChiTietSanPham())
                        .maChiTietSanPham(lstSP.getMaChiTietSanPham())
                        .giaSanPham(lstSP.getGiaSanPham())
                        .sanPham(lstSP.getSanPham())
                        .vatLieu(lstSP.getVatLieu())
                        .mauSac(lstSP.getMauSac())
                        .khuyenMai(lstSP.getKhuyenMai())
//                        .soLuong(lstSP.getSoLuong())
                        .soLuong(seriRepository.countByIdCTSPEnabled(lstSP.getIdChiTietSanPham()))  // find All Imei status is enabled
                        .build()).collect(Collectors.toList());
        return lst;
    }

    @Override
    public ChiTietSanPham findByMaChiTietSanPham(String maChimaTietSanPham) {
        return chiTietSanPhamRepository.findByMaChiTietSanPham(maChimaTietSanPham);
    }

    public ChiTietSanPham getChiTietSanPhamByMa(String ma) {
        return chiTietSanPhamRepository.findByMaChiTietSanPham(ma);
    }

    @Override
    public List<ChiTietSanPham> listAll(){
        return chiTietSanPhamRepository.findAll(Sort.by("maChiTietSanPham").ascending());
    }

    @Override
    public Page<ChiTietSanPham> listByPage(int pageNumber,String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1 ,PRODUCT_DETAIL_PER_PAGE, sort);
        if (keyword != null){
            return chiTietSanPhamRepository.findAll(keyword,pageable);
        }
        return chiTietSanPhamRepository.findAll(pageable);

    }

    @Override
    public ChiTietSanPham save(ChiTietSanPham chiTietSanPham) {
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham get(Integer id) throws ChiTietSanPhamNotFountException {
        try{
            return chiTietSanPhamRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new ChiTietSanPhamNotFountException("không tìm thấy sản phẩm có id" + id);
        }
    }
}
