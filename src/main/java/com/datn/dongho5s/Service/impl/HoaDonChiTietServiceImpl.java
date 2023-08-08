package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.ChiTietSanPham;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Entity.HoaDonChiTiet;
import com.datn.dongho5s.Repository.ChiTietSanPhamRepository;
import com.datn.dongho5s.Repository.HoaDonChiTietRepository;
import com.datn.dongho5s.Request.HoaDonChiTietRequest;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.DonHangService;
import com.datn.dongho5s.Service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;
    @Autowired
    DonHangService donHangService;
    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @Override
    public HoaDonChiTiet save(HoaDonChiTiet hdct) {
        return hoaDonChiTietRepository.save(hdct);
    }

    @Override
    public List<HoaDonChiTiet> convertToListHoaDonChiTiet(List<HoaDonChiTietRequest> list, Integer idDonHang) {
        List<HoaDonChiTiet> result = new ArrayList<>();
        list.forEach(item -> {
            ChiTietSanPham ctsp = chiTietSanPhamService.getChiTietSanPhamById(item.getIdChiTietSanPham());
            HoaDonChiTiet hdct = HoaDonChiTiet.builder()
                    .donHang(donHangService.getById(idDonHang))
                    .chiTietSanPham(ctsp)
                    .soLuong(item.getSoLuong())
                    .giaBan(ctsp.getGiaSanPham())
                    .build();
            result.add(hdct);
        });
        return result;
    }

    @Override
    public List<HoaDonChiTiet> saveAll(List<HoaDonChiTiet> listHDCT) {
        hoaDonChiTietRepository.saveAll(listHDCT);
        return null;
    }

    @Override
    public Double getTongGia(List<HoaDonChiTietRequest> list) {
        Double result = 0D;
        for (int i = 0; i < list.size(); i++) {
            Double giaBan = chiTietSanPhamService.getChiTietSanPhamById(list.get(i).getIdChiTietSanPham()).getGiaSanPham();
            result += list.get(i).getSoLuong() * giaBan;
        }
        return result;
    }

    @Override
    public List<HoaDonChiTiet> getByIdDonHang(int id) {
        return hoaDonChiTietRepository.findHDCTBYIdDonHang(id);
    }
  
    @Override
    public List<HoaDonChiTiet> getByHoaDonId(DonHang donHang) {
        return hoaDonChiTietRepository.findByDonHang(donHang);
    }

    @Override
    public Page<HoaDonChiTiet> getHDCTByMaDonHang(String maDonHang, int pageNum) {
        Page<HoaDonChiTiet> hoaDonChiTietPage = hoaDonChiTietRepository.findByMaDonHang(maDonHang, PageRequest.of(pageNum - 1, 5));
        return hoaDonChiTietPage;
    }

    @Override
    public void themSoLuongSanPham(
            int soLuong,
            ChiTietSanPham chiTietSanPham,
            DonHang donHang
    ) {
        //if not exist
        int existIdHCT = -1;
        for (HoaDonChiTiet hoaDonChiTiet : donHang.getListHoaDonChiTiet()){
            if (hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham() == chiTietSanPham.getIdChiTietSanPham()){
                existIdHCT = hoaDonChiTiet.getIdHoaDonChiTiet();
                break;
            }
        }
        if (existIdHCT==-1){
            chiTietSanPhamRepository.updateSoLuongCTSPById(soLuong,chiTietSanPham.getIdChiTietSanPham());
            hoaDonChiTietRepository.save(HoaDonChiTiet
                    .builder()
                    .chiTietSanPham(chiTietSanPham)
                    .donHang(donHang)
                    .giaBan(chiTietSanPham.getGiaSanPham())
                    .soLuong(soLuong)
                    .build());
        } else{ // else ctsp exist -> update quantity by idHDCT
            chiTietSanPhamRepository.updateSoLuongCTSPById(soLuong,chiTietSanPham.getIdChiTietSanPham());
            hoaDonChiTietRepository.updateSoLuongSanPham(soLuong,existIdHCT);
        }
    }

    @Override
    public void xoaHDCT(
            HoaDonChiTiet hoaDonChiTiet
    ){
        // update lại số lượng sản phẩm
        chiTietSanPhamRepository.updateSoLuongFromHDCT(hoaDonChiTiet.getSoLuong(),hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham());
        // xoa HDCT
        hoaDonChiTietRepository.xoaHDCT(hoaDonChiTiet.getIdHoaDonChiTiet());
    }

    @Override
    public HoaDonChiTiet findHoaDonChiTietById(
            int id
    ){
        return hoaDonChiTietRepository.findByIdHoaDonChiTiet(id);
    }
    @Override
    public void updateSoLuongInHDCT(
            HoaDonChiTiet hoaDonChiTiet,
            int soLuongThayDoi
    ){
        hoaDonChiTietRepository.updateSoLuongSanPhamWithEdit(soLuongThayDoi,hoaDonChiTiet.getIdHoaDonChiTiet());
        // cap nhat lai so luong da thay doi
        int slHienTai = hoaDonChiTiet.getSoLuong();
        chiTietSanPhamRepository.updateSoLuongFromHDCT(slHienTai - soLuongThayDoi,hoaDonChiTiet.getChiTietSanPham().getIdChiTietSanPham());
    }
}
