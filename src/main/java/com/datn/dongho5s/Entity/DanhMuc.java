package com.datn.dongho5s.Entity;

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

    @Column(nullable = false)
    private boolean enabled;

    public DanhMuc() {
    }

    public DanhMuc(Integer id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public DanhMuc(Integer id, String ten, boolean enabled) {
        this.id = id;
        this.ten = ten;
        this.enabled = enabled;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
