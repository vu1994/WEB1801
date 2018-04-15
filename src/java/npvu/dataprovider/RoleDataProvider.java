/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.dataprovider;

import npvu.util.HibernateUtil;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author npvu
 */
public class RoleDataProvider implements Serializable {  
    private static final Logger log = LoggerFactory.getLogger(RoleDataProvider.class);
    
    public List<Map> getDanhSachRole(){
        Session session = HibernateUtil.currentSession();
        List<Map> dsRole = new ArrayList();
        try {
            session.beginTransaction();
            dsRole = session.createSQLQuery("SELECT * FROM roles")
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                    .list();
            session.getTransaction().commit();
	} catch (Exception e) {
            log.error("Lỗi get danh sách role {}", e);
	} finally {
            session.close();
	}
        return dsRole;
    }
    
    public String[] getDanhSachRoleByTaiKhoan(long taiKhoanID){
        Session session = HibernateUtil.currentSession();
        List<Map> dsRole;
        String[] arrResult = null;
        try {
            session.beginTransaction();
            dsRole = session.createSQLQuery("SELECT role.*"
                    + " FROM roles_taikhoan rtk"
                    + " LEFT JOIN roles role"
                    + " ON role.role_id = rtk.role_id "
                    + " WHERE rtk.taikhoan_id = "+taiKhoanID)
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                    .list();
            session.getTransaction().commit();
            arrResult = new String[dsRole.size()];
            int i = 0;
            for(Map role : dsRole){
                arrResult[i] = role.get("role_id").toString();
                i++;
            }
	} catch (Exception e) {
            log.error("Lỗi get danh sách quyền theo tài khoản id: <<" + taiKhoanID + ">> {}", e);
	} finally {
            session.close();
	}
        return arrResult;
    }
}
