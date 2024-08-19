package stack;

/**
 * 2289. 使数组按非递减顺序排列
 * 给你一个下标从 0 开始的整数数组 nums 。在一步操作中，移除所有满足 nums[i - 1] > nums[i] 的 nums[i] ，其中 0 < i < nums.length 。
 * 重复执行步骤，直到 nums 变为 非递减 数组，返回所需执行的操作数。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 */
public class StepsToMakeArrayNonDecreasing_LK2289 {

    static final int MAX_LENGTH = 100001;

    static final int[][] stack = new int[MAX_LENGTH][2];

    static int r;

    public int totalSteps(int[] nums) {
        r = 0;
        int ans = 0;
        for (int i = nums.length - 1, cur; i >= 0; i--) {
            cur = 0;
            while (r > 0 && stack[r - 1][0] < nums[i]) {
                cur = Math.max(cur + 1, stack[r - 1][1]);
                r--;
            }
            stack[r][0] = nums[i];
            stack[r++][1] = cur;
            ans = Math.max(ans, cur);
        }
        return ans;
    }
}
