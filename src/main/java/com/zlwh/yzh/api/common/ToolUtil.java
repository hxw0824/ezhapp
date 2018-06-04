/**   
 * @Title: ToolUtile.java    
 * @Package com.zlwh.yzh.api.common    
 * @Description: TODO(用一句话描述该文件做什么)    
 * @author 刘斐   
 * @date 2016年6月4日 下午4:19:33    
 * @version V1.0   
 */
package com.zlwh.yzh.api.common;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName: ToolUtile
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 刘斐
 * @date 2016年6月4日 下午4:19:33
 * 
 */
public class ToolUtil {

	/**
	 * 
	 * <pre>
	 * <b>说明:</b>计算页数
	 * <b>日期:</b> 2016年6月4日 下午4:22:37
	 */
	public static int getPageSize(int totalSize, int pageSize) {
		int totalPages = 0;
		if (totalSize % pageSize == 0) {
			totalPages = totalSize / pageSize;
		} else {
			totalPages = totalSize / pageSize + 1;
		}
		return totalPages;
	}

	// 生成订单号
	public static String buildOrderNum(int payCount, String productType) {
		String head = "";
		if ("0".equals(productType)) {
			head = "V";
		} else {
			head = "C";
		}
		StringBuffer code = new StringBuffer(head);
		int num = new Random().nextInt(9999);
		if (num < 1000) {
			num += 1000;
		}
		code.append(num);
		String str = String.format("%05d", payCount);
		code.append(str);
		return code.toString();
	}

	/**
	 * 
	*<pre>
	*<b>说明:</b> 得到对应格式的日期增加结果
	*<b>日期:</b> 2016年6月22日 上午10:54:46
	 */
	public static String GetSysDate(String format, String StrDate, int year,
			int month, int day) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sFmt = new SimpleDateFormat(format);
		cal.setTime(sFmt.parse((StrDate), new ParsePosition(0)));

		if (day != 0) {
			cal.add(cal.DATE, day);
		}
		if (month != 0) {
			cal.add(cal.MONTH, month);
		}
		if (year != 0) {
			cal.add(cal.YEAR, year);

		}
		return sFmt.format(cal.getTime());
	}

	public static void main(String[] args) {

		String code = buildOrderNum(111, "0");
		System.out.println(code);
	}
}
