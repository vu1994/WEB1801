/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import npvu.dataprovider.RoleDataProvider;
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
    
    private final RoleDataProvider roleProvider = new RoleDataProvider();
    
    private final ShowGrowlUtils showGrowl = new ShowGrowlUtils();
    
    private List<TaiKhoanModel> dsTaiKhoan = new ArrayList<>();

    private List<Map> dsRole               = new ArrayList<>();
    
    private TaiKhoanModel objTaiKhoan;
    
    private int selectedRole;
    
    private int viewMode;
    
    private int tabIndex = 0;
    
    private String passTemp;
    
    private String[] selectRoles;                                     // Biến dùng để lưu role khi cấp quyền
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
    
    public List<Map> actionGetDanhSachRole(){
        return roleProvider.getDanhSachRole();
    }
    
    public void actionUpdateTaiKhoan(){
        log.info("***** Tạo tài khoản mới <actionUpdateTaiKhoan> *****");
        objTaiKhoan.setNgayTao(DateUtils.getCurrentDate());
        if (actionVaildFormTaoTaiKhoan()) {
            if (tkProvider.updateTaiKhoan(objTaiKhoan)) {
                showGrowl.showMessageSuccess("Cập nhật tài khoản thành công !");
            } else {
                showGrowl.showMessageFatal("Cập nhật tài khoản thất bại, Vui lòng thử lại !");
            }
        } else {
            return;
        }
        passTemp = "";
        actionGetDanhSachTaiKhoan();
        viewMode = 0;
    }
    
    public boolean actionVaildFormTaoTaiKhoan(){
        boolean vaild = true;
        if(!objTaiKhoan.getMatKhau().equals(passTemp)){
            showGrowl.showMessageFatal("Mật khẩu không khớp !");
            vaild = false;
            objTaiKhoan.setMatKhau("");
            passTemp = "";
            tabIndex = 0;
        }
        return vaild;
    }
    
    private void actionGetDanhSachTaiKhoan(){
        log.info("***** Lấy danh sách tài khoản <actionGetDanhSachTaiKhoan> *****");
        dsTaiKhoan.clear();
        dsTaiKhoan = tkProvider.getDanhSachTaiKhoan();
    }
    
    public void actionGetDanhSachTaiKhoanByRole(){
        log.info("***** Lấy danh sách tài khoản <actionGetDanhSachTaiKhoanByRole> *****");
        dsTaiKhoan.clear();
        dsTaiKhoan = tkProvider.getDanhSachTaiKhoanByRole(selectedRole);
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

    public int getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(int selectedRole) {
        this.selectedRole = selectedRole;
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

    public List<Map> getDsRole() {
        return dsRole;
    }

    public void setDsRole(List<Map> dsRole) {
        this.dsRole = dsRole;
    }

    public String[] getSelectRoles() {
        return selectRoles;
    }

    public void setSelectRoles(String[] selectRoles) {
        this.selectRoles = selectRoles;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }
    
    
    
}
