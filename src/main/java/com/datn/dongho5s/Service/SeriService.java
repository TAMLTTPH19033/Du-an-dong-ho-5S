package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Entity.Seri;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SeriService {
    Seri save(Seri seri);
    List<Seri> saveMany(List<Seri> seri);
    Seri get(Integer id);
    Page<Seri> searchSeri(int pageNumber,int pageSize, String keyword);
}
