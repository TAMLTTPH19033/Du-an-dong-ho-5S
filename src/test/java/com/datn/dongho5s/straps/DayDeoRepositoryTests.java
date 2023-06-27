package com.datn.dongho5s.straps;

import com.datn.dongho5s.Entity.ChucVu;
import com.datn.dongho5s.Entity.DayDeo;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Entity.ThuongHieu;
import com.datn.dongho5s.Repository.DanhMucRepository;
import com.datn.dongho5s.Repository.DayDeoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class DayDeoRepositoryTests {
    @Autowired
    DayDeoRepository repo;


    @Test
    public void testCreateNewStraps(){
        DayDeo DayDeo1 = new DayDeo(1,"Day Da flix","Lam Tu Da Cao Cap",18.0f,"Da Ca Sau",true);
        DayDeo savedDayDeo = repo.save(DayDeo1);
        assertThat(savedDayDeo.getIdDayDeo()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewStrapsTwo(){
        DayDeo dayDeo2 = new DayDeo(2, "Day Kim Loại", "Làm từ thép không gỉ", 25.5f, "Thép không gỉ", true);
        DayDeo savedDayDeo = repo.save(dayDeo2);
        assertThat(savedDayDeo.getIdDayDeo()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewStrapsThree(){
        DayDeo dayDeo3 = new DayDeo(3, "Day Lụa Trơn", "Chất liệu lụa cao cấp", 20.3f, "Lụa", true);
        DayDeo savedDayDeo = repo.save(dayDeo3);
        assertThat(savedDayDeo.getIdDayDeo()).isGreaterThan(0);
    }

    @Test
    public void testFindByTen(){
        String ten = "Day Da flix";
        DayDeo dayDeo = repo.findByTenDayDeo(ten);
        assertThat(dayDeo).isNotNull();
        assertThat(dayDeo.getTenDayDeo()).isEqualTo(ten);
    }

    @Test
    public void testGetDayDeoById(){
        DayDeo dayDeo1 = repo.findById(1).get();
        System.out.println(dayDeo1);
        assertThat(dayDeo1).isNotNull();
    }
}
