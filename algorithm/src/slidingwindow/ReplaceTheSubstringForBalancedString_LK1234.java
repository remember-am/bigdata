package slidingwindow;

/**
 * 1234. 替换子串得到平衡字符串
 * 有一个只含有 'Q', 'W', 'E', 'R' 四种字符，且长度为 n 的字符串。
 * 假如在该字符串中，这四个字符都恰好出现 n/4 次，那么它就是一个「平衡字符串」。
 * 给你一个这样的字符串 s，请通过「替换一个子串」的方式，使原字符串 s 变成一个「平衡字符串」。
 * 你可以用和「待替换子串」长度相同的 任何 其他字符串来完成替换。
 * 请返回待替换子串的最小可能长度。如果原字符串自身就是一个平衡字符串，则返回 0。
 *
 * 提示：
 * 1 <= s.length <= 10^5
 * s.length 是 4 的倍数
 * s 中只含有 'Q', 'W', 'E', 'R' 四种字符
 */
public class ReplaceTheSubstringForBalancedString_LK1234 {
    public int balancedString(String s) {
        // Q W E R对应0 1 2 3，先统计对应的字符出现的个数
        int[] cnt = new int[4];
        for (char c : s.toCharArray()) {
            cnt[get(c)]++;
        }
        // 答案最多的长度也就是字符串本身的长度
        int ans = s.length();
        // want代表每个字符期望的个数
        int want = ans / 4;
        // 滑动窗口：维持l到r的窗口左闭右开[l,r)，转换另一种思路，cnt数组只统计 不在 [l,r)范围内的字符个数
        // 现在假设除开[l,r)范围内的其它字符Q出现4次，W出现3次，E出现6次，R出现0次，而每个字母所需的个数是6
        // 那么[l,r)范围内想变化得到结果，则需要2个Q、3个W、0个E、6个R，总数是11，则r-l需要等于11才能有答案
        for (int l = 0, r = 0; l < s.length(); l++) {
            // r尝试往前扩，看能不能满足[l,r)范围内变换字符得到答案
            while (!ok(cnt, r - l, want) && r < s.length()) {
                // 如果不能得到答案，则r继续往前扩，此时计数数组中需要减去窗口中的字符，因为计数数组中只统计除开窗口的字符
                cnt[get(s.charAt(r++))]--;
            }
            // 从上面while循环中出来，需要看是r越界还是满足条件，如果满足条件则找到一个答案
            if (ok(cnt, r - l, want)) {
                ans = Math.min(ans, r - l);
            }
            // [l,r)窗口说明l到r位置变换才能找打答案，说明l+1,l+2,l+3,...,r-1到r位置一定找不到答案
            // 窗口继续从l+1位置开始寻找答案，但是窗口右边界r不需要回退
            cnt[get(s.charAt(l))]++;
        }
        return ans;
    }

    public boolean ok(int[] cnt, int len, int want) {
        for (int i = 0; i < 4; i++) {
            // len是窗口的长度，want是需要的个数
            // 如果当前不在窗口范围内中字符中，有任意一个字符的个数大于需要的个数，那一定搞不定
            if (cnt[i] > want) {
                return false;
            }
            len -= want - cnt[i];
        }
        // 如果能搞定，窗口的长度一定等于其它字符少于want的个数的和
        return len == 0;
    }

    public int get(char c) {
        return switch (c) {
            case 'Q' -> 0;
            case 'W' -> 1;
            case 'E' -> 2;
            default -> 4;
        };
    }
}
