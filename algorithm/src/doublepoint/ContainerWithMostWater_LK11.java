package doublepoint;

/**
 * 11. 盛最多水的容器
 * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 *
 * 提示：
 * n == height.length
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 */
public class ContainerWithMostWater_LK11 {
    public int maxArea(int[] height) {
        // 滑动窗口：一开始窗口左边界在0位置，窗口右边界在n-1位置，计算当前窗口的值，窗口左右两侧谁小谁就往里缩
        int ans = 0, l = 0, r = height.length - 1;
        while (l < r) {
            ans = Math.max(ans, (r - l) * (height[l] <= height[r] ? height[l++] : height[r--]));
        }
        return ans;
    }
}
