package com.dongho5s.admin.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "NhanVien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Ho", length = 45,nullable = false)
    private String ho;

    @Column(name = "Ten", length = 45,nullable = false)
    private String ten;

    @Column(name = "GioiTinh")
    private Integer gioiTinh;

    @Column(name = "NgaySinh")
    private String ngaySinh;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "SoDienThoai")
    private String soDienThoai;

    @Column(name = "Email",length = 128,nullable = false,unique = true)
    private String email;

    @Column(name = "MatKhau")
    private String matKhau;

    private boolean enabled;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "Anh", length = 64)
    private String anh;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nhanvien_chucvu",
            joinColumns = @JoinColumn(name="nhanvien_id"),
            inverseJoinColumns = @JoinColumn(name = "chucvu_id")
    )
    private Set<ChucVu> chucVu = new HashSet<>();

    public NhanVien() {
    }


    public NhanVien(String ho, String ten, String email, String matKhau) {
        this.ho = ho;
        this.ten = ten;
        this.email = email;
        this.matKhau = matKhau;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Set<ChucVu> getChucVu() {
        return chucVu;
    }

    public void setChucVu(Set<ChucVu> chucVu) {
        this.chucVu = chucVu;
    }

    public void addChucVu(ChucVu chucVu){
        this.chucVu.add(chucVu);
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "id=" + id +
                ", ho='" + ho + '\'' +
                ", ten='" + ten + '\'' +
                ", email='" + email + '\'' +
                ", chucVu=" + chucVu +
                '}';
    }

    @Transient
    public String getPhotoImagesPath(){
        if(id == null || anh == null) return "/images/default-user.png";

        return "/user-photos/" + this.id + "/" + this.anh;
    }
}
