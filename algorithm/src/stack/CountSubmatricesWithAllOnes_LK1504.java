package stack;

import java.util.Arrays;

/**
 * 1504. 统计全 1 子矩形
 * 给你一个 m x n 的二进制矩阵 mat ，请你返回有多少个 子矩形 的元素全部都是 1 。
 *
 * 提示：
 * 1 <= m, n <= 150
 * mat[i][j] 仅包含 0 或 1
 */
public class CountSubmatricesWithAllOnes_LK1504 {

    static final int MAX_LENGTH = 151;

    static final int[] floor = new int[MAX_LENGTH];

    static final int[] stack = new int[MAX_LENGTH];

    static int m, n, r;

    public int numSubmat(int[][] mat) {
        m = mat.length;
        n = mat[0].length;
        Arrays.fill(floor, 0);
        int ans = 0;
        // 考虑必须以第i行的数据为矩形的底的时候，能有多少个全是1元素的子矩阵
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 如果第i行的j位置元素为0，则第i行的j位置是不存在满足条件的矩阵的
                floor[j] = mat[i][j] == 0 ? 0 : floor[j] + 1;
            }
            // 计算必须以第i行为底时满足条件的矩阵有多少个
            ans += compute();
        }
        return ans;
    }

    public int compute() {
        r = 0;
        int ans = 0;
        int cur, len, left, high;
        for (int i = 0; i < n; i++) {
            // 单调栈：严格大压小
            while (r > 0 && floor[i] <= floor[stack[r - 1]]) {
                cur = stack[--r];
                // 相等时直接将前一个位置弹出栈不做计算，只有严格小于栈顶元素才计算
                if (floor[i] < floor[cur]) {
                    left = r == 0 ? -1 : stack[r - 1];
                    len = i - left - 1;
                    // 对于cur位置来说，left位置是左边第一个小于它的数，i位置是右边第一个小于它的数
                    // 所以cur位置能结算left位置和i位置对应的高度的最大值到cur位置的高度之间的数值
                    // 例如：cur高度为7，left高度为3，i高度为5，则cur位置可以结算高度为6和7的矩形个数，长度为i - left - 1
                    high = Math.max(left == -1 ? 0 : floor[left], floor[i]);
                    ans += len * (len + 1) * (floor[cur] - high) / 2;
                }
            }
            stack[r++] = i;
        }
        while (r > 0) {
            cur = stack[--r];
            left = r == 0 ? -1 : stack[r - 1];
            len = n - left - 1;
            high = left == -1 ? 0 : floor[left];
            ans += len * (len + 1) * (floor[cur] - high) / 2;
        }
        return ans;
    }
}
