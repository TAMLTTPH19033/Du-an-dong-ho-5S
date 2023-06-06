package com.datn.dongho5s.user;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Repository.DanhMucRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class DanhMucRepositoryTests {
    @Autowired
    private DanhMucRepository repo;

    @Test
    public void testCreateDanhMucNam(){
        DanhMuc danhMucNam = new DanhMuc("Đồng Hồ Nam");
        DanhMuc savedDanhMucNam = repo.save(danhMucNam);
        assertThat(savedDanhMucNam.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateDanhMucNu(){
        DanhMuc danhMucNu = new DanhMuc("Đồng Hồ Nữ");
        DanhMuc savedDanhMucNu = repo.save(danhMucNu);
        assertThat(savedDanhMucNu.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateDanhMucConMotCuaNam(){
        DanhMuc danhMucCha = new DanhMuc(1);
        DanhMuc danhMucCon = new DanhMuc("Đồng Hồ Cơ",danhMucCha);
        DanhMuc savedDanhMucCon = repo.save(danhMucCon);
        assertThat(savedDanhMucCon.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateDanhMucConMotCuaNu(){
        DanhMuc danhMucCha = new DanhMuc(2);
        DanhMuc danhMucCon = new DanhMuc("Đồng Hồ Cơ Nữ",danhMucCha);
        DanhMuc savedDanhMucCon = repo.save(danhMucCon);
        assertThat(savedDanhMucCon.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateDanhMucCacConCuaNam(){
        DanhMuc danhMucCha = new DanhMuc(1);
        DanhMuc danhMucConHai = new DanhMuc("Đồng hồ Điện Tử",danhMucCha);
        DanhMuc danhMucConBa = new DanhMuc("Đồng Hồ Thông Minh",danhMucCha);
         repo.saveAll(List.of(danhMucConHai,danhMucConBa));
    }

    @Test
    public void testCreateDanhMucCacConCuaNu(){
        DanhMuc danhMucCha = new DanhMuc(2);
        DanhMuc danhMucConHai = new DanhMuc("Đồng hồ Điện Tử Nữ",danhMucCha);
        DanhMuc danhMucConBa = new DanhMuc("Đồng Hồ Thông Minh Nữ",danhMucCha);
        repo.saveAll(List.of(danhMucConHai,danhMucConBa));
    }
    //COSMOGRAPH DAYTONA

    @Test
    public void testCreateDanhMucChauCuaNam(){
        DanhMuc danhMucCon = new DanhMuc(3);
        DanhMuc danhMucChau = new DanhMuc("COSMOGRAPH DAYTONA",danhMucCon);
        DanhMuc savedDanhMucChau = repo.save(danhMucChau);
        assertThat(savedDanhMucChau.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetDanhMucNam(){
        DanhMuc danhMuc = repo.findById(1).get();
        System.out.println(danhMuc.getTen());
        Set<DanhMuc> mucCon = danhMuc.getCon();
        for(DanhMuc danhMucCon : mucCon){
            System.out.println(danhMucCon.getTen());
        }
        assertThat(mucCon.size()).isGreaterThan(0);
    }

    @Test
    public void testGetDanhMucNu(){
        DanhMuc danhMuc = repo.findById(2).get();
        System.out.println(danhMuc.getTen());
        Set<DanhMuc> mucCon = danhMuc.getCon();
        for(DanhMuc danhMucCon : mucCon){
            System.out.println(danhMucCon.getTen());
        }
        assertThat(mucCon.size()).isGreaterThan(0);
    }

    @Test
    public void testPhanCapDanhMuc(){
        Iterable<DanhMuc> category =repo.findAll();
        for (DanhMuc loaiDanhMuc : category){
            if(loaiDanhMuc.getCha()==null){
                System.out.println(loaiDanhMuc.getTen());
            }

            Set<DanhMuc> danhMucCon = loaiDanhMuc.getCon();

            for(DanhMuc mucCon : danhMucCon){
                System.out.println("--" + mucCon.getTen());
                printDanhMucChau(mucCon,1);
            }
        }

    }

    private void printDanhMucChau(DanhMuc danhMucCon, int subLevel){
        int newSubLevel = subLevel +1;
        Set<DanhMuc> mucChau = danhMucCon.getCon();
        for(DanhMuc danhMucChau : mucChau){
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(danhMucChau.getTen());

            printDanhMucChau(danhMucChau,newSubLevel);
        }
    }

}
