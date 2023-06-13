package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.MauSac;
import com.datn.dongho5s.Repository.MauSacRepository;
import com.datn.dongho5s.Service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacServiceImpl implements MauSacService {
    @Autowired
    MauSacRepository mauSacRepository;
    @Override
    public List<MauSac> getAllMauSac() {
        return mauSacRepository.findAll();
    }
}
