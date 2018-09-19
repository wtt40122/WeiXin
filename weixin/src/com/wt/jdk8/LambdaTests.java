package com.wt.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import com.google.common.base.Supplier;

/**
 * @Description:
 * @author wt
 * @date 2018年9月19日
 */
/**
 * java8内置的四大核心函数式接口
 * Consumer<T>:消费性接口
 * 			void accept(T t);
 * Supplier<T>:供给型接口
 * 			T get();
 * Function<T,R>:函数式接口
 * 			R apply(T t);
 * Predicate<T>:断言型接口
 * 			boolean test(T t);
 */
public class LambdaTests {
	/**
	 * Consumer->消费性接口
	 */
	@Test
	public void test(){
		happy(100,(m)->System.out.println("他花了："+m+"元"));
	}
	public void happy(double money,Consumer<Double> con){
		con.accept(money);
	}
	/**
	 * supplier->供给型接口
	 */
	@Test
	public void test2(){
		List<Integer> numList = getNumList(10, ()->(int)(Math.random()*100));
		for (Integer integer : numList) {
			System.out.println(integer);
		}
	}
	public List<Integer> getNumList(int num,Supplier<Integer> sup){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < num; i++) {
			list.add(sup.get());
		}
		return list;
	}
	/**
	 * Function->函数型接口
	 */
	@Test
	public void test3(){
		String newStr =strHandler("\t\t\t我们都是开发者  ", (str)->str.trim()) ;
		System.out.println(newStr);
		
		System.out.println(strHandler("我们都是好孩子", (str)->str.substring(2, 5)));
	}
	public String strHandler(String str,Function<String,String> fun){
		return fun.apply(str);
	}
	/**
	 * Predicate->断言型接口
	 */
	@Test
	public void test4(){
		List<String> list= Arrays.asList("hello","wo","what","3","ewrewre");
		
		List<String> filterStr = filterStr(list, (str)->str.length()>3);
		for (String str : filterStr) {
			System.out.println(str);
		}
	}
	public List<String> filterStr(List<String> list,Predicate<String> pre){
		List<String> strList = new ArrayList<String>();
		for (String str : list) {
			if(pre.test(str)){
				strList.add(str);
			}
		}
		return strList;
	}
}
