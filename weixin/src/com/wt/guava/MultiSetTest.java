package com.wt.guava;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * @Description:无序，可重复
 * @author wt
 * @date 2018年9月14日
 */
public class MultiSetTest {
	public static void main(String[] args) {
		String str= "this is a cat and that is a mice";
		String[] strArr = str.split(" ");
		List<String> words = new ArrayList<String>();
		for (String stmp : strArr) {
			words.add(stmp);
		}
		Multiset<String> wordMultiset = HashMultiset.create();
		wordMultiset.addAll(words);
		
		//将不同元素放在一个集合set中
		for(String key : wordMultiset.elementSet()){
			//查看指定元素的个数
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		System.out.println("---------向集合添加元素-----------------");
		wordMultiset.add("that");
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		System.out.println("---------向集合添加若干个元素--------------");
		wordMultiset.add("and",10);
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		//向集合删除移除一个元素
		System.out.println("----------从集合中移除一个元素--------------");
		wordMultiset.remove("that");
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		System.out.println("----------从集合移除若干个元素---------------");
		wordMultiset.remove("and",10);
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		System.out.println("--------设置某一元素的重复次数---------------");
		wordMultiset.setCount("and", 6);
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		System.out.println("---------设置复合元素的个数为新的重复次数");
		wordMultiset.setCount("and", 6, 1);
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
		
		System.out.println("-----------删除指定集合中的元素--------------");
		wordMultiset.removeAll(words);
		for(String key : wordMultiset.elementSet()){
			System.out.println(key + "-->" + wordMultiset.count(key));
		}
	}
}
