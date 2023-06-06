package com.datn.dongho5s.Repository;

import com.datn.dongho5s.Entity.DanhMuc;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends PagingAndSortingRepository<DanhMuc,Integer> {
}
