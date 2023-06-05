package com.dongho5s.admin.user;


import com.dongho5s.admin.Entity.ChucVu;
import com.dongho5s.admin.Repository.ChucVuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ChucVuRepositoryTests {

    @Autowired
    private ChucVuRepository repo;

    @Test
    public void testCreateFirstChucVu(){
        ChucVu chucVuAdmin = new ChucVu("Admin","Quản lý mọi thứ");
        ChucVu savedChucVu = repo.save(chucVuAdmin);
        assertThat(savedChucVu.getIdChucVu()).isGreaterThan(0);
    }

    @Test
    public void testCreateSecondChucVu(){
        ChucVu chucVustaff = new ChucVu("Staff","Quản lý bán hàng, khách hàng, báo cáo doanh thu, xem sản phẩm");
        ChucVu savedChucVu = repo.save(chucVustaff);
        assertThat(savedChucVu.getIdChucVu()).isGreaterThan(0);
    }
}
