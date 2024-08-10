package stack;

/**
 * 84. 柱状图中最大的矩形
 * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 * 提示：
 * 1 <= heights.length <=10^5
 * 0 <= heights[i] <= 10^4
 */
public class LargestRectangleInHistogram_LK84 {

    static final int MAX_LENGTH = 100001;

    static final int[] stack = new int[MAX_LENGTH];

    static int r, n;

    public int largestRectangleArea(int[] heights) {
        // 严格单调栈（大压小）：考虑矩形必须以i位置的元素为高度的的情况下，最大的面积是多少
        // 则转化为求i位置的左右两边第一个比i位置小的数
        n = heights.length;
        r = 0;
        int ans = 0, cur, left;
        for (int i = 0; i < n; i++) {
            // 相等情况下也弹出
            while (r > 0 && heights[i] <= heights[stack[r - 1]]) {
                cur = stack[--r];
                left = r > 0 ? stack[r - 1] : -1;
                // 弹出则计算必须以当前弹出元素做高度的矩形的最大面积
                ans = Math.max(ans, (i - left - 1) * heights[cur]);
            }
            stack[r++] = i;
        }
        // 此时栈中元素严格单调递增，则每一个元素的右边边界都是n，左边边界就是它压着的数
        while (r > 0) {
            cur = stack[--r];
            left = r > 0 ? stack[r - 1] : -1;
            ans = Math.max(ans, (n - left - 1) * heights[cur]);
        }
        return ans;
    }
}
