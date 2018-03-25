package com.sepient;

import java.io.IOException;

public class Test3 {

	public static void main(String[] args) {
		Test3 t = new Test3();
		int i = t.check();
		System.out.println(i);
	}

	int check() {
		try {
			throw new IOException();
		} catch (Exception e) {
			System.out.println("catch Block");
			return 1;
		} finally {
			System.out.println("Finelly Returning");
			return 2;
		}
	}
}
