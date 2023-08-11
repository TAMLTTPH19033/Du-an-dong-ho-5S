package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Repository.DonHangRepository;
import com.datn.dongho5s.Repository.HoaDonChiTietRepository;
import com.datn.dongho5s.Request.DonHangRequest;
import com.datn.dongho5s.Response.DonHangResponse;
import com.datn.dongho5s.Response.HoaDonChiTietResponse;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.mapper.DonHangMapping;
import com.datn.dongho5s.mapper.HoaDonChiTietMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonHangServiceImpl implements DonHangService {

    public static final int DONHANG_PAGE = 10;

    @Autowired
    DonHangRepository donHangRepository;

    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public DonHang save(DonHang donHang) {
        return donHangRepository.save(donHang);
    }

    @Override
    public DonHang getById(Integer idDonHang) {
        Optional<DonHang> optionalDonHang = donHangRepository.findById(idDonHang);
        if (optionalDonHang.isPresent()) {
            return optionalDonHang.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<DonHang> getAll(int pageNumber) {
        Page<DonHang> allDonHang = donHangRepository.findAll(PageRequest.of(pageNumber - 1, DONHANG_PAGE));
        return allDonHang;
    }


    @Override
    public List<DonHang> findByNgayTao(
            Date dateStart,
            Date dateEnd
    ) {
        List<DonHang> allDonHang = donHangRepository.findByNgayTao(dateStart, dateEnd);
        return allDonHang;
    }

    @Override
    public Double tongTien(int id) {
        return donHangRepository.findTongTienByIdDonHang(id);
    }

    @Override
    public DonHang findById(int id) {
        return donHangRepository.findByIdDonHang(id);
    }

    @Override
    public void updateTrangThaiDonHang(DonHang donHang) {
        donHangRepository.updateTrangThaiDonHang(donHang);
    }

    @Override
    public List<HoaDonChiTietResponse> findHDCTbyDH(Integer idDonhang) {
        List<HoaDonChiTiet> listHDCT = donHangRepository.findHDCTbyDH(idDonhang);
        List<HoaDonChiTietResponse> responseList = listHDCT.stream().map(HoaDonChiTietMapping::mapEntitytoResponse).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<DonHangResponse> findAllHD(Integer idKhachHang) {
        List<DonHang> listHD = donHangRepository.findAllHD(idKhachHang);
        List<DonHangResponse> responseList = listHD.stream().map(DonHangMapping::mapEntitytoResponseBT).collect(Collectors.toList());
        responseList.sort((o1,o2) -> o2.getNgayTao().compareTo(o1.getNgayTao()));
        return responseList;
    }

    @Override
    public List<DonHangResponse> findHDByStatus(Integer idKhachHang, Integer trangThaiDonHang) {
        List<DonHang> listHD = donHangRepository.findHDByStatus(idKhachHang,trangThaiDonHang);
        List<DonHangResponse> responseList = listHD.stream().map(DonHangMapping::mapEntitytoResponse).collect(Collectors.toList());
        responseList.sort((o1,o2) -> o2.getNgayTao().compareTo(o1.getNgayTao()));
        return responseList;
    }

    @Override
    public DonHangResponse updateDH(DonHangRequest donHangRequest) {

        try{

        DonHang donHang = donHangRepository.findByIdDonHang(donHangRequest.getIdDonHang());
        if(donHang != null){
            if(donHangRequest.getLyDo() == null){
                return null ;
            }else {
                donHang.setLyDo(donHangRequest.getLyDo());
                donHang.setTrangThaiDonHang(donHangRequest.getTrangThaiDonHang());
                donHang.setNgayCapNhap(new Date());
                donHang = donHangRepository.save(donHang);
            }
        }

        DonHangResponse donHangResponse = DonHangMapping.mapEntitytoResponse(donHang);
        return donHangResponse;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<DonHang> findByTrangThaiDonHang(int trangThai) {
        return donHangRepository.findByTrangThaiDonHang(trangThai);
    }

    @Override
    public Page<DonHang> getAllForBanHang(int pageNum) {
        Page<DonHang> allDonHang = donHangRepository.findAllSort(PageRequest.of(pageNum - 1, 5));
        return allDonHang;
    }

    @Override
    public DonHang findByMaDonHang(String maDonHang) {
        return donHangRepository.findByMaDonHang(maDonHang);
    }

    @Override
    public String thanhToanAdmin(DonHang donHang){
        donHangRepository.updateTrangThaiDonHang(donHang);
        return "Thanh toan thanh cong don hang " + donHang.getMaDonHang();
    }

    @Override
    public String xoaDonHangAdmin(DonHang donHang){
        hoaDonChiTietRepository.deleteByDonHang(donHang);
        donHangRepository.deleteByMaDonHang(donHang.getMaDonHang());
        return "Delete succcessful! Code is" + donHang.getMaDonHang();
    }
}
