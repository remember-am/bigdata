package slidingwindow;

import java.util.Arrays;

/**
 * 395. 至少有 K 个重复字符的最长子串
 * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
 * 如果不存在这样的子字符串，则返回 0。
 *
 * 提示：
 * 1 <= s.length <= 10^4
 * s 仅由小写英文字母组成
 * 1 <= k <= 10^5
 */
public class LongestSubstringWithAtLeastKRepeatingCharacters_LK395 {
    public int longestSubstring(String s, int k) {
        // 利用数据量字符串中只包含26个小写英文字母，可以使用滑动窗口解题
        // 转化为求如果只出现一种字符，出现次数都不少于k次
        //           只出现2种字符，出现次数都不少于k次
        //           只出现3种字符，出现次数都不少于k次
        //           。。。
        //           只出现26种字符，出现次数都不少于k次
        // 在以上的每种结果种求取最大值
        int n = s.length(), ans = 0;
        int[] cnt = new int[26];
        // x代表出现的字符种类
        for (int x = 1; x <= 26; x++) {
            Arrays.fill(cnt, 0);
            // 滑动窗口：r代表进窗口的数据，collect代表窗口中收集到的字符种类，satisfy代表窗口中满足出现次数最少k次的字符种类
            for (int l = 0, r = 0, collect = 0, satisfy = 0, idx; r < n; r++) {
                idx = s.charAt(r) - 'a';
                cnt[idx]++;
                // 出现字符计数是1次，则代表新增，收集到的字符种类加一
                if (cnt[idx] == 1) {
                    collect++;
                }
                // 出现字符计数是k次，则代表满足字符出现最少k次，satisfy加一
                if (cnt[idx] == k) {
                    satisfy++;
                }
                // 如果窗口中收集到的字符种类超过x种了，则开始收缩窗口，因为窗口中需要字符种类需要严格等于x种
                while (collect > x) {
                    idx = s.charAt(l) - 'a';
                    if (cnt[idx] == 1) {
                        collect--;
                    }
                    if (cnt[idx] == k) {
                        satisfy--;
                    }
                    cnt[idx]--;
                }
                // 满足条件的satisfy数等于x个，则找到一个答案
                if (satisfy == x) {
                    ans = Math.max(ans, r-  l + 1);
                }
            }
        }
        return ans;
    }
}
