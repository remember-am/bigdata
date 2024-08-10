package stack;

import java.util.Arrays;

/**
 * 85. 最大矩形
 * 给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 *
 * 提示：
 * rows == matrix.length
 * cols == matrix[0].length
 * 1 <= row, cols <= 200
 * matrix[i][j] 为 '0' 或 '1'
 */
public class MaximalRectangle_LK85 {

    static final int MAX_LENGTH = 201;

    static final int[] stack = new int[MAX_LENGTH];

    static final int[] floor = new int[MAX_LENGTH];

    static int r, m, n;

    public int maximalRectangle(char[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        Arrays.fill(floor, 0);
        int ans = 0;
        // 84题的变种，84题为单行数组，此处只需要变种将二维数组每一行都看作是矩形的底就行
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                floor[j] = matrix[i][j] == '0' ? 0 : floor[j] + 1;
            }
            // 计算必须以第i行为底时，全为1的矩形面积有多大
            ans = Math.max(ans, calculate());
        }
        return ans;
    }

    public int calculate() {
        r = 0;
        int ans = 0, cur, left;
        for (int i = 0; i < n; i++) {
            while (r > 0 && floor[i] <= floor[stack[r - 1]]) {
                cur = stack[--r];
                left = r > 0 ? stack[r - 1] : -1;
                ans = Math.max(ans, (i - left - 1) * floor[cur]);
            }
            stack[r++] = i;
        }
        while (r > 0) {
            cur = stack[--r];
            left = r > 0 ? stack[r - 1] : -1;
            ans = Math.max(ans, (n - left - 1) * floor[cur]);
        }
        return ans;
    }
}
