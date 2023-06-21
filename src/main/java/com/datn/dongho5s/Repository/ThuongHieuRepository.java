package com.datn.dongho5s.Repository;


import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu,Integer> {
    @Query("UPDATE ThuongHieu th SET th.enabled = ?2 WHERE th.idThuongHieu = ?1")
    @Modifying
    void updateEnabledStatus(Integer id, boolean enabled);

    @Query("SELECT th FROM ThuongHieu th WHERE UPPER(CONCAT(th.idThuongHieu, ' ', th.ten, ' ', th.moTaThuongHieu)) LIKE %?1%")
    public Page<ThuongHieu> findAll(String keyword, Pageable pageable);

    public ThuongHieu findByTen(String ten);


}
