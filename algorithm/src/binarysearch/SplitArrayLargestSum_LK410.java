package binarysearch;

/**
 * 410. 分割数组的最大值
 * 给定一个非负整数数组 nums 和一个整数 k ，你需要将这个数组分成 k 个非空的连续子数组。
 * 设计一个算法使得这 k 个子数组各自和的最大值最小。
 *
 * 提示：
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 10^6
 * 1 <= k <= min(50, nums.length)
 */
public class SplitArrayLargestSum_LK410 {
    public int splitArray(int[] nums, int k) {
        // 二分答案法：划分出来的k分数组的累加和可能的范围是0到nums的累加和
        int ans = 0, l = 0, r = 0, m = 0, cur = 0;
        for (int num : nums) {
            r += num;
        }
        while (l <= r) {
            m = l + ((r - l) >> 1);
            // 如果划分数组后和每一部分的和为m的情况下，需要划分为多少份
            cur = getCurK(nums, m);
            if (cur <= k) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public int getCurK(int[] nums, int sum) {
        int ans = 1, curSum = 0;
        for (int num : nums) {
            // 如果当前的单个值已经超过给定的和，那么划分多少分都不行，返回系统最大值
            if (num > sum) {
                return Integer.MAX_VALUE;
            }
            curSum += num;
            // 超过给定的和，就重新划分一份，没超过就继续累加
            if (curSum > sum) {
                ans++;
                curSum = num;
            }
        }
        return ans;
    }
}
