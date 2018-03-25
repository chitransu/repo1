package com.java.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapExample {

	public static void main(String[] args) {
		Map<String,String> map=new LinkedHashMap<>();
		map.put("abhi", "1");
		map.put("bhikari", "2");
		map.put("chitranshu", "3");
		map.put("abhi", "2");
		
		Iterator<Map.Entry<String,String>> itr=map.entrySet().iterator();
		
		while(itr.hasNext()) {
			Map.Entry<String,String> entry=itr.next();
			System.out.println(entry.getKey() + "   "+entry.getValue());
		}

	}

}
