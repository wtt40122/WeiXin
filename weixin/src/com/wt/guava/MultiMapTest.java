package com.wt.guava;

import java.util.Collection;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * @Description:键值可重复使用
 * @author wt
 * @date 2018年9月14日
 */
public class MultiMapTest {
	public static void main(String[] args) {
		Multimap<String, String> myMultimap = ArrayListMultimap.create();
		myMultimap.put("Fruit", "banana");
		myMultimap.put("Fruit", "apple");
		myMultimap.put("Fruit", "pear");
		myMultimap.put("Vegetables", "carrot");
		
		int size = myMultimap.size();
		System.out.println("长度:"+size);
		
		Collection<String> fruits = myMultimap.get("Fruit");
		System.out.println(fruits);
		
		for (String value : myMultimap.values()) {
			System.out.println(value);
		}
		
		myMultimap.remove("Fruit", "pear");
		System.out.println(myMultimap.get("Fruit"));
		
		myMultimap.removeAll("Fruit");
		System.out.println(myMultimap.get("Fruit"));
	}
}
