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
 * @author npvu
 */
public class TaiKhoanDataProvider implements Serializable {  
    private static final Logger log = LoggerFactory.getLogger(TaiKhoanDataProvider.class);
    private static final int ROLE_DEFAULT = 100;
    
    public TaiKhoanModel getTaiKhoanByTenDangNhap(String tenDangNhap){
        Session session = HibernateUtil.currentSession();
        TaiKhoanModel objTaiKhoanModel = null;
        try {
            session.beginTransaction();
            objTaiKhoanModel = (TaiKhoanModel) session.createSQLQuery("SELECT *"
                    + " FROM taikhoan tk"
                    + " WHERE tk.taikhoan_tendangnhap = '"+tenDangNhap+"'")
                    .addEntity(TaiKhoanModel.class).uniqueResult();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get tài khoản <<" + tenDangNhap + ">> {}", e);
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
            dsTaiKhoan = session.createSQLQuery("SELECT * "
                    + " FROM taikhoan tk"
                    + " LEFT JOIN roles_taikhoan role"
                    + " ON role.taikhoan_id = tk.taikhoan_id ").addEntity(TaiKhoanModel.class).list();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get danh sách tài khoản {}", e);
	} finally {
            session.close();
	}
        return dsTaiKhoan;
    }
    
    public List<TaiKhoanModel> getDanhSachTaiKhoanByRole(int roleID){
        Session session = HibernateUtil.currentSession();
        List<TaiKhoanModel> dsTaiKhoan = new ArrayList();
        try {
            session.beginTransaction();
            dsTaiKhoan = session.createSQLQuery("SELECT *"
                    + " FROM taikhoan tk"
                    + " LEFT JOIN roles_taikhoan role"
                    + " ON role.taikhoan_id = tk.taikhoan_id "
                    + " WHERE qtk.role_id = "+roleID).addEntity(TaiKhoanModel.class).list();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get danh sách tài khoản theo quyền <<" + roleID + ">> {}", e);
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
            session.createSQLQuery("INSERT INTO roles_taikhoan(role_id, taikhoan_id)"
                    + "VALUES(:roleID,:taiKhoanID)")
                    .setInteger("roleID", ROLE_DEFAULT)
                    .setLong("taiKhoanID", objTaiKhoan.getId())
                    .executeUpdate();
            session.getTransaction().commit();
	} catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Lỗi update tài khoản <<" + objTaiKhoan.getTenDangNhap() + ">> {}", e);
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
            session.createSQLQuery("DELETE FROM roles_taikhoan WHERE taikhoan_id = "+objTaiKhoan.getId()).executeUpdate();
            session.getTransaction().commit();
	} catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Lỗi delete tài khoản <<" + objTaiKhoan.getTenDangNhap() + ">> {}", e);
            return false;
	} finally {
            session.close();
	}
        return true;
    }
}
