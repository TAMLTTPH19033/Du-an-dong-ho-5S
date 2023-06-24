package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.VatLieu;
import com.datn.dongho5s.Exception.MaterialNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VatLieuService{
    public static final int MATERIALS_PER_PAGE = 4;
    List<VatLieu> listAll();
    public VatLieu save(VatLieu vl);
    public VatLieu get(Integer id) throws MaterialNotFoundException;
    public void updateVatLieuEnabledStatus(Integer id, boolean enabled);
    public Page<VatLieu> listByPage(int pageNumber, String sortField, String sortDir, String keyword);
}
