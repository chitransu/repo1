package com.java.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ComparatorDemo {

	public static void main(String[] args) {
		List<Employee> employeeList=new ArrayList<Employee>();
		employeeList.add(new Employee(1,"Banana",new Date("28/08/1986")));	
		employeeList.add(new Employee(4,"Kiwi",new Date("28/06/1987")));
		employeeList.add(new Employee(3,"Graps",new Date("20/08/1987")));		
		employeeList.add(new Employee(5,"Payaya",new Date("28/07/1986")));
		employeeList.add(new Employee(2,"Apple",new Date("28/08/1987")));
		
		System.out.println("Before Sorting");
		for(Employee employee:employeeList) {
			System.out.println(employee);
		}
		
		Collections.sort(employeeList,new EmployeeComparator());
		System.out.println("After Sorting");
		
		for(Employee employee:employeeList) {
			System.out.println(employee);
		}
	}
}
