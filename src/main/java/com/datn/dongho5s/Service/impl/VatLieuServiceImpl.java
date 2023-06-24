package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Exception.MaterialNotFoundException;
import com.datn.dongho5s.Repository.VatLieuRepository;
import com.datn.dongho5s.Service.VatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VatLieuServiceImpl implements VatLieuService {

    public static final int MATERIALS_PER_PAGE = 4;
    @Autowired
    private VatLieuRepository vatLieuRepo;

    public List<VatLieu> listAll(){
        return (List<VatLieu>) vatLieuRepo.findAll(Sort.by("ten").ascending());
    }

    public Page<VatLieu> listByPage(int pageNumber, String sortField, String sortDir, String keyword){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber -1, MATERIALS_PER_PAGE, sort);
        if (keyword != null){
            return vatLieuRepo.findAll(keyword, pageable);
        }
        return  vatLieuRepo.findAll(pageable);
    }
    public VatLieu save(VatLieu vl) {
        return vatLieuRepo.save(vl);
    }

    public VatLieu get(Integer id) throws MaterialNotFoundException {
        Optional<VatLieu> result = vatLieuRepo.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new MaterialNotFoundException("Could not find any materials with ID" + id);
    }


    public void updateVatLieuEnabledStatus(Integer id, boolean enabled){
        vatLieuRepo.updateEnabledStatus(id, enabled);
    }

}
