package binarysearch;

import java.util.Arrays;

/**
 * 719. 找出第 K 小的数对距离
 * 数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
 * 给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length 。
 * 返回 所有数对距离中 第 k 小的数对距离。
 *
 * 提示：
 * n == nums.length
 * 2 <= n <= 10^4
 * 0 <= nums[i] <= 10^6
 * 1 <= k <= n * (n - 1) / 2
 */
public class FindKThSmallestPairDistance_LK719 {
    // 二分答案法：1、数组排序
    //           2、数对距离的最小值是0，最大值是数组最大值减数组最小值
    //           3、求取在给定数对距离为不超过m的情况下，有多少个满足条件的数对
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 0, n = nums.length;
        for (int l = 0, r = nums[n - 1] - nums[0], m, cur; l <= r; ) {
            m = l + ((r - l) >> 1);
            // 在数对距离不超过m的情况下，返回有多少个数对
            cur = f(nums, m);
            if (cur >= k) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    // 在数对距离不超过m的情况下，返回有多少个数对
    public int f(int[] nums, int m) {
        int ans = 0;
        for (int l = 0, r = 0; l < nums.length; l++) {
            // 滑动窗口
            while (r < nums.length - 1 && nums[r + 1] - nums[l] <= m) {
                r++;
            }
            ans += r - l;
        }
        return ans;
    }
}
