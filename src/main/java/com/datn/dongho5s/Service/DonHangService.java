package com.datn.dongho5s.Service;

import com.datn.dongho5s.Entity.DonHang;

public interface DonHangService {
    DonHang save (DonHang donHang);
    DonHang getById (Integer idDonHang);
}
