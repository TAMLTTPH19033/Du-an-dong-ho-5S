package com.dongho5s.admin.Service.Impl;

import com.dongho5s.admin.Entity.ChucVu;
import com.dongho5s.admin.Entity.NhanVien;
import com.dongho5s.admin.Exception.NhanVienNotFoundException;
import com.dongho5s.admin.Repository.ChucVuRepository;
import com.dongho5s.admin.Repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class NhanVienService {
    public static final int USERS_PER_PAGE = 4;

    @Autowired
    private NhanVienRepository nhanVienrepo;

    @Autowired
    private ChucVuRepository chucVuRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public NhanVien getByEmail(String email){
        return nhanVienrepo.getNhanVienByEmail(email);
    }

    public List<NhanVien> listAll(){

        return (List<NhanVien>) nhanVienrepo.findAll(Sort.by("ten").ascending());
    }

    public Page<NhanVien> listByPage(int pageNumber, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber -1, USERS_PER_PAGE, sort);

        if(keyword != null){
            return nhanVienrepo.findAll(keyword,pageable);
        }

        return nhanVienrepo.findAll(pageable);
    }

    public List<ChucVu> listChucVu(){
        return(List<ChucVu>) chucVuRepo.findAll();
    }

    public NhanVien save(NhanVien nhanVien) {
        boolean isUpdatingNhanVien = (nhanVien.getId() != null);

        if(isUpdatingNhanVien){
            NhanVien existingNhanVien = nhanVienrepo.findById(nhanVien.getId()).get();
            if(nhanVien.getMatKhau() == null || nhanVien.getMatKhau().isEmpty()){
                nhanVien.setMatKhau(existingNhanVien.getMatKhau());
            }else{
                encodePassword(nhanVien);
            }
        }else{
            encodePassword(nhanVien);
        }

        return nhanVienrepo.save(nhanVien);
    }

    public NhanVien nhanVienUpdateAccount(NhanVien nhanVienInForm){
        NhanVien nhanVienInDB = nhanVienrepo.findById(nhanVienInForm.getId()).get();
        if(nhanVienInForm.getMatKhau().isEmpty()){
            nhanVienInDB.setMatKhau(nhanVienInForm.getMatKhau());
            encodePassword(nhanVienInDB);
        }

        if(nhanVienInForm.getAnh() != null){
            nhanVienInDB.setAnh(nhanVienInForm.getAnh());
        }
        nhanVienInDB.setHo(nhanVienInForm.getHo());
        nhanVienInDB.setTen(nhanVienInForm.getTen());
        nhanVienInDB.setDiaChi(nhanVienInForm.getDiaChi());
        nhanVienInDB.setSoDienThoai(nhanVienInForm.getSoDienThoai());
        return nhanVienrepo.save(nhanVienInDB);

    }

    private void encodePassword(NhanVien nhanVien){
        String encodedPassword = passwordEncoder.encode(nhanVien.getMatKhau());
        nhanVien.setMatKhau(encodedPassword);
    }

    public boolean isEmailUnique(Integer id ,String email){
        NhanVien nhanVienByEmail = nhanVienrepo.getNhanVienByEmail(email);
        if(nhanVienByEmail == null) return true;
        boolean isCreateingNew = (id == null);
        if(isCreateingNew){
            if(nhanVienByEmail != null) return  false;
        }else {
            if(nhanVienByEmail.getId() != id){
                return false;
            }
        }

        return true;
    }

    public NhanVien get(Integer id) throws NhanVienNotFoundException {
        try{
            return nhanVienrepo.findById(id).get();
        }catch (NoSuchElementException ex){
            throw  new NhanVienNotFoundException("không tìm thấy nhân viên nào theo ID :" +id);
        }

    }

    public void delete(Integer id) throws NhanVienNotFoundException {
        Long countById = nhanVienrepo.countById(id);
        if (countById == null || countById == 0){
            throw  new NhanVienNotFoundException("không tìm thấy nhân viên nào theo ID :" +id);
        }
        nhanVienrepo.deleteById(id);
    }

    public  void updateNhanVienEnabledStatus(Integer id, boolean enabled){
        nhanVienrepo.updateEnabledStatus(id, enabled);
    }

}