package com.datn.dongho5s.brands;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Repository.ThuongHieuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ThuongHieuRepositoryTests {
    @Autowired
    private ThuongHieuRepository repo;
//    @Test
//    public void testFindByTen(){
//        String ten = "Rolex";
//        ThuongHieu thuongHieu = repo.findByTenThuonghieu(ten);
//        assertThat(thuongHieu).isNotNull();
//        assertThat(thuongHieu.getTenThuonghieu()).isEqualTo(ten);
//    }

    @Test
    public void testListAllThuongHieu(){
        Iterable<ThuongHieu> listTH =  repo.findAll();
        listTH.forEach(thuongHieu -> System.out.println(thuongHieu));
    }

    @Test
    public void testGetThuongHieuById(){
        ThuongHieu thuongHieuRolex = repo.findById(1).get();
        System.out.println(thuongHieuRolex);
        assertThat(thuongHieuRolex).isNotNull();
    }

    @Test
    public void testUpdateThuongHieu(){
        ThuongHieu thuongHieuRolex = repo.findById(1).get();
        thuongHieuRolex.setEnabled(true);
        thuongHieuRolex.setMoTaThuongHieu("From Thuy Sy");

        repo.save(thuongHieuRolex);
    }
}
