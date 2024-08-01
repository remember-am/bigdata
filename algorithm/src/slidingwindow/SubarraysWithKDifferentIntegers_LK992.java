package slidingwindow;

import java.util.*;

/**
 * 992. K 个不同整数的子数组
 * 给定一个正整数数组 nums和一个整数 k，返回 nums 中 「好子数组」 的数目。
 * 如果 nums 的某个子数组中不同整数的个数恰好为 k，则称 nums 的这个连续、不一定不同的子数组为 「好子数组 」。
 * 例如，[1,2,3,1,2] 中有 3 个不同的整数：1，2，以及 3。
 * 子数组 是数组的 连续 部分。
 *
 * 提示：
 * 1 <= nums.length <= 2 * 10^4
 * 1 <= nums[i], k <= nums.length
 */
public class SubarraysWithKDifferentIntegers_LK992 {
    public int subarraysWithKDistinct(int[] nums, int k) {
        // 转换思路：子数组恰好为k个的好子数组个数 = 子数组至少为k个的好子数组个数 - 子数组至少为k-1个的好子数组个数
        return f(nums, k) - f(nums, k - 1);
    }

    static final int MAX_LENGTH = (int) 2e4 + 1;
    static final int[] cnt = new int[MAX_LENGTH];

    public int f(int[] nums, int k) {
        Arrays.fill(cnt, 0);
        int ans = 0;
        // 滑动窗口：number代表当前窗口中有多少个不同的数字
        for (int l = 0, r = 0, number = 0; r < nums.length; r++) {
            // 进窗口，如果是没出现过的number++
            if (++cnt[nums[r]] == 1) {
                number++;
            }
            // 如果窗口中不同数字个数大于k个了，则窗口左边需要吐出数字
            while (number > k) {
                if (--cnt[nums[l++]] == 0) {
                    number--;
                }
            }
            // 累加当前窗口的答案，当前窗口中出现不同数字 不大于k 次的答案为l-r+1
            ans += l - r + 1;
        }
        return ans;
    }
}
