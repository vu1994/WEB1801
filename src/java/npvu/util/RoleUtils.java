/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import npvu.config.Constant;
import npvu.controller.Login;
import npvu.dataprovider.RoleDataProvider;

/**
 *
 * @author npvu
 */
public class RoleUtils {
    
    private final RoleDataProvider roleProvider = new RoleDataProvider();
    
    private String[] arrRole;
    
    public RoleUtils(){
        
    }
    
    public boolean checkRole(int roleID){        
        if(!Login.logined){
            try {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                ec.redirect(ec.getRequestContextPath() + Constant.URL_DANGNHAP);
                return false;
            } catch (IOException ex) {
                Logger.getLogger(RoleUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            arrRole = Login.roles;
            for(String role : arrRole){
                if(Integer.parseInt(role)==Constant.ROLE_FULL || Integer.parseInt(role)==roleID){
                    return true;
                }
            }
        }        
        return false;
    }
}
