/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import npvu.config.Constant;
import npvu.dataprovider.RoleDataProvider;
import npvu.dataprovider.TaiKhoanDataProvider;
import npvu.model.TaiKhoanModel;
import npvu.util.DateUtils;
import npvu.util.EncryptionUtils;
import npvu.util.RoleUtils;
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
    
    private UIComponent uicTenDangNhap;
    
    private UIComponent uicMatKhau;
    
    private UIComponent uicReMatKhau;
    
    private final TaiKhoanDataProvider tkProvider = new TaiKhoanDataProvider();
    
    private final RoleDataProvider roleProvider = new RoleDataProvider();
    
    private final ShowGrowlUtils showGrowl = new ShowGrowlUtils();
    
    private final RoleUtils roleUtils = new RoleUtils();
    
    private List<TaiKhoanModel> dsTaiKhoan = new ArrayList<>();

    private List<Map> dsRole               = new ArrayList<>();
    
    private TaiKhoanModel objTaiKhoan;
    
    private int selectedRole;
    
    private int viewMode;                                             // 0 là form danh sách, 1 là form cập nhật
    
    private int tabIndex = 0;
    
    private String passTemp;
    
    private String[] selectRoles;                                     // Biến dùng để lưu role khi cấp quyền
    
    private boolean editMode;                                         // True là chỉnh sửa tài khoản, False là thêm mới tài khoản
    
    /**
     * Creates a new instance of TaiKhoanController
     */
    public TaiKhoanController() {
        if(roleUtils.checkRole(Constant.ROLE_ADMIN_TAIKHOAN)){
            actionGetDanhSachTaiKhoan();
            viewMode = 0;
        } else {
            try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + Constant.URL_ERROR_401);                
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(RoleUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    public void preActionTaoTaiKhoan(){
        log.info("***** Khởi tạo tham số cho tài khoản mới <preActionTaoTaiKhoan> *****");
        resetValue();
        editMode = false;
        viewMode = 1;
    }
    
    public void preActionEditTaiKhoan(TaiKhoanModel tkModel){
        objTaiKhoan = tkModel; 
        selectRoles = roleProvider.getDanhSachRoleByTaiKhoan(objTaiKhoan.getId());
        editMode = true;
        tabIndex = 0;
        viewMode = 1;
    }
    
    public List<Map> actionGetDanhSachRole(){
        return roleProvider.getDanhSachRole();
    }
    
    public void actionUpdateTaiKhoan(){
        log.info("***** Tạo tài khoản mới <actionUpdateTaiKhoan> *****");
        objTaiKhoan.setNgayTao(DateUtils.getCurrentDate());
        if ( editMode || actionVaildFormTaoTaiKhoan()) {
            if(!editMode){
                objTaiKhoan.setMatKhau(EncryptionUtils.encryptMatKhau(objTaiKhoan.getMatKhau()));
            }            
            if (tkProvider.updateTaiKhoan(objTaiKhoan, true, selectRoles)) {
                showGrowl.showMessageSuccess("Cập nhật tài khoản thành công !");
            } else {
                showGrowl.showMessageFatal("Cập nhật tài khoản thất bại, Vui lòng thử lại !");
            }
        } else {
            return;
        }    
        resetValue();
        actionGetDanhSachTaiKhoan();
        viewMode = 0;        
    }
    
    public boolean actionVaildFormTaoTaiKhoan(){
        boolean vaild = true;    
        
        /* Bắt đầu kiểm tra tên đăng nhập */
        if(objTaiKhoan.getTenDangNhap().length() < Constant.MIN_TENDANGNHAP
                || objTaiKhoan.getTenDangNhap().length() > Constant.MAX_TENDANGNHAP){
            showGrowl.showMessageError("Tên đăng nhập có độ dài từ "+Constant.MIN_TENDANGNHAP+""
                    + " đến "+Constant.MAX_TENDANGNHAP+" ký tự !", uicTenDangNhap);
            vaild = false;
        }
        if(tkProvider.checkExistTenDangNhap(objTaiKhoan.getTenDangNhap())){
            showGrowl.showMessageError("Tên đăng nhập đã tồn tại !", uicTenDangNhap);
            vaild = false;
        }
        /* Kết thúc kiểm tra tên đăng nhập */
        
        /* Bắt đầu kiểm tra mật khẩu */
        if(objTaiKhoan.getMatKhau().length() < Constant.MIN_MATKHAU
                || objTaiKhoan.getMatKhau().length() > Constant.MAX_MATKHAU){
            showGrowl.showMessageError("Tên đăng nhập có độ dài từ "+Constant.MIN_MATKHAU+""
                    + " đến "+Constant.MAX_MATKHAU+" ký tự !", uicMatKhau);
            vaild = false;
        }
        if(passTemp.length() < Constant.MIN_MATKHAU
                || passTemp.length() > Constant.MAX_MATKHAU){
            showGrowl.showMessageError("Tên đăng nhập có độ dài từ "+Constant.MIN_MATKHAU+""
                    + " đến "+Constant.MAX_MATKHAU+" ký tự !", uicReMatKhau);
            vaild = false;
        }        
        if(!objTaiKhoan.getMatKhau().equals(passTemp)){
            showGrowl.showMessageError("Mật khẩu không khớp !", uicMatKhau);
            showGrowl.showMessageError("Mật khẩu không khớp !", uicReMatKhau);
            vaild = false;
            objTaiKhoan.setMatKhau("");
            passTemp = "";            
        }
        /* Kết thúc kiểm tra mật khẩu */
        if(!vaild){
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
        tkProvider.updateTaiKhoan(objTaiKhoan, false, null);
    }
    
    public void actionDelTaiKhoan(TaiKhoanModel objTaiKhoan){
        
        tkProvider.updateTaiKhoan(objTaiKhoan, false, null);
    }
    
    public void resetValue(){
        objTaiKhoan     = null;
        objTaiKhoan     = new TaiKhoanModel();
        passTemp        = null;
        selectRoles     = null;
        selectedRole    = 0;
        tabIndex        = 0;
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

    public UIComponent getUicTenDangNhap() {
        return uicTenDangNhap;
    }

    public void setUicTenDangNhap(UIComponent uicTenDangNhap) {
        this.uicTenDangNhap = uicTenDangNhap;
    }

    public UIComponent getUicMatKhau() {
        return uicMatKhau;
    }

    public void setUicMatKhau(UIComponent uicMatKhau) {
        this.uicMatKhau = uicMatKhau;
    }

    public UIComponent getUicReMatKhau() {
        return uicReMatKhau;
    }

    public void setUicReMatKhau(UIComponent uicReMatKhau) {
        this.uicReMatKhau = uicReMatKhau;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    
    
    
}
