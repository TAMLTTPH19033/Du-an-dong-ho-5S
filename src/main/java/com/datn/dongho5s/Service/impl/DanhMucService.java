package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Exception.DanhMucNotFoundException;
import com.datn.dongho5s.Exception.NhanVienNotFoundException;
import com.datn.dongho5s.Repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class DanhMucService {
    public static final int CATEGORIES_PER_PAGE = 4;
    @Autowired
    private DanhMucRepository repo;

//    public List<DanhMuc> listAll(){
//        return (List<DanhMuc>) repo.findAll();
//    }

    public List<DanhMuc> listAll(){
        return (List<DanhMuc>) repo.findAll(Sort.by("ten").ascending());
    }

    public Page<DanhMuc> listByPage(int pageNumber, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);

        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber -1, CATEGORIES_PER_PAGE, sort);

        if(keyword != null){
            return repo.findAll(keyword,pageable);
        }

        return repo.findAll(pageable);
    }

    public void updateDanhMucEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id,enabled);
    }

    public DanhMuc save(DanhMuc danhMuc){
        return repo.save(danhMuc);
    }

    public DanhMuc get(Integer id) throws DanhMucNotFoundException {
        try{
            return repo.findById(id).get();
        }catch (NoSuchElementException ex){
            throw  new DanhMucNotFoundException("không tìm thấy danh mục nào theo ID :" +id);
        }
    }

    public String checkUnique(Integer id, String ten){
        boolean isCreatingNew = (id == null || id == 0);
        DanhMuc danhMucTheoTen = repo.findByTen(ten);

        if(isCreatingNew){
            if(danhMucTheoTen != null){
                return "DuplicateTen";
            }
        }else{
            if(danhMucTheoTen != null && danhMucTheoTen.getId() != id){
                return "DuplicateTen";
            }
        }
        return "OK";
    }
}
