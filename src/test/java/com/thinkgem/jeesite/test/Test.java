package com.thinkgem.jeesite.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.activiti.bpmn.model.InclusiveGateway;

import com.thinkgem.jeesite.common.utils.DateUtils;

public class Test {

	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = simpleDateFormat.parse("2016-09-01");
//		long days = DateUtils.pastDays(date);
//		days = days%100;
//		System.out.println(days);
//		Date date2 = new Date();
//		String dateString = simpleDateFormat.format(date2); 
//		System.out.println(dateString);
//		Calendar cal = Calendar.getInstance();
//		cal.set(2016, 6, 25, 14, 15, 15);
//		int day = cal.get(Calendar.DATE);
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH);
//		int week = cal.get(Calendar.DAY_OF_WEEK);
//		cal.add(Calendar.DATE, -1);
//		String string = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
//		System.out.println(string);
//		
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date date = simpleDateFormat.parse("2016-01-01");
//		long days = DateUtils.pastDays(date);
//		days = Math.abs(days%100);
//		System.out.println(days);
		
//		Random random = new Random();
//		int i = random.nextInt(99999);
//		String imageName ="地理上_部分142_1";
//		String indexString = imageName.substring(imageName.indexOf("分")+1, imageName.length()-2);
//		int index =Integer.parseInt(indexString);
//		if(index>64){
//			index=index-2;
//		}
//		if (index>126) {
//			index=index-2;
//		}
//		int sort=0;
//		if(index%2==0){
//			sort = (index-16)/2+1;
//		}else {
//			sort = (index-1-16)/2+1;
//		}
		String imageName ="80.jpg";
		int sort= Integer.parseInt(imageName.substring(0, imageName.length()-4));
		String newName = String.format("%02d", sort);
		System.out.println(newName);
	}
}
