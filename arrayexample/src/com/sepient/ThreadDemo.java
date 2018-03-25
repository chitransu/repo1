package com.sepient;

import java.util.ArrayList;

class A {

}

class B extends A {

}

class C extends B {

}

public class ThreadDemo {

	public static void main(String[] args) {
		ArrayList<A> x = new ArrayList<A>();
		ArrayList<B> a = new ArrayList<B>();
		x.add(new A());
		//a=(ArrayList<B>)x;

	}
}
