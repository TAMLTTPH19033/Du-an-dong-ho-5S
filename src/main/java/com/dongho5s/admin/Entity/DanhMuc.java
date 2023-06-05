package com.dongho5s.admin.Entity;

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

    @Column(nullable = false)
    private boolean enabled;

    @Column(length = 128, nullable = false)
    private String biDanh;

    @ManyToOne
    @JoinColumn(name = "cha_id")
    private DanhMuc cha;


    @OneToMany(mappedBy = "cha")
    private Set<DanhMuc> con = new HashSet<>();


    public DanhMuc() {
    }

    public DanhMuc(Integer id) {
        this.id = id;
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
}
