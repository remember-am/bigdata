package prefixinfo;

import java.util.Arrays;

/**
 * 1371. 每个元音包含偶数次的最长子字符串
 * 给你一个字符串s，请你返回满足以下条件的最长子字符串的长度：每个元音字母，即'a'，'e'，'i'，'o'，'u' ，在子字符串中都恰好出现了偶数次。
 *
 * 提示：
 * 1 <= s.length <= 5 x 10^5
 * s 只包含小写英文字母。
 */
public class FindTheLongestSubstring_LK1371 {
    // 考虑aeiou字符串的状态，用一个正整数status表示，如果当前子字符串中a出现的次数是奇数则status二进制第4为值为1，反之则为0
    // e,i,o,u同理对应的位置为status二进制的3,2,1,0位置
    // 遍历到每一个字符串位置时，如果当前状态之前没出现过就把他加入缓存
    // 考虑当前来到i位置，状态为status，若存在某一个位置x，使得x位置到i位置的子字符串都恰好出现了偶数次，则0到x位置的状态一定
    // 等于status，因为只有status异或上自己才能使得状态为0，即存在x位置到i位置的子字符串都恰好出现了偶数次
    public int findTheLongestSubstring(String s) {
        // 用数组代替Map结构，aeiou的状态值只可能在0到31之间
        int[] map = new int[32];
        // -2代表所有状态都不存在
        Arrays.fill(map, -2);
        // 0状态在没有元素的时候就已经存在了
        map[0] = -1;
        int ans = 0;
        for (int i = 0, status = 0, idx; i < s.length(); i++) {
            // 当前字符是不是aeiou中的一个，是需要更新当前状态，不是就忽略
            idx = getLoc(s.charAt(i));
            if (idx != -1) {
                status ^= 1 << idx;
            }
            // 如果之前已经存在当前状态，则以i结尾的某个子字符串满足条件，更新答案。
            // 如果不存在当前状态，则将当前状态加入缓存
            if (map[status] != -2) {
                ans = Math.max(ans, i - map[status]);
            } else {
                map[status] = i;
            }
        }
        return ans;
    }

    public int getLoc(char c) {
        return switch(c) {
            case 'a' -> 4;
            case 'e' -> 3;
            case 'i' -> 2;
            case 'o' -> 1;
            case 'u' -> 0;
            default -> -1;
        };
    }
}
