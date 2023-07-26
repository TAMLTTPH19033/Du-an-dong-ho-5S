package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.Repository.DonHangRepository;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DonHangServiceImpl implements DonHangService {

    public static final int DONHANG_PAGE = 10;

    @Autowired
    DonHangRepository donHangRepository;

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
}
