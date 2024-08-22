package queue;

/**
 * 862. 和至少为 K 的最短子数组
 * 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。如果不存在这样的 子数组 ，返回 -1 。
 * 子数组 是数组中 连续 的一部分。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -105 <= nums[i] <= 10^5
 * 1 <= k <= 109
 */
public class ShortestSubarrayWithSumAtLeastK_LK862 {

    static final int MAX_LENGTH = 100001;

    static final int[] deque = new int[MAX_LENGTH];

    static final long[] preSum = new long[MAX_LENGTH];

    static int h, t;

    public int shortestSubarray(int[] nums, int k) {
        // 考虑以i位置结尾的子数组和至少为k需要往左扩几个位置
        // 1、求取数组前缀和，前缀和数组i位置代表的是前i个数的前缀和，例如前0个数和是0，前1个数和是preSum[1]...
        for (int i = 0; i < nums.length; i++) {
            preSum[i + 1] = preSum[i] + nums[i];
        }
        int ans = Integer.MAX_VALUE;
        h = t = 0;
        for (int i = 0; i <= nums.length; i++) {
            // 当前i个数的和是preSum[i]，如果x个数到i个数的和是大于等于k的，那么只需要找到从0个数到x个数中，前缀和是小于k的并且和尽可能的大
            // 如果当前i位置的前缀和减去队列头部的前缀和大于等于k，则可以计算答案
            while (h < t && preSum[i] - preSum[deque[h]] >= k) {
                ans = Math.min(ans, i - deque[h++]);
            }
            // 单调队列严格小压大
            while (h < t && preSum[i] <= preSum[deque[t - 1]]) {
                t--;
            }
            deque[t++] = i;
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
