/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npvu.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author b1203
 */
public class DateUtils {
    
    public DateUtils(){
        
    }
    
    public static Date getCurrentDate(){
        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();
        return current;
    }
}
