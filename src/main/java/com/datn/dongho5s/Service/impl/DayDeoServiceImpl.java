package com.datn.dongho5s.Service.impl;


import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Repository.DayDeoRepository;
import com.datn.dongho5s.Service.DayDeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayDeoServiceImpl implements DayDeoService {

    @Autowired
    DayDeoRepository daoDeoRepository;
    @Override
    public List<DayDeo> getAllDayDeo() {
        return daoDeoRepository.findAll();
    }
}
