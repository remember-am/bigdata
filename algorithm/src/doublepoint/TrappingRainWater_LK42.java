package doublepoint;

/**
 * 42. 接雨水
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * 提示：
 * n == height.length
 * 1 <= n <= 2 * 10^4
 * 0 <= height[i] <= 10^5
 */
public class TrappingRainWater_LK42 {
    // 常规解法，i位置的能接多少的雨水取决于i位置的左半部分的最高柱子和i位置的右半部分的最高柱子，两者取最小值
    public int trap1(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int n = height.length;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = height[0];
        right[n - 1] = height[n - 1];
        // 求左右部分的柱子的最大高度
        for (int i = 1, j = n - 2; i < n && j >= 0; i++, j--) {
            left[i] = Math.max(left[i - 1], height[i]);
            right[j] = Math.max(right[j + 1], height[j]);
        }
        int ans = 0;
        for (int i = 1, cur; i < n - 1; i++) {
            cur = Math.min(left[i - 1], right[i + 1]) - height[i];
            if (cur > 0) {
                ans += cur;
            }
        }
        return ans;
    }

    /**
     * 双指针解法
     * @param height 柱子
     * @return 雨水
     */
    public int trap(int[] height) {
        int ans = 0, l = 1, r = height.length - 2, lmax = height[0], rmax = height[height.length - 1];
        while (l <= r) {
            // lmax比rmax小就能结算l位置的雨水量，rmax比lmax小就能结算r位置的雨水量
            // 假设此时lmax比rmax小，那么应该结算l位置的雨水量。原因在于lmax是l位置左边的已经确定的最大值，而l位置的
            // 右边部分的最大值虽然没有确定一个真实值，但是l位置的右边部分的最大值一定是大于等于rmax的，所以l位置的雨水量
            // 取决于lmax和一个大于等于rmax的值，若lmax小于等于rmax，则当然可以结算l位置的雨水量了。r位置同理
            if (lmax <= rmax) {
                ans += Math.max(0, lmax - height[l]);
                lmax = Math.max(lmax, height[l++]);
            } else {
                ans += Math.max(0, rmax - height[r]);
                rmax = Math.max(rmax, height[r--]);
            }
        }
        return ans;
    }
}
