package com.datn.dongho5s.categories;//package com.datn.dongho5s.categories;
//
//import com.datn.dongho5s.Entity.DanhMuc;
//import com.datn.dongho5s.Repository.DanhMucRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//
//@DataJpaTest(showSql = false)
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//@Rollback(false)
//public class DanhMucRepositoryTests {
//    @Autowired
//    private DanhMucRepository repo;
//
//    @Test
//    public void testFindByTen(){
//        String ten = "Đồng Hồ Cơ";
//        DanhMuc danhMuc = repo.findByTen(ten);
//        assertThat(danhMuc).isNotNull();
//        assertThat(danhMuc.getTen()).isEqualTo(ten);
//    }
//
//}
