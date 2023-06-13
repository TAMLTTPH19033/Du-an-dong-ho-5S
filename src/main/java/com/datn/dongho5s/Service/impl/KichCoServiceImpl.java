package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.KichCo;
import com.datn.dongho5s.Repository.KichCoRepository;
import com.datn.dongho5s.Service.KichCoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KichCoServiceImpl implements KichCoService {
    @Autowired
    KichCoRepository kichCoRepository;
    @Override
    public List<KichCo> getAllKichCo() {
        return kichCoRepository.findAll();
    }
}
