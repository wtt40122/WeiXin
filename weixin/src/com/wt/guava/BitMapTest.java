package com.wt.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @Description:键值都不可重复
 * @author wt
 * @date 2018年9月14日
 */
public class BitMapTest {
	public static void main(String[] args) {
		BiMap<String, String> biMap = HashBiMap.create();
		biMap.put("No.1", "China");
		biMap.put("No.2", "USA");
		biMap.put("No.3", "Japan");
		System.out.println(biMap);
		System.out.println(biMap.inverse());
	}
}
