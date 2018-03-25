package com.java.programmng;

import java.util.Scanner;

public class CalculateAverage {

	public static void main(String[] args) {
		
 
		//int[] numbers= {10,20,30,40,50};
		
		System.out.println("How many numbers you want to enter :");
		Scanner sc=new Scanner(System.in);
		int size=sc.nextInt();
		
		int[] numbers=new int[size];
		
		for(int i=0;i<numbers.length;i++) {
			System.out.print("Enter no : "+(i+1));
			numbers[i]=sc.nextInt();
		}
		
		sc.close();
		
		int sum=0;
		
		for(int i=0;i<numbers.length;i++) {
			sum=sum+numbers[i];
		}
		
		int average=0;
		
		average=sum/numbers.length;
		
		System.out.println("Average is : "+average);
		
	}

}
