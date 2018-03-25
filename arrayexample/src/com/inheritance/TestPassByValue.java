package com.inheritance;

public class TestPassByValue {

	public static void main(String[] args) {
		TestPassByValue test=new TestPassByValue();
		int[] arr= {10,20};
		test.m1(arr);
		System.out.println(arr[0]+" "+arr[1]);

	}
	
	public void m1(int[] intArr) {
		intArr[0]=80;
		intArr[1]=70;
	}

}

