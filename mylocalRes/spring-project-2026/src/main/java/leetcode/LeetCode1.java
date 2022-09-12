package leetcode;

import java.util.*;
import java.lang.*;
import java.io.*;

public class LeetCode1 {
	Boolean check = true;
	static int array[] = { 3, 5, 7, 2 };
	int checkN = 0;
	static int target = 9;
	int ArrayLen = array.length;
	int ArrayRound = 0;
	int total = 0;

	Map<Integer, Integer> map = new HashMap<>();

	public Boolean CallBack(Boolean checks) {
		return checks;
	}

	public int[] twosum(int[] nums, int targetNum) {
		List<Integer> CallValue = new ArrayList<>();

		for (int i = 0; i < ArrayLen; i++) {
			total = nums[checkN] + nums[i];
			if (total == targetNum) {
				CallValue.add(nums[checkN]);
				CallValue.add(nums[i]);

//				map.put(checkN, array[checkN])
//				map.put(i, array[i]);

			} else {
				total = 0;
				
			}
			ArrayRound = ArrayRound+1;
			if (ArrayRound == 4) {
				ArrayRound = 0;
				checkN = checkN+1;			
			}

		}
		
		if (CallValue.size() > 0) {
			int[] ReturnA = new int[CallValue.size()];
			for (int i = 0; i < CallValue.size(); i++) {
				ReturnA[i] = CallValue.get(i);
			}
			return ReturnA;
		}
		return null;
	}

	public static void main(String[] args) {
		/* package whatever; // don't place package name! */

		LeetCode1 a = new LeetCode1();
		while (true) {

			int[] Data = a.twosum(array, target);
			if (Data != null) {
				System.out.println(Data[0]);
				break;
			}

			System.out.println("第" + a.checkN +"次");

		}

	}

}
