package com.java.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ReadCSVFileExample {

	private static Scanner sc;
	
	public static void main(String[] args) {
		String filePath="toturial.txt";
		String searchTerm="1234";
		readRecord(filePath,searchTerm);
	}
	
	private static void readRecord(String filePath,String searchTerm) {
		boolean found=false;
		String ID="";String name="";String age="";
		try {
			sc=new Scanner(new File(filePath));
			sc.useDelimiter("[|\n]");
			while(sc.hasNext() && !found) {
				ID=sc.next();
				name=sc.next();
				age=sc.next();
				if(ID.equals(searchTerm)) {
					found=true;
				}
			}
			if(found) {
				JOptionPane.showMessageDialog(null, "ID:"+ID+"name:"+name+"Age:"+age);
			}else {
				JOptionPane.showMessageDialog(null, "Record Not Found");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
