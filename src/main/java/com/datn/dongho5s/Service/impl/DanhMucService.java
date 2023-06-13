package com.datn.dongho5s.Service.impl;

import com.datn.dongho5s.Entity.DanhMuc;
import com.datn.dongho5s.Exception.CustomException.DanhMucNotFoundException;
import com.datn.dongho5s.Repository.DanhMucRepository;
import com.datn.dongho5s.Service.DanhmucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DanhMucService implements DanhmucService {
    @Autowired
    private DanhMucRepository repo;

    public List<DanhMuc> listAll(){

      List<DanhMuc> rootDanhMuc =  repo.findRootDanhMuc();
      return listPhanCapDanhMuc(rootDanhMuc);
    }

    private List<DanhMuc> listPhanCapDanhMuc(List<DanhMuc> rootDanhMuc){
        List<DanhMuc> phanCapDanhMuc = new ArrayList<>();
        for (DanhMuc rootMuc : rootDanhMuc) {
            phanCapDanhMuc.add(DanhMuc.copyFull(rootMuc));

            Set<DanhMuc> danhMucCon = rootMuc.getCon();
            for (DanhMuc mucCon : danhMucCon){
                String ten = "--" + mucCon.getTen();
                phanCapDanhMuc.add(DanhMuc.copyFull(mucCon,ten));
                listPhanCapChauDanhMuc(phanCapDanhMuc,mucCon,1);
            }
        }
        return phanCapDanhMuc;
    }

    private void listPhanCapChauDanhMuc(List<DanhMuc> phanCapDanhMuc,
                                        DanhMuc con, int conLevel){
        Set<DanhMuc> mucChau =con.getCon();
        int newChauLevel = conLevel +1;
        for (DanhMuc chauDanhMuc : mucChau ){
            String name ="";
            for (int i =0; i< newChauLevel; i++ ){
                name += "--";
            }
            name += chauDanhMuc.getTen();
            phanCapDanhMuc.add(DanhMuc.copyFull(chauDanhMuc,name));
            listPhanCapChauDanhMuc(phanCapDanhMuc,chauDanhMuc,newChauLevel);
        }

    }

    public DanhMuc save(DanhMuc danhMuc){
        return repo.save(danhMuc);
    }

    public List<DanhMuc> listDanhMucUsedInForm(){
        List<DanhMuc> DanhMucUsedInForm = new ArrayList<>();
        Iterable<DanhMuc> danhMucInDB =  repo.findAll();

        for (DanhMuc loaiDanhMuc : danhMucInDB){
            if(loaiDanhMuc.getCha()==null){
                DanhMucUsedInForm.add(DanhMuc.copyIdVaTen(loaiDanhMuc));

                Set<DanhMuc> danhMucCon = loaiDanhMuc.getCon();

                for(DanhMuc mucCon : danhMucCon){
                    String ten = "--" + mucCon.getTen();
                    DanhMucUsedInForm.add(DanhMuc.copyIdVaTen(mucCon.getId(),ten));
                    listChauDanhMucUsedInForm(DanhMucUsedInForm,mucCon,1);
                }
            }
        }

        return DanhMucUsedInForm;
    }

    private void listChauDanhMucUsedInForm(List<DanhMuc> DanhMucUsedInForm,
                                           DanhMuc danhMucCon, int subLevel){
        int newSubLevel = subLevel +1;
        Set<DanhMuc> mucChau = danhMucCon.getCon();
        for(DanhMuc danhMucChau : mucChau){
            String ten = "";
            for (int i = 0; i < newSubLevel; i++) {
                ten += "--";
            }
            ten += danhMucChau.getTen();
            DanhMucUsedInForm.add(DanhMuc.copyIdVaTen(danhMucChau.getId(),ten));

            listChauDanhMucUsedInForm(DanhMucUsedInForm ,danhMucChau,newSubLevel);
        }
    }

    public DanhMuc get(Integer id) throws DanhMucNotFoundException {
        try{
            return repo.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new DanhMucNotFoundException("Không tìm thấy danh mục với id : " + id);
        }
    }

//    public void updateDanhMucEnabledStatus(Integer id, boolean enabled) {
//        repo.updateEnabledStatus(id, enabled);
//    }

}
