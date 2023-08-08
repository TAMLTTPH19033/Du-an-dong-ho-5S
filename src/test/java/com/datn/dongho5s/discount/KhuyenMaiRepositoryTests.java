package com.datn.dongho5s.discount;

import com.datn.dongho5s.Entity.KhuyenMai;
import com.datn.dongho5s.Repository.KhuyenMaiRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.Date;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class KhuyenMaiRepositoryTests {
    @Autowired
    private KhuyenMaiRepository repo;

    @Test
    public void testCreateKhuyenMai(){
        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTenKhuyenMai("NgayThanhLap");
        khuyenMai.setMoTaKhuyenMai("Giảm giá đồng loạt 50%");
        khuyenMai.setNgayBatDau(new Date());
        khuyenMai.setNgayKetThuc(new Date());
        khuyenMai.setNgaySua(new Date());
        khuyenMai.setEnabled(true);

        KhuyenMai savedKhuyenMai = repo.save(khuyenMai);
        assertThat(savedKhuyenMai).isNotNull();
        assertThat(savedKhuyenMai.getIdKhuyenMai()).isGreaterThan(0);
    }

    @Test
    public void testCreateKhuyenMai1(){
        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTenKhuyenMai("Ngay sinh nhật");
        khuyenMai.setMoTaKhuyenMai("Giảm giá đồng loạt 25%");
        khuyenMai.setNgayBatDau(new Date());
        khuyenMai.setNgayKetThuc(new Date());
        khuyenMai.setNgaySua(new Date());
        khuyenMai.setEnabled(true);

        KhuyenMai savedKhuyenMai = repo.save(khuyenMai);
        assertThat(savedKhuyenMai).isNotNull();
        assertThat(savedKhuyenMai.getIdKhuyenMai()).isGreaterThan(0);
    }

    @Test
    public void testCreateKhuyenMai2(){
        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTenKhuyenMai("Ngày Tết Thiếu Nhi");
        khuyenMai.setMoTaKhuyenMai("Giảm giá đồng loạt 15%");
        khuyenMai.setNgayBatDau(new Date());
        khuyenMai.setNgayKetThuc(new Date());
        khuyenMai.setNgaySua(new Date());
        khuyenMai.setEnabled(true);

        KhuyenMai savedKhuyenMai = repo.save(khuyenMai);
        assertThat(savedKhuyenMai).isNotNull();
        assertThat(savedKhuyenMai.getIdKhuyenMai()).isGreaterThan(0);
    }

    @Test
    public void testCreateKhuyenMai3(){
        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTenKhuyenMai("Ngày Tết Nguyên Đán");
        khuyenMai.setMoTaKhuyenMai("Giảm giá đồng loạt 60%");
        khuyenMai.setNgayBatDau(new Date());
        khuyenMai.setNgayKetThuc(new Date());
        khuyenMai.setNgaySua(new Date());
        khuyenMai.setEnabled(true);

        KhuyenMai savedKhuyenMai = repo.save(khuyenMai);
        assertThat(savedKhuyenMai).isNotNull();
        assertThat(savedKhuyenMai.getIdKhuyenMai()).isGreaterThan(0);
    }

    @Test
    public void testCreateKhuyenMai4(){
        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setTenKhuyenMai("Ngày Thành Niên");
        khuyenMai.setMoTaKhuyenMai("Giảm giá đồng loạt 10%");
        khuyenMai.setNgayBatDau(new Date());
        khuyenMai.setNgayKetThuc(new Date());
        khuyenMai.setNgaySua(new Date());
        khuyenMai.setEnabled(true);

        KhuyenMai savedKhuyenMai = repo.save(khuyenMai);
        assertThat(savedKhuyenMai).isNotNull();
        assertThat(savedKhuyenMai.getIdKhuyenMai()).isGreaterThan(0);
    }
}
