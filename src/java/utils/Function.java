/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author PC
 */
public class Function {
    
    public static java.sql.Date stringToDate(String srtDate){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = null;
        try {
            java.util.Date utilDate = dateFormat.parse(srtDate);
            
            date = new java.sql.Date(utilDate.getTime());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return date;
    }
}
