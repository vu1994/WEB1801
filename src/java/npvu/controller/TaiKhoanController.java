/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import npvu.dataprovider.TaiKhoanDataProvider;
import npvu.model.TaiKhoanModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author SunnyNguyen
 */
@ManagedBean
@ViewScoped
public class TaiKhoanController {
    private static final Logger log = LoggerFactory.getLogger(TaiKhoanController.class);
    
    private final TaiKhoanDataProvider tkProvider = new TaiKhoanDataProvider();
    
    private List<TaiKhoanModel> dsTaiKhoan = new ArrayList<>();
    
    private int selectedQuyen;
    
    private int viewMode;

    /**
     * Creates a new instance of TaiKhoanController
     */
    public TaiKhoanController() {
        actionGetDanhSachTaiKhoan();
        viewMode = 0;
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
    
    
    
}
