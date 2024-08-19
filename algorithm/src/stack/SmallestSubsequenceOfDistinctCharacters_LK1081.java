package stack;

import java.util.Arrays;

/**
 * 1081. 不同字符的最小子序列
 * 316. 去除重复字母
 * 返回 s 字典序最小的 子序列 ，该子序列包含 s 的所有不同字符，且只包含一次。
 *
 * 提示:
 * 1 <= s.length <= 10^4
 * s 由小写英文字母组成
 */
public class SmallestSubsequenceOfDistinctCharacters_LK1081 {

    static final int MAX_LENGTH = 26;

    static final char[] stack = new char[MAX_LENGTH];

    // 计数数组
    static final int[] cnt = new int[MAX_LENGTH];

    // 表示在对应位置的字符是否进入到栈里了
    static final boolean[] enter = new boolean[MAX_LENGTH];

    static int r;

    public String smallestSubsequence(String s) {
        // 静态数组需要初始化
        Arrays.fill(cnt, 0);
        Arrays.fill(enter, false);
        r = 0;
        int ans = 0, n = s.length();
        // 统计每个字符的词频
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // 当前的字符没有进过栈才计算，否则直接跳过，跳过也需要对应的词频减一
            if (!enter[c - 'a']) {
                // 如果当前字符没有进过栈里，且当前字符比栈顶字符小，说明当前字符字典序比栈顶字符字典序小，应该排在栈顶字符的前面
                // 但是当前字符能不能取代栈顶字符还需要看栈顶字符在后续里还有没有，即栈顶字符的词频是否大于0，大于0则可以取代
                while (r > 0 && c < stack[r - 1] && cnt[stack[r - 1] - 'a'] > 0) {
                    // 栈顶字符被弹出，对应的也就是弹出的字符就没进过栈了
                    enter[stack[--r] - 'a'] = false;
                }
                // 当前字符进栈
                enter[c - 'a'] = true;
                stack[r++] = c;
            }
            cnt[c - 'a']--;
        }
        return new String(stack, 0, r);
    }
}
