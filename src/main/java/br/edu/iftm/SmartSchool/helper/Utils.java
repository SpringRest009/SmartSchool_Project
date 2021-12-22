package br.edu.iftm.SmartSchool.helper;

public class Utils {
    
    public static Boolean isLong(String value){
        try {  
            Long.parseLong(value);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }
    }
}
