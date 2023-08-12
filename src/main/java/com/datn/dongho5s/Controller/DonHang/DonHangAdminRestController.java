package com.datn.dongho5s.Controller.DonHang;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DonHang;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Response.DonHangAdminResponse;
import com.datn.dongho5s.Service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/don-hang")
public class DonHangAdminRestController {

    @Autowired
    private DonHangService donHangService;

    @GetMapping("/findByTrangThai/{trangThai}")
    public ResponseEntity<?> findByTrangThaiDonHang(
            @PathVariable("trangThai") int trangThai
    ){
        List<DonHangAdminResponse> result = new ArrayList<>();
        if(trangThai==7){
            List<DonHang> listDH  = donHangService.getAll(1).getContent();
            listDH.forEach(item->{
                try {
                    result.add(toDonHangAdminResponse(item));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }else{
            List<DonHang> listDH = donHangService.findByTrangThaiDonHang(trangThai);
            listDH.forEach(item->{
                try {
                    result.add(toDonHangAdminResponse(item));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private DonHangAdminResponse toDonHangAdminResponse (DonHang donHang) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String tinhThanh = DiaChiCache.hashMapTinhThanh.get(donHang.getIdTinhThanh()) ;
        String quanHuyen = DiaChiAPI.callGetQuanHuyenAPI(donHang.getIdTinhThanh()).get(donHang.getIdQuanHuyen());
        String phuongXa = DiaChiAPI.callGetPhuongXaAPI(donHang.getIdQuanHuyen()).get(donHang.getIdPhuongXa());
        String detailDiaChi = tinhThanh +"-"+ quanHuyen + "-" +phuongXa +"-" + donHang.getDiaChi();
        DonHangAdminResponse result = DonHangAdminResponse.builder()
                .idDonHang(donHang.getIdDonHang())
                .maDonHang(donHang.getMaDonHang())
                .idKhachHang(donHang.getKhachHang().getIdKhachHang())
                .ngayTao(formatter.format(donHang.getNgayTao()))
                .ngayCapNhap(formatter.format(donHang.getNgayCapNhap()))
                .trangThaiDonHang(donHang.getTrangThaiDonHang())
                .diaChi(detailDiaChi)
                .tongTien(donHang.getTongTien())
                .phiVanChuyen(donHang.getPhiVanChuyen())
                .ghiChu(donHang.getGhiChu())
                .lyDo(donHang.getLyDo())
                .build();
        if(donHang.getNhanVien()!= null){
            result.setIdNhanVien(donHang.getNhanVien().getId());
        }
        return result;
    }
}