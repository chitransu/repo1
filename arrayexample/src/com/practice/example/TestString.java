package com.practice.example;

public class TestString {

	public static void main(String[] args) {
		String s1="ABC";
		//String s2=new String("ABC");
		String s2="ABC";
		
		
		if(s1.equals(s2)) {
			System.out.println("s1.equals(s2) is true");
			System.out.println(s1.hashCode());
			System.out.println(s2.hashCode());
		}
		
		if(s1==s2) {
			System.out.println("s1==s2 is true");
		}
	}

}
