package com.datn.dongho5s.user;

import com.datn.dongho5s.Entity.ChucVu;
import com.datn.dongho5s.Entity.NhanVien;
import com.datn.dongho5s.Repository.NhanVienRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class NhanVienRepositoryTests {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private NhanVienRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUser(){
        ChucVu chucVuAdmin =  entityManager.find(ChucVu.class,1);
        passwordEncoder.encode("annt123");
        NhanVien userAnntaa = new NhanVien("Nguyen","An","an25051997@gmail.com",passwordEncoder.encode("annt123"));
        userAnntaa.addChucVu(chucVuAdmin);

        NhanVien savedNhanVien = repo.save(userAnntaa);
        assertThat(savedNhanVien.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUser(){
        ChucVu chucVuAdmin =  entityManager.find(ChucVu.class,2);
        passwordEncoder.encode("annt123");
        NhanVien userHuonggg = new NhanVien("Nguyen","Huong","huong24@gmail.com",passwordEncoder.encode("annt123"));
        userHuonggg.addChucVu(chucVuAdmin);

        NhanVien savedNhanVien = repo.save(userHuonggg);
        assertThat(savedNhanVien.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewTwoUser(){
        ChucVu chucVuAdmin =  entityManager.find(ChucVu.class,2);
        NhanVien userNgoc = new NhanVien("Nguyen","vungoc","ngocvnph18825@gmail.com","ngocvn123");
        userNgoc.addChucVu(chucVuAdmin);

        NhanVien savedNhanVien = repo.save(userNgoc);
        assertThat(savedNhanVien.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllNhanVien(){
        Iterable<NhanVien> listNV =  repo.findAll();
        listNV.forEach(nhanVien -> System.out.println(nhanVien));
    }

    @Test
    public void testGetUserById(){
        NhanVien nhanVienAn = repo.findById(1).get();
        System.out.println(nhanVienAn);
        assertThat(nhanVienAn).isNotNull();
    }

    @Test
    public void testUpdateNhanVien(){
        NhanVien nhanVienAn = repo.findById(1).get();
        nhanVienAn.setEnabled(true);
        nhanVienAn.setEmail("anntph18823@gmail.com");

        repo.save(nhanVienAn);
    }

    @Test
    public void testDeleteNhanVien(){
        Integer nhanVienId = 3;
        repo.deleteById(nhanVienId);

    }

    @Test
    public void testGetNhanVienByEmail(){
        String email = "anntph18823@gmail.com";
        NhanVien nhanVien = repo.getNhanVienByEmail(email);
        assertThat(nhanVien).isNotNull();
    }

    @Test
    public void TestCountById(){
        Integer id = 24;
        Long countById = repo.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableNhanVien(){
        Integer id = 1;
        repo.updateEnabledStatus(id, false);

    }

    @Test
    public void testEnableNhanVien(){
        Integer id = 1;
        repo.updateEnabledStatus(id, true);

    }

    @Test
    public void testListFirstPage(){
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<NhanVien> page =  repo.findAll(pageable);

        List<NhanVien> listNhanVien = page.getContent();
        listNhanVien.forEach(nhanVien -> System.out.println(nhanVien));

        assertThat(listNhanVien.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchNhanVien(){
        String keyword = "Nguyễn";

        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<NhanVien> page =  repo.findAll(keyword, pageable);

        List<NhanVien> listNhanVien = page.getContent();
        listNhanVien.forEach(nhanVien -> System.out.println(nhanVien));

        assertThat(listNhanVien.size()).isGreaterThan(0);
    }
}
