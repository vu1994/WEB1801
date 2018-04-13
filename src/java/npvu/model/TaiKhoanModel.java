/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author SunnyNguyen
 */
@Entity
@Table(name = "taikhoan")
public class TaiKhoanModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taikhoan_taikhoan_id_seq")
    @SequenceGenerator(name = "taikhoan_taikhoan_id_seq", sequenceName = "taikhoan_taikhoan_id_seq", allocationSize = 1)
    @Column(name = "taikhoan_id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "taikhoan_tenhienthi")
    private String tenHienThi;
    
    @Column(name = "taikhoan_tendangnhap")
    private String tenDangNhap;
    
    @Column(name = "taikhoan_matkhau")
    private String matKhau;
    
    @Column(name = "taikhoan_ngaytao")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayTao;
    
    @Column(name = "taikhoan_nguoitao")
    private long nguoiTao;
    
    @Column(name = "taikhoan_hoatdong")
    private boolean hoatdong;
    
    @Column(name = "taikhoan_ngaykhoa")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayKhoa;
    
    @Column(name = "taikhoan_ngaymokhoa")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngayMoKhoa;
    
    @Column(name = "taikhoan_hoten")
    private String hoTen;
    
    @Column(name = "taikhoan_ngaysinh")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date ngaySinh;
    
    @Column(name = "taikhoan_gioitinh")
    private String gioiTinh;
    
    @Column(name = "taikhoan_diachi")
    private String diaChi;
    
    @Column(name = "taikhoan_sodienthoai")
    private String soDienThoai;
    
    @Column(name = "taikhoan_email")
    private String email;
    
    @Transient
    private List<Integer> roleID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public long getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(long nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public List<Integer> getRoleID() {
        return roleID;
    }

    public void setRoleID(List<Integer> roleID) {
        this.roleID = roleID;
    }

    public boolean isHoatdong() {
        return hoatdong;
    }

    public void setHoatdong(boolean hoatdong) {
        this.hoatdong = hoatdong;
    }

    public Date getNgayKhoa() {
        return ngayKhoa;
    }

    public void setNgayKhoa(Date ngayKhoa) {
        this.ngayKhoa = ngayKhoa;
    }

    public Date getNgayMoKhoa() {
        return ngayMoKhoa;
    }

    public void setNgayMoKhoa(Date ngayMoKhoa) {
        this.ngayMoKhoa = ngayMoKhoa;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
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

}
