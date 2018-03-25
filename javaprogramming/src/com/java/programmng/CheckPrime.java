package com.java.programmng;

public class CheckPrime {

	public static void main(String[] args) {

		int count = 0;

		for (int i = 1; i <= 100; i++) {
			for (int j = 2; j <= i; j++) {
				if (i % j == 0) {
					count++;
				}
			}	
			if (i==1||count == 1) {
				System.out.println(i);
				count=0;
			}else {
				count=0;
			}
			
		}

	}

}
