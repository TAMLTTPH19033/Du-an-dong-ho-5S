package com.datn.dongho5s.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "daydeo")
public class DayDeo {
    @Id
    @Column(name = "IdDayDeo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDayDeo;

    @Column(name = "TenDayDeo")
    private String tenDayDeo;

    @Column(name = "MoTaDayDeo")
    private String moTaDayDeo;

    @Column(name = "ChieuDai")
    private Float chieuDai;

    @Column(name = "ChatLieu")
    private String chatLieu;

}
