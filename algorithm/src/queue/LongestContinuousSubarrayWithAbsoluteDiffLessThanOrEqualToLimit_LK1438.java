package queue;

/**
 * 1438. 绝对差不超过限制的最长连续子数组
 * 给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
 * 如果不存在满足条件的子数组，则返回 0 。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 0 <= limit <= 10^9
 */
public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit_LK1438 {

    static final int MAX_LENGTH = 100001;

    static final int[] maxDq = new int[MAX_LENGTH];

    static final int[] minDq = new int[MAX_LENGTH];

    static int maxh, maxt, minh, mint;

    public int longestSubarray(int[] nums, int limit) {
        // 单调队列：分别维护一个窗口的最大值队列和最小值队列
        maxh = maxt = minh = mint = 0;
        int ans = 0, n = nums.length;
        for (int l = 0, r = 0; l < n; l++) {
            // 将r位置添加进队列是否还满足最大值减最小值的结果大于等于limit
            // 如果还是满足，则r位置可以进队列
            while (r < n && ok(nums, limit, nums[r])) {
                push(nums, r++);
            }
            // 从上面循环出来，r位置要么是越界要么是不满足当前窗口的限制，所以当前l,l+1,l+2...r-2,r-1的窗口是满足条件的，计算答案
            ans = Math.max(ans, r - l);
            // 将窗口左边界吐出一个数
            pop(nums, l);
        }
        return ans;
    }

    // r位置的数如果进入窗口是否满足条件
    public boolean ok(int[] nums, int limit, int num) {
        int max = maxh < maxt ? Math.max(nums[maxDq[maxh]], num) : num;
        int min = minh < mint ? Math.min(nums[minDq[minh]], num) : num;
        return max - min <= limit;
    }

    // r位置进进窗口
    public void push(int[] nums, int r) {
        while (maxh < maxt && nums[r] >= nums[maxDq[maxt - 1]]) {
            maxt--;
        }
        maxDq[maxt++] = r;
        while (minh < mint && nums[r] <= nums[minDq[mint - 1]]) {
            mint--;
        }
        minDq[mint++] = r;
    }

    // l位置出窗口
    public void pop(int[] nums, int l) {
        if (maxh < maxt && l == maxDq[maxh]) {
            maxh++;
        }
        if (minh < mint && l == minDq[minh]) {
            minh++;
        }
    }
}
