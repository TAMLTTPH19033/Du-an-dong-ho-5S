package com.datn.dongho5s.Service;

import com.datn.dongho5s.Request.TimKiemRequest;
import com.datn.dongho5s.Response.TimKiemResponse;

import java.util.List;

public interface SanPhamService {
    List<TimKiemResponse> getSanPhamByCondition(TimKiemRequest timKiemRequest);
}
