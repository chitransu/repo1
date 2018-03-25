package com.inheritance;

class ParentClass {
	
	{
		System.out.println("parent class block");
	}
	
	static {
		System.out.println("parent class static block");
	}

	public ParentClass() {
		System.out.println("Parent class");
	}
	
	public ParentClass(String str) {
		System.out.println("Parent class"+str);
	}
	
	
	public void display() {
		System.out.println("Inside Parent class..");
	}
}

class ChildClass extends ParentClass{
	
	{
		System.out.println("child class block");
	}
	
	static {
		System.out.println("child class static block");
	}
	public ChildClass() {
		System.out.println("child class");
	}
	
	@Override
	public void display() {
		System.out.println("Inside child class");
	}
	
	public void display2() {
		System.out.println("Display 2 Inside child class");
	}
}

public class MainClass {

	//first static block of parent class will execute then child class static block will execute
	//then parent class block will execute then child class block will execute
	//parent class constructor will execute then child class constructor will execute
	//if we are creating child class object and assigning in parent class reference , this reference can only call overrided methods means the methods are present in both
	// parent and child class , and in this scenerio child class method will be invoked not parent class method as it is overrided my child class method.
	// if we try to call a method via parent class reference which is present in child class but not in parent class then we will get compile time error like method is undefined
	// in parent class
	public static void main(String[] args) {
		ParentClass parentClass=new ParentClass();// this will call parent class static block , normal block , parent class constructor
		parentClass.display(); // it will call parent class display method
		ParentClass parentClass2=new ChildClass();// this will call child class static block , normal block , child class constructor
		//Note : this will not call parent class static block , normal block and constructor as it has been already called by parent class constructor during parent class object creation
		// means calls will only happen only once
		parentClass2.display(); //// it will call child class display method as it has been override by child class
		
		/*
		 * if we try to execute below given code
		 * we will get run time exception java.lang.ClassCastException as parent class can not be cast to child class
		 * so we can not invoke child class methods in this way
		ChildClass childClass=(ChildClass)(new ParentClass());
		childClass.display2();
		*/
	}
}
