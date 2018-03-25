package com.sepient;

import java.io.IOException;

public class Test {

	public static void main(String[] args) {
		Test t = new Test();
		int i = t.check();
		System.out.println(i);
	}

	int check() {
		try {
			throw new IOException();
			//As we are throwing exception explicitly due to this return statement will be unreachable
			// thus we will get compiletime exception
			//but in case code is throwing any exception like below then same code will work fine
			//int result = 1 / 0;
			//return 1; // Unreachable code
		} catch (Exception e) {
			System.out.println("catch Block");
			return 2;
		} finally {
			System.out.println("Finelly Returning");
			return 3;
		}
	}

}
