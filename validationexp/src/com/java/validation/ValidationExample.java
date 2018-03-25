package com.java.validation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class ValidationExample {

	public static void main(String args[]) {

		if(lookUp("Monday")) {
		JOptionPane.showMessageDialog(null, "valid");
		}else {		
			JOptionPane.showMessageDialog(null, "Invalid");
		}
		
	}
	
	public static boolean rangeCheck(int num,int min,int max) {
		return  num>min && num<max;
	}
	
	public static boolean leanthCheck(String input,int len) {
		return input.length()==len;
	}
	
	public static boolean stringLenthRangeCheck(String input,int min,int max) {
		return input.length()>min && input.length()<max;
	}
	
	public static boolean phoneNoCheck(String num) {
		//return (num.charAt(0)=='9' && num.charAt(1)=='1' && num.length()==12 && num.matches("[0-9]+"))||(num.charAt(0)=='0' && num.length()==11 && num.matches("[0-9]+"));
		return (num.startsWith("91") && num.length()==12 && num.matches("[0-9]+")) || (num.startsWith("0") && num.length()==11 && num.matches("[0-9]+")) || (num.length()==10 && num.matches("[0-9]+"));
	}
	
	public static boolean dateCheck(String date) {
		DateFormat df=new SimpleDateFormat("dd/MM/yyyy");	
		df.setLenient(false);
		try {
			Date dob=df.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean lookUp(String input) {
		String days[]= {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
		boolean valid=false;
		for(int i=0;i<days.length;i++) {
			if(input.equals(days[i])) {
				valid=true;
			}
		}
		return valid;
	}
}
