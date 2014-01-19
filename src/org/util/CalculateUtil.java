package org.util;

import java.util.ArrayList;
import java.util.Random;

public class CalculateUtil {
	
	// 生成3个 [1-size]不重复的随机数
	public static ArrayList<Integer> getRandomList(int size){
		ArrayList<Integer> list = new ArrayList<Integer>();
		int n = size;
		Random rand = new Random();
		boolean[] bool = new boolean[n];
		int num = 0;
		
		for (int i = 0; i < 3; i++) {
			do {
				// 如果产生的数相同继续循环
				num = rand.nextInt(n);
			} while (bool[num]);
				bool[num] = true;
				list.add(num+1);
		}
	  
		return list;
	}
}
