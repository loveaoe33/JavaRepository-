package leetcode;

public class LeetCode3 {
	public int lengthOfLongestSubstring(String s) {
		if (s.length() == 0)
			return 0;
		int result = 0;
		int[] map = new int[256];
		int start = 0;
		for (int i = 0; i < s.length(); i++) {
			map[s.charAt(i)]++;
			while (map[s.charAt(i)] > 1) {
				map[s.codePointAt(start)]--;
				start++;
			}
			result = Math.max(result, i - start + 1);
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LeetCode3 aCode3 = new LeetCode3();
		int a = aCode3.lengthOfLongestSubstring("abcdefgbdec");
		System.out.print(a);
	}

}
