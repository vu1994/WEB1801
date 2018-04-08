/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import npvu.dataprovider.TaiKhoanDataProvider;
import npvu.model.TaiKhoanModel;
import npvu.util.DateUtils;
import npvu.util.ShowGrowlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author SunnyNguyen
 */
@ManagedBean
@ViewScoped
public class TaiKhoanController implements Serializable{
    private static final Logger log = LoggerFactory.getLogger(TaiKhoanController.class);
    
    private final TaiKhoanDataProvider tkProvider = new TaiKhoanDataProvider();
    
    private ShowGrowlUtils showGrowl = new ShowGrowlUtils();
    
    private List<TaiKhoanModel> dsTaiKhoan = new ArrayList<>();

    private TaiKhoanModel objTaiKhoan;
    
    private int selectedQuyen;
    
    private int viewMode;
    
    private String passTemp;
    /**
     * Creates a new instance of TaiKhoanController
     */
    public TaiKhoanController() {
        actionGetDanhSachTaiKhoan();
        viewMode = 0;
    }
    
    public void preActionTaoTaiKhoan(){
        log.info("***** Khởi tạo tham số cho tài khoản mới <preActionTaoTaiKhoan> *****");
        objTaiKhoan = new TaiKhoanModel();
        viewMode = 1;
    }
    
    public void actionUpdateTaiKhoan(){
        log.info("***** Tạo tài khoản mới <actionUpdateTaiKhoan> *****");
        objTaiKhoan.setNgayTao(DateUtils.getCurrentDate());
        if (actionVaildFormTaoTaiKhoan()) {
            if (tkProvider.updateTaiKhoan(objTaiKhoan)) {
                showGrowl.showMessageSuccess("Cập nhật tài khoản thành công !");
            } else {
                showGrowl.showMessageError("Cập nhật tài khoản thất bại, Vui lòng thử lại !");
            }
        }        
        actionGetDanhSachTaiKhoan();
        viewMode = 0;
    }
    
    public boolean actionVaildFormTaoTaiKhoan(){
        boolean vaild = true;
        if(!objTaiKhoan.getMatKhau().equals(passTemp)){
            
        }
        return vaild;
    }
    
    private void actionGetDanhSachTaiKhoan(){
        log.info("***** Lấy danh sách tài khoản <actionGetDanhSachTaiKhoan> *****");
        dsTaiKhoan.clear();
        dsTaiKhoan = tkProvider.getDanhSachTaiKhoan();
    }
    
    public void actionGetDanhSachTaiKhoanByQuyen(){
        log.info("***** Lấy danh sách tài khoản <actionGetDanhSachTaiKhoanByQuyen> *****");
        dsTaiKhoan.clear();
        dsTaiKhoan = tkProvider.getDanhSachTaiKhoanByQuyen(selectedQuyen);
    }
    
    public void selectTaiKhoan(TaiKhoanModel objTaiKhoan){
        this.objTaiKhoan = objTaiKhoan;
    }
    
    public void actionLockUnLockTaiKhoan(TaiKhoanModel objTaiKhoan){
        if(objTaiKhoan.isHoatdong()){
            objTaiKhoan.setNgayKhoa(DateUtils.getCurrentDate());
        } else {
            objTaiKhoan.setNgayMoKhoa(DateUtils.getCurrentDate());
        }
        objTaiKhoan.setHoatdong(!objTaiKhoan.isHoatdong());        
        tkProvider.updateTaiKhoan(objTaiKhoan);
    }
    
    public void actionDelTaiKhoan(TaiKhoanModel objTaiKhoan){
        
        tkProvider.updateTaiKhoan(objTaiKhoan);
    }
    
    public void actionChangeViewMode(int mode){
        viewMode = mode;
    }
    
    // Getter and Setter
    public List<TaiKhoanModel> getDsTaiKhoan() {
        return dsTaiKhoan;
    }

    public void setDsTaiKhoan(List<TaiKhoanModel> dsTaiKhoan) {
        this.dsTaiKhoan = dsTaiKhoan;
    }

    public int getSelectedQuyen() {
        return selectedQuyen;
    }

    public void setSelectedQuyen(int selectedQuyen) {
        this.selectedQuyen = selectedQuyen;
    }

    public int getViewMode() {
        return viewMode;
    }

    public void setViewMode(int viewMode) {
        this.viewMode = viewMode;
    }

    public TaiKhoanModel getObjTaiKhoan() {
        return objTaiKhoan;
    }

    public void setObjTaiKhoan(TaiKhoanModel objTaiKhoan) {
        this.objTaiKhoan = objTaiKhoan;
    }

    public String getPassTemp() {
        return passTemp;
    }

    public void setPassTemp(String passTemp) {
        this.passTemp = passTemp;
    }
    
    
    
}
