package com.java.collection;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee>{

	@Override
	public int compare(Employee emp1, Employee emp2) {
		int flag=emp1.getDate().compareTo(emp2.getDate());
		if(flag==0) {
			flag= emp1.getId()-emp2.getId();
			if(flag==0) {
				flag=emp1.getName().compareTo(emp2.getName());
			}
		}
		return flag;
		
	}

}
