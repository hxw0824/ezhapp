package com.zlwh.yzh.api.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.thinkgem.jeesite.common.config.Global;

public class CheckDate {
	
	public static boolean hasPermision(int flag,String time){
		//根据tel获取订单的失效期
		try {
			if(flag==0){//如果是试用期
				if(daysBetween(convert(time),new Date())<Integer.parseInt(Global.getInDate())){
					return true;
				}
			}else{
				if(daysBetween(convert(time),new Date())<0){
					return true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	  public static int daysBetween(Date smdate,Date bdate) throws Exception    
	    {    
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	        smdate=sdf.parse(sdf.format(smdate));  
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));           
	    } 

     public static Date convert(String time){
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Date d = new Date();
         try {
        	 d= sdf.parse(time);
			System.out.println(sdf.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return d;
      }
}
