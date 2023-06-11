package com.datn.dongho5s.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "danhmuc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String ten;

    @Column(length = 128, nullable = false)
    private String anh;

    private boolean enabled;

    @Column(length = 64, nullable = false, unique = true)
    private String biDanh;

    @OneToOne
    @JoinColumn(name = "cha_id")
    @JsonIgnore
    private DanhMuc cha;


    @OneToMany(mappedBy = "cha")
    @JsonIgnore
    private Set<DanhMuc> con = new HashSet<>();


    public DanhMuc() {
    }

    public DanhMuc(Integer id) {
        this.id = id;
    }

    public static DanhMuc  copyIdVaTen(DanhMuc danhMuc) {
        DanhMuc copyDanhMuc = new DanhMuc();
        copyDanhMuc.setId(danhMuc.getId());
        copyDanhMuc.setTen(danhMuc.getTen());
        return copyDanhMuc;
    }

    public static DanhMuc  copyIdVaTen(Integer id, String ten) {
        DanhMuc copyDanhMuc = new DanhMuc();
        copyDanhMuc.setId(id);
        copyDanhMuc.setTen(ten);
        return copyDanhMuc;
    }

    public static DanhMuc copyFull(DanhMuc danhMuc){
        DanhMuc copyDanhMuc = new DanhMuc();
        copyDanhMuc.setId(danhMuc.getId());
        copyDanhMuc.setTen(danhMuc.getTen());
        copyDanhMuc.setAnh(danhMuc.getAnh());
        copyDanhMuc.setBiDanh(danhMuc.getBiDanh());
        copyDanhMuc.setEnabled(danhMuc.isEnabled());
        return copyDanhMuc;
    }

    public static DanhMuc copyFull(DanhMuc danhMuc, String ten){
        DanhMuc copyDanhMuc = DanhMuc.copyFull(danhMuc);
        copyDanhMuc.setTen(ten);

        return copyDanhMuc;
    }

    public DanhMuc(String ten) {
        this.ten = ten;
        this.biDanh = ten;
        this.anh = "default.png";
    }

    public DanhMuc(String ten, DanhMuc cha) {
        this(ten);
        this.cha = cha;
    }

//     Các getter và setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBiDanh() {
        return biDanh;
    }

    public void setBiDanh(String biDanh) {
        this.biDanh = biDanh;
    }

    public DanhMuc getCha() {
        return cha;
    }

    public void setCha(DanhMuc cha) {
        this.cha = cha;
    }

    public Set<DanhMuc> getCon() {
        return con;
    }

    public void setCon(Set<DanhMuc> con) {
        this.con = con;
    }

    @Transient
    public String getImagePath(){
        if (this.id == null) return "/images/image-thumbnail.png";
        return "/category-images/" + this.id + "/" + this.anh;
    }
}
