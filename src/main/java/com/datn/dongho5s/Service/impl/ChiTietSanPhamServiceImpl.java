package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Response.TimKiemSettingResponse;
import com.datn.dongho5s.Service.ChiTietSanPhamService;
import com.datn.dongho5s.Service.DanhmucService;
import com.datn.dongho5s.Service.DayDeoService;
import com.datn.dongho5s.Service.KichCoService;
import com.datn.dongho5s.Service.MauSacService;
import com.datn.dongho5s.Service.ThuongHieuService;
import com.datn.dongho5s.Service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

//    @Autowired
//    DanhmucService danhmucService;
    @Autowired
    DayDeoService dayDeoService;
    @Autowired
    KichCoService kichCoService;
    @Autowired
    MauSacService mauSacService;
//    @Autowired
//    ThuongHieuService thuongHieuService;
    @Autowired
    VatLieuService vatLieuService;

    @Override
    public TimKiemSettingResponse getTimKiemSetting() {
        TimKiemSettingResponse result = new TimKiemSettingResponse();
//        result.setListDanhMuc(danhmucService.listAll());
        result.setListDayDeo(dayDeoService.getAllDayDeo());
        result.setListKichCo(kichCoService.getAllKichCo());
        result.setListMauSac(mauSacService.getAllMauSac());
//        result.setListThuongHieu(thuongHieuService.getAllThuongHieu());
        result.setListVatLieu(vatLieuService.getAllVatLieu());
        return result;
    }
}
