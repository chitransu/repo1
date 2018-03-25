package com.sepient;

public class Test2 {

	public static void main(String[] args) {
		Test2 t = new Test2();
		int i = t.check();
		System.out.println(i);
	}

	int check() {
		try {
			System.out.println("try block");
			System.exit(0);
			return 1;
		} catch (Exception e) {
			System.out.println("catch Block");
			return 2;
		} finally {
			System.out.println("Finelly Returning");
			return 3;
		}
	}

}
