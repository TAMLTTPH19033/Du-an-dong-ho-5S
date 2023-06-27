package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DiaChi;
import com.datn.dongho5s.Request.DiaChiRequest;

import java.util.List;

public interface DiaChiService {
    public DiaChi createDiaChi(DiaChiRequest diaChiRequest);
    public List<DiaChi> getAllDiaChi();
}
