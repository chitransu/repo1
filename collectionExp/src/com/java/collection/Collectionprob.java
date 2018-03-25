package com.java.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collectionprob {

	public static void main(String[] args) {
		int[] numbers= {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,7};
		
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		
		for(int i=0;i<numbers.length;i++) {
			if(map.containsKey(numbers[i])) {
				map.put(numbers[i], map.get(numbers[i])+1);
			}else {
				map.put(numbers[i], 1);
			}
		}
		List<Integer> duplicateNumbers=new ArrayList<Integer>();
		for(Integer number:map.keySet()) {
			if(map.get(number)==1) {
				duplicateNumbers.add(number);
			}
		}
		
		for(Integer duplicateNUmber:duplicateNumbers) {
			System.out.println(duplicateNUmber);
		}

	}

}
