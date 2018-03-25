package com.sepient;

import java.io.IOException;

public class Test4 {

	public static void main(String[] args) {
		Test4 t = new Test4();
		int i = t.check();
		System.out.println(i);
	}

	int check() {
		try {
			System.out.println("Try Block");
			return 1;
		} catch (Exception e) {
			System.out.println("catch Block");
			return 2;
		} finally {
			System.exit(0);
			System.out.println("Finelly Returning");
			return 3;
		}
	}
}
