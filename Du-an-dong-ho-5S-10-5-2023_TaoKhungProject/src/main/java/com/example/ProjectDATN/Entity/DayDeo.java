package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

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

    @Column(name = "TrangThai")
    private Integer trangThai;

}
