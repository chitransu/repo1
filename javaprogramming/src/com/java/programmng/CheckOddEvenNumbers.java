package com.java.programmng;

import java.util.Scanner;

public class CheckOddEvenNumbers {

	public static void main(String[] args) {
		
		System.out.println("Enter number to check odd or even :");
		Scanner sc=new Scanner(System.in);
		int number=sc.nextInt();
		
		if(number%2==0) {
			System.out.println("This is even number");
		}else {
			System.out.println("This is odd number");
		}
	}
}
