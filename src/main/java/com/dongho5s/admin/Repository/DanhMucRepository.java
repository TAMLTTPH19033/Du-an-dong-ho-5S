package com.dongho5s.admin.Repository;

import com.dongho5s.admin.Entity.DanhMuc;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends PagingAndSortingRepository<DanhMuc,Integer> {
}
