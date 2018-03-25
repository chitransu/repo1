package com.java.busroute;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class ValidatorExamp {

	public static void main(String[] args) {
		
		String pattern="";
		String charSeq="1234a";
		boolean result=true;
		
		if(!Pattern.matches("\\d{5}", "12345")) {
			result=false;
		}
		
		if(!Pattern.matches("\\d+", "20")) {
			result=false;
		}
		
		System.out.println(result);
		
		/*SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
		Date date=null;
		try {
			date = sf.parse("05-01-2017");
		} catch (ParseException e) {
			System.out.println("Unable to parse date");
		}
		
		SimpleDateFormat formatter=new SimpleDateFormat("F");
		
		String dateStr=formatter.format(date);
		System.out.println(dateStr);*/
		
		SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
		Date date=null;
		try {
			date = sf.parse("12-12-2017");
		} catch (ParseException e) {
			System.out.println("Unable to parse date");
		}
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		
		//System.out.println(calendar.get(Calendar.));
	}
}
