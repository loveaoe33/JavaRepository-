package leetcode;

public class LeetCode4 {

	public String BackNumber(String s) {
		Boolean[][] array = new Boolean[s.length()][s.length()];
		int[][] array2 = new int[s.length()][s.length()];
		int result = 0;
		int len = 0;
		int start = 0;
		int end = 0;
		int mid = 0;// 如是偶數中間值
		int ResultStart = 0;
		int Resultend = 0;
		int left=0;
		int right=0;
	   boolean C = false;
		String T = "";

		if (s.length() < 2)
		
			return "0";
             
		for (int i = 0; i < s.length(); i++) {
			for (int j = i + 1; j < s.length(); j++) {

				if (s.charAt(i) == s.charAt(j) && j - i > 2 && ((j + 1) - i) % 2 == 0) {
					len = (j + 1) - i;
					mid = len / 2;
					left=i;
					right=j;
					for (int x = 1; x < mid; x++) {
					    left++;
					    right--;
						if (s.charAt(left) == s.charAt(right)) {
							C = true;
						} else {
							C = false;
							break;
						}
					}

					if (C == true) {
						start = i;
						end = j;
						if(result<len) {ResultStart = start;Resultend = end;}
						result=Math.max(result, len);

					}

				} else if (s.charAt(i) == s.charAt(j) && j - i > 2 && ((j + 1) - i) % 2 >= 0)

				{
					len = (j + 1) - i;
					mid = len / 2;
					left=i;
					right=j;
					for (int x = 1; x < mid; x++) {
						 left++;
						   right--;
						if (s.charAt(left) == s.charAt(right)) {
							C = true;
						} else {
							C = false;
							break;
						}
					}

					if (C == true) {
						start = i;
						end = j;

						if(result<len) {ResultStart = start;Resultend = end;}
						result=Math.max(result, len);

					}

				} else if (s.charAt(i) == s.charAt(j) && j - i <= 2) {
					if (s.charAt(i + 1) == s.charAt(j - 1)) {
						start = i;
						end = j;
						len = (j + 1) - i;
						
						
						if(result<len) {ResultStart = start;Resultend = end;}
						result=Math.max(result, len);
				
					}

				}

			}
		}
		T = s.substring(ResultStart, Resultend+1);
		return T;

	}

	public static void main(String[] args) {
		LeetCode4 L = new LeetCode4();
		String S = L.BackNumber("avavaabccbac");
		System.out.print(S);

	}

}
