package com.example.ProjectDATN.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pin")
public class Pin {
    @Id
    @Column(name = "IdPin")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPin;

    @Column(name = "TenPin")
    private String tenPin;

    @Column(name = "DungLuong")
    private Integer dungLuong;

    @Column(name = "ThoiGianSuDung")
    private Integer thoiGianSuDung;

}
