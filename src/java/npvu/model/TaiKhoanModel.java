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
import javax.persistence.Table;

/**
 *
 * @author SunnyNguyen
 */
@Entity
@Table(name = "taikhoan")
public class TaiKhoanModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "tk_id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "tk_tenhienthi")
    private String tenHienThi;
    
    @Column(name = "tk_tentaikhoan")
    private String tenTaiKhoan;
    
    @Column(name = "tk_matkhau")
    private String matKhau;
    
    @Column(name = "tk_ngaytao")
    private Date ngayTao;
    
    @Column(name = "tk_nguoitao")
    private String nguoiTao;
    
    
    // Khóa ngoại - các bảng liên quan
    @Column(name = "quyen_id")
    private List<Integer> quyenID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
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

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public List<Integer> getQuyenID() {
        return quyenID;
    }

    public void setQuyenID(List<Integer> quyenID) {
        this.quyenID = quyenID;
    }
    
   
}
