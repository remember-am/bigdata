package slidingwindow;

/**
 * 76. 最小覆盖子串
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 * 注意：
 *   对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 *   如果 s 中存在这样的子串，我们保证它是唯一的答案。
 *
 * 提示：
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 10^5
 * s 和 t 由英文字母组成
 */
public class MinimumWindowSubstring_LK76 {
    public String minWindow(String s, String t) {
        // 如果t长度大于s长度，那肯定不存在答案
        if (t.length() > s.length()) {
            return "";
        }
        // 由于字符只有255种，所以准备长度255的数组就可以计数了
        int[] cnt = new int[255];
        // 表示s需要凑齐t中对应字符的个数
        for (char c : t.toCharArray()) {
            cnt[c]--;
        }
        // start记录最小包含子串的开始位置，len记录最小包含字串的长度
        int start = 0, len = Integer.MAX_VALUE;
        // 滑动窗口：把t字符串的长度当成是欠款，那么s字符串需要还完t的所有欠款才算找到答案
        //         右边界r进来就判断是不是有效的还款
        //         如果还款已经清零，则代表l到r这段子串是能包含t的，但可能存在多余
        for (int l = 0, r = 0, debt = t.length(); r < s.length(); r++) {
            if (cnt[s.charAt(r)]++ < 0) {
                debt--;
            }
            if (debt == 0) {
                // debt等于0则l到r是能包含t的，但需要看左边界l是不是多余的无效字符
                while (cnt[s.charAt(l)] > 0) {
                    cnt[s.charAt(l++)]--;
                }
                // 从while里出来，说明找到一个答案，更新答案
                if (r - l + 1 < len) {
                    start = l;
                    len = r - l + 1;
                }
            }
        }
        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }
}
