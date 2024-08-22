package queue;

/**
 * 239. 滑动窗口最大值
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。
 * 滑动窗口每次只向右移动一位。 返回 滑动窗口中的最大值 。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -104 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 */
public class SlidingWindowMaximum_LK239 {

    static final int MAX_LENGTH = 100001;

    static final int[] queue = new int[MAX_LENGTH];

    // 单调队列的头和尾
    static int h, t;

    public int[] maxSlidingWindow(int[] nums, int k) {
        h = t = 0;
        // 先形成k-1的窗口
        for (int i = 0; i < k - 1; i++) {
            // 在单调队列里维护窗口中的最大值
            while (h != t && nums[i] >= nums[queue[t - 1]]) {
                t--;
            }
            queue[t++] = i;
        }
        int[] ans = new int[nums.length - k + 1];
        // 从第k个元素开始遍历
        for (int l = 0, r = k - 1; r < nums.length; l++, r++) {
            while (h != t && nums[r] >= nums[queue[t - 1]]) {
                t--;
            }
            queue[t++] = r;
            // 单调队列头部的元素永远是当前窗口的最大值
            ans[l] = nums[queue[h]];
            // 判断当前窗口中的最大值是否应该随窗口过期
            if (l == queue[h]) {
                h++;
            }
        }
        return ans;
    }
}
