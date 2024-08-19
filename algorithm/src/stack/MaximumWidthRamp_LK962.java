package stack;

/**
 * 962. 最大宽度坡
 * 给定一个整数数组 A，坡是元组 (i, j)，其中  i < j 且 A[i] <= A[j]。这样的坡的宽度为 j - i。
 * 找出 A 中的坡的最大宽度，如果不存在，返回 0 。
 *
 * 提示：
 * 2 <= A.length <= 50000
 * 0 <= A[i] <= 50000
 */
public class MaximumWidthRamp_LK962 {

    static final int MAX_LENGTH = 50001;

    static final int[] stack = new int[MAX_LENGTH];

    static int r;

    public int maxWidthRamp(int[] nums) {
        r = 0;
        int ans = 0, n = nums.length;
        // 单调栈：0位置一定要放入栈，从0位置开始，寻找nums数组中严格递减的位置，将这些位置加入栈中
        for (int i = 0; i < n; i++) {
            if (r == 0 || nums[i] < nums[stack[r - 1]]) {
                stack[r++] = i;
            }
        }
        // 数组从右往左遍历，遍历到的元素只要大于栈顶元素就计算，计算完后弹出栈顶元素
        for (int i = n - 1; i >= 0 && r > 0; i--) {
            while (r > 0 && nums[i] >= nums[stack[r - 1]]) {
                ans = Math.max(ans, i - stack[--r]);
            }
        }
        return ans;
    }
}
