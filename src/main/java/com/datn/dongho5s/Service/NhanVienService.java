package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.ChucVu;
import com.datn.dongho5s.Entity.NhanVien;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NhanVienService {

    public List<NhanVien> listAll();

    public Page<NhanVien> listByPage(int pageNumber, String sortField, String sortDir, String keyword);

    public List<ChucVu> listChucVu();

    public NhanVien save(NhanVien nhanVien);

    public NhanVien nhanVienUpdateAccount(NhanVien nhanVienInForm);

    public void encodePassword(NhanVien nhanVien);
}
