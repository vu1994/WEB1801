/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import npvu.dataprovider.RoleDataProvider;
import npvu.dataprovider.TaiKhoanDataProvider;
import npvu.model.TaiKhoanModel;
import npvu.util.EncryptionUtils;
import npvu.util.ShowGrowlUtils;

/**
 *
 * @author npvu
 */
@ManagedBean (name="Login")
@SessionScoped
public class Login implements Serializable{
    
    ShowGrowlUtils showGrow = new ShowGrowlUtils();
    
    public static boolean logined;
    public static long   taiKhoanID;
    public static String tenDangNhap;
    public static String tenHienThi;
    public static Date thoiGian;
    public static String[] roles;
    
    private String tempTaiKhoan;
    private String tempMatKhau;
    
    private String urlCurrent;
    
    public Login() {
        
    }
    
    public void preLogin(){
        try {
            // Lấy full url hiện tại
            
            // -- làm sau
            
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/dang-nhap/");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void login(){
        // Xử lý tên tài khoản
        
        // Lấy tài khoản từ database
        TaiKhoanDataProvider tkProvider = new TaiKhoanDataProvider();
        RoleDataProvider roleProvider = new RoleDataProvider();
        TaiKhoanModel objTaiKhoan = tkProvider.getTaiKhoanByTenDangNhap(tempTaiKhoan);
        
        if(objTaiKhoan != null){
            if(objTaiKhoan.getMatKhau().equals(EncryptionUtils.encryptMatKhau(tempMatKhau))){
                logined         = true;
                taiKhoanID      = objTaiKhoan.getId();
                tenDangNhap     = objTaiKhoan.getTenDangNhap();
                tenHienThi      = objTaiKhoan.getTenHienThi();
                thoiGian        = Calendar.getInstance().getTime();
                roles           = roleProvider.getDanhSachRoleByTaiKhoan(objTaiKhoan.getId());
                tempTaiKhoan    = null;
                showGrow.showMessageSuccess("Đăng nhập thành công !.");
                try {
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect(ec.getRequestContextPath() + "/");
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
            } else {
                // Sai mật khẩu
                showGrow.showMessageError("Mật khẩu không đúng, vui lòng kiểm tra lại thông tin !.");
            }
        } else {
            // Tài khoản không tồn tại
            showGrow.showMessageError("Tên đăng nhập không tồn tại, vui lòng kiểm tra lại thông tin !.");
        }
        tempMatKhau = null;
    }
    
    public void logout(){
        logined = false;
        tenDangNhap = null;
        tenHienThi = null;
        thoiGian = null;
        tempMatKhau = null;
        try {            
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isLogined() {
        return logined;
    }

    public void setLogined(boolean logined) {
        this.logined = logined;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    public void setTenHienThi(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTempTaiKhoan() {
        return tempTaiKhoan;
    }

    public void setTempTaiKhoan(String tempTaiKhoan) {
        this.tempTaiKhoan = tempTaiKhoan;
    }

    public String getTempMatKhau() {
        return tempMatKhau;
    }

    public void setTempMatKhau(String tempMatKhau) {
        this.tempMatKhau = tempMatKhau;
    }

    public long getTaiKhoanID() {
        return taiKhoanID;
    }

    public void setTaiKhoanID(long taiKhoanID) {
        this.taiKhoanID = taiKhoanID;
    }

}
