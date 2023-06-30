package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Exception.KhuyenMaiNotFoundException;
import com.datn.dongho5s.Repository.KhuyenMaiRepository;
import com.datn.dongho5s.Service.KhuyenMaiService;
import com.datn.dongho5s.Utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    @Autowired
    private KhuyenMaiRepository repo;

    @Override
    public List<KhuyenMai> listAll() {
        return (List<KhuyenMai>) repo.findAll(Sort.by("tenKhuyenMai").ascending());
    }

    @Override
    public Page<KhuyenMai> listByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber -1, DISCOUNT_PER_PAGE, sort);
        if(keyword != null){
            return repo.findAll(keyword,pageable);
        }
        return repo.findAll(pageable);
    }

    @Override
    public KhuyenMai save(KhuyenMai khuyenMai) {
        // Chuyển đổi ngày bắt đầu và ngày kết thúc trước khi lưu vào cơ sở dữ liệu
        String ngayBatDauString = DateConverter.formatDateForDatabase(khuyenMai.getNgayBatDau());
        khuyenMai.setNgayBatDau(DateConverter.parseDateFromDatabase(ngayBatDauString));

        String ngayKetThucString = DateConverter.formatDateForDatabase(khuyenMai.getNgayKetThuc());
        khuyenMai.setNgayKetThuc(DateConverter.parseDateFromDatabase(ngayKetThucString));

        // Tiếp tục lưu đối tượng KhuyenMai vào cơ sở dữ liệu
        return repo.save(khuyenMai);
    }

    @Override
    public KhuyenMai get(Integer id) throws KhuyenMaiNotFoundException, Exception {
        try{
            return repo.findById(id).orElseThrow(()->new KhuyenMaiNotFoundException("Không tìm thấy Khuyến Mãi nào theo ID:" + id));
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }

    @Override
    public boolean checkUnique(Integer id, String ten) {
        KhuyenMai khuyenMaiTheoTen = repo.findByTenKhuyenMai(ten);
        if(khuyenMaiTheoTen == null) return true;
        boolean isCreatingNew = (id == null);
        if(isCreatingNew){
            if(khuyenMaiTheoTen != null){
                return false;
            }
        }else{
            if(khuyenMaiTheoTen.getIdKhuyenMai() != id){
                return false;
            }
        }
        return true;
    }
}

