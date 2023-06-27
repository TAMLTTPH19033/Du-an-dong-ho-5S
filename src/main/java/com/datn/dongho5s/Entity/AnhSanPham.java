package com.datn.dongho5s.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "anhsanpham")
public class AnhSanPham {
    @Id
    @Column(name = "id_anh_san_pham")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnhSanPham;

    @Column(name = "link")
    private String link;

    @Column(name = "ten_anh")
    private String tenAnh;

    @ManyToOne
    @JoinColumn(name = "id_san_pham")
    private SanPham sanPham;

}
