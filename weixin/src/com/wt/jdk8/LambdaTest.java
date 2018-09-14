package com.wt.jdk8;

import java.util.Comparator;
import java.util.TreeSet;

import org.junit.Test;

/**
 * @Description:lamnda表达式的学习
 * @author wt
 * @date 2018年9月14日
 */
public class LambdaTest {
	@Test
	public void testLambda1(){
		//匿名内部类
		Runnable run = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Hello world");
			}
		};
		//表达式
		Runnable runL = ()->System.out.println("这是Lambda表达式的测试");
		//使用匿名内部类作为参数传递
		TreeSet<String> ts = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}
		});
		TreeSet<String> tsl = new TreeSet<String>((o1,o2)->Integer.compare(o1.length(), o2.length()));
		//无参-无返回值--只需一条语句
		//
	}
}
