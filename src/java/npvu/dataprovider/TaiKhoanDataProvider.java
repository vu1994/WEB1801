/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.dataprovider;

import npvu.model.TaiKhoanModel;
import npvu.util.HibernateUtil;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author thyen
 */
public class TaiKhoanDataProvider implements Serializable {  
    private static final Logger log = LoggerFactory.getLogger(TaiKhoanDataProvider.class);
    
    public TaiKhoanModel getTaiKhoanByTenTaiKhoan(String tenTaiKhoan){
        Session session = HibernateUtil.currentSession();
        TaiKhoanModel objTaiKhoanModel = null;
        try {
            session.beginTransaction();
            objTaiKhoanModel = (TaiKhoanModel) session.createSQLQuery("SELECT *"
                    + " FROM taikhoan tk"
                    + " WHERE tk.tk_tentaikhoan = '"+tenTaiKhoan+"'")
                    .addEntity(TaiKhoanModel.class).uniqueResult();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get tài khoản <<" + tenTaiKhoan + ">> {}", e);
	} finally {
            session.close();
	}
        return objTaiKhoanModel;
    }
    
    public List<TaiKhoanModel> getDanhSachTaiKhoan(){
        Session session = HibernateUtil.currentSession();
        List<TaiKhoanModel> dsTaiKhoan = new ArrayList();
        try {
            session.beginTransaction();
            dsTaiKhoan = session.createSQLQuery("SELECT *"
                    + " FROM taikhoan tk"
                    + " LEFT JOIN quyen_taikhoan qtk"
                    + " ON qtk.tk_id = tk.tk_id").addEntity(TaiKhoanModel.class).list();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get danh sách tài khoản {}", e);
	} finally {
            session.close();
	}
        return dsTaiKhoan;
    }
    
    public List<TaiKhoanModel> getDanhSachTaiKhoanByQuyen(int quyenID){
        Session session = HibernateUtil.currentSession();
        List<TaiKhoanModel> dsTaiKhoan = new ArrayList();
        try {
            session.beginTransaction();
            dsTaiKhoan = session.createSQLQuery("SELECT *"
                    + " FROM taikhoan tk"
                    + " LEFT JOIN quyen_taikhoan qtk"
                    + " ON qtk.tk_id = tk.tk_id "
                    + " WHERE qtk.quyen_id = "+quyenID).addEntity(TaiKhoanModel.class).list();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get danh sách tài khoản theo quyền <<" + quyenID + ">> {}", e);
	} finally {
            session.close();
	}
        return dsTaiKhoan;
    }

    
    public boolean updateTaiKhoan(TaiKhoanModel objTaiKhoan){
        Session session = HibernateUtil.currentSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(objTaiKhoan);
            session.getTransaction().commit();
	} catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Lỗi update tài khoản <<" + objTaiKhoan.getTenTaiKhoan() + ">> {}", e);
            return false;
	} finally {
            session.close();
	}
        return true;
    }
    
     public boolean delTaiKhoan(TaiKhoanModel objTaiKhoan){
        Session session = HibernateUtil.currentSession();
        try {
            session.beginTransaction();
            session.delete(objTaiKhoan);
            session.createSQLQuery("DELETE FROM quyen_taikhoan WHERE tk_id = "+objTaiKhoan.getId()).executeUpdate();
            session.getTransaction().commit();
	} catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Lỗi delete tài khoản <<" + objTaiKhoan.getTenTaiKhoan() + ">> {}", e);
            return false;
	} finally {
            session.close();
	}
        return true;
    }
}
