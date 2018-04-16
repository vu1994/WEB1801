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
import npvu.config.Constant;
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
    
    public boolean checkExistTenDangNhap(String tenDangNhap){
        Session session = HibernateUtil.currentSession();
        boolean result = true;
        try {
            session.beginTransaction();
            result = (boolean) session.createSQLQuery("SELECT EXISTS("
                    + "SELECT * FROM taikhoan WHERE taikhoan_tendangnhap = '"+tenDangNhap+"')")
                    .uniqueResult();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi Kiểm tra sự tồn tại tài khoản <<" + tenDangNhap + ">> {}", e);
            return false;
	} finally {
            session.close();
	}
        return result;
    }
    
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
    
    public List<TaiKhoanModel> getDanhSachTaiKhoan(String tenDangNhapFilter, String tenHienThiFilter, String emailFilter){
        Session session = HibernateUtil.currentSession();
        List<TaiKhoanModel> dsTaiKhoan = new ArrayList();
        String where = "";
        if(tenDangNhapFilter != null){
            where += " AND taikhoan_tendangnhap like '%"+tenDangNhapFilter+"%' ";
        }
        if(tenHienThiFilter != null){
            where += " AND taikhoan_tenhienthi like '%"+tenHienThiFilter+"%' ";
        }
        if(emailFilter != null){
            where += " AND taikhoan_email like '%"+emailFilter+"%' ";
        }
        try {
            session.beginTransaction();
            dsTaiKhoan = session.createSQLQuery("SELECT * FROM taikhoan WHERE 1 = 1 "+where).addEntity(TaiKhoanModel.class).list();
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

    /**
     * 
     * @param objTaiKhoan   - Đối tượng tài khoản
     * @param updateRole    - true: cập nhật role, false: không cập nhật role
     * @param roles         - Mảng role
     * @return              - true: Cập nhật thành công, false: Cập nhật thất bại
     */
    public boolean updateTaiKhoan(TaiKhoanModel objTaiKhoan, boolean updateRole, String[] roles){
        Session session = HibernateUtil.currentSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(objTaiKhoan);
            if(updateRole){
                updateRole(objTaiKhoan.getId(), roles, session);
            }            
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
    
    public void updateRole(long taiKhoanID, String[] roles, Session session){
        if(roles.length == 0){
            roles = new String[1];
            roles[0] = ""+Constant.ROLE_DEFAULT;
        }
        session.createSQLQuery("DELETE FROM roles_taikhoan WHERE taikhoan_id = "+taiKhoanID).executeUpdate();
        String values = "";
        for(int i = 0; i < roles.length; i++){
            values += "("+roles[i]+","+taiKhoanID+")";
            if(i < roles.length - 1){
                values += ",";
            }
        }
        String query = "INSERT INTO roles_taikhoan(role_id, taikhoan_id)"
                + " VALUES " + values;
        session.createSQLQuery(query).executeUpdate();
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
