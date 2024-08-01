package slidingwindow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 3. 无重复字符的最长子串
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长 子串 的长度。
 *
 * 提示：
 * 0 <= s.length <= 5 * 10^4
 * s 由英文字母、数字、符号和空格组成
 */
public class LengthOfLongestSubstring_LK3 {
    public int lengthOfLongestSubstring(String s) {
        if (s.length() < 2) {
            return s.length();
        }
        int[] arr = new int[255];
        Arrays.fill(arr, -1);
        int ans = 0;
        for (int l = 0, r = 0; r < s.length(); r++) {
            char c = s.charAt(r);
            l = Math.max(l, arr[c] + 1);
            arr[c] = r;
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }
}
