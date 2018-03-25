package com.practice.example;

public class ExceptionDemo {

	public static void main(String[] args) {
		ExceptionDemo exceptionDemo=new ExceptionDemo();
		int result=exceptionDemo.testException(3);
		System.out.println("result is "+ result);

	}
	
	@SuppressWarnings("finally")
	public int testException(int number) {
		int result =0;
		try {
			result=number/0;
			return result;
		}catch(Exception e) {
			result=200;
			return result;
			
		}finally{
			//result=300;
			return result;
		}
	}

}
