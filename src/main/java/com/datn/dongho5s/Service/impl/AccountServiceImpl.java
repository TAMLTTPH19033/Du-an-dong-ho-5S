package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Cache.DiaChiCache;
import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Entity.KhachHang;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Exception.CustomException.BadRequestException;
import com.datn.dongho5s.GiaoHangNhanhService.DiaChiAPI;
import com.datn.dongho5s.Repository.DiaChiRepository;
import com.datn.dongho5s.Repository.KhachHangRepository;
import com.datn.dongho5s.Repository.NhanVienRepository;
import com.datn.dongho5s.Request.ChangePassRequest;
import com.datn.dongho5s.Request.RegisterRequest;
import com.datn.dongho5s.Response.RegisterResponse;
import com.datn.dongho5s.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    DiaChiRepository diaChiRepository;
    @Override
    public ResponseEntity<?> register(RegisterRequest registerRequest) throws Exception {
//        try {
            KhachHang findKHbyEmail = khachHangRepository.getKhachHangByEmail(registerRequest.getEmail());
            if(registerRequest.getIdTinhThanh() ==null || registerRequest.getIdQuanHuyen()== null || registerRequest.getIdPhuongXa() == null){
                throw new BadRequestException("Mời bạn nhập địa chỉ ");
            }
            if(registerRequest.getNgaySinh().after(new Date())){
                throw new BadRequestException("Ngày sinh sai rùi ");
            }
        if(registerRequest.getPassword().isEmpty()){
            throw new BadRequestException("Password không được trống ");
        }
            if (findKHbyEmail != null) {
                throw new BadRequestException("Email đã tồn tại ");
            } else {
                KhachHang khachHang = KhachHang.builder()
                        .idKhachHang(null)
                        .email(registerRequest.getEmail())
                        .enabled(true)
                        .gioiTinh(registerRequest.getGioiTinh())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .tenKhachHang(registerRequest.getTenKhachHang())
                        .ngaySinh(registerRequest.getNgaySinh())
                        .ngaySua(new Date())
                        .soDienThoai(registerRequest.getSoDienThoai())
                        .thoiGianTaoTaiKhoan(new Timestamp(new Date().getTime()))

                        .build();
                KhachHang khachHang1 = khachHangRepository.save(khachHang);

                DiaChi diaChi = DiaChi.builder()
                        .diaChi(registerRequest.getDiaChi())
                        .idTinhThanh(registerRequest.getIdTinhThanh())
                        .idQuanHuyen(registerRequest.getIdQuanHuyen())
                        .idPhuongXa(registerRequest.getIdPhuongXa())
                        .ghiChu("")
                        .maBuuChinh(123)
                        .khachHang(khachHang1)
                        .trangThaiMacDinh(0)
                        .soDienThoai(registerRequest.getSoDienThoai())
                        .build();
                DiaChi diaChi1 = diaChiRepository.save(diaChi);
                return new ResponseEntity<>(khachHang1, HttpStatus.OK);
            }
//        }catch (Exception e){
//            throw new Exception("Đăng nhập thất bại");
//        }
    }

    @Override
    public HashMap<Integer,String> getListTP(){
        return DiaChiCache.hashMapTinhThanh;
    }

    @Override
    public HashMap<Integer, String> getListQuan( Integer idTP) throws Exception {
        DiaChiAPI.callGetQuanHuyenAPI(idTP);
        return DiaChiCache.hashMapQuanHuyen.get(idTP);
    }

    @Override
    public HashMap<String, String> getListPhuong( Integer idQH) throws Exception {
        DiaChiAPI.callGetPhuongXaAPI(idQH);
        return DiaChiCache.hashMapPhuongXa.get(idQH);

    }


    @Override
    public ResponseEntity<?> changePass(Principal p ,ChangePassRequest changePassRequest) throws Exception {
        String email = p.getName();
        KhachHang user = khachHangRepository.getKhachHangByEmail(email);
        boolean f = passwordEncoder.matches(changePassRequest.getOldPass(), user.getPassword());
        if(f){
            if(!changePassRequest.getNewPass().equals(changePassRequest.getConfirmPass())){
              throw  new BadRequestException("Xác nhận mật khẩu không khớp");
            }else {
                user.setPassword(passwordEncoder.encode(changePassRequest.getNewPass()));
                 khachHangRepository.save(user);
                return ResponseEntity.status(HttpStatus.OK).body( khachHangRepository.save(user));
            }
        }else {
            throw new BadRequestException("Mật khẩu không đúng");
        }
    }

}
