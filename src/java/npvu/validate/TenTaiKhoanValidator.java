/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.validate;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author npvu
 */
@FacesValidator("TenTaiKhoanValidator")
public class TenTaiKhoanValidator implements Validator{
    private static final String ERROR_NULL = "Vui lòng nhập tên tài khoản !";
    
    public TenTaiKhoanValidator(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
        String msg = "";
        if(value != null){
            String taiKhoan = value.toString();
        } else {
            msg = ERROR_NULL;
        }
        FacesMessage message = new FacesMessage(msg);
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        context.addMessage(comp.getClientId(), message);
        throw new ValidatorException(message);
    }
    
}
