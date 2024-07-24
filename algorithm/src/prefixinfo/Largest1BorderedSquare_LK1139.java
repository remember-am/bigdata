package prefixinfo;

import java.util.Arrays;

/**
 * 1139. 最大的以 1 为边界的正方形
 * 给你一个由若干 0 和 1 组成的二维网格 grid，请你找出边界全部由 1 组成的最大正方形子网格，并返回该子网格中的元素数量。如果不存在，则返回 0。
 *
 * 提示：
 * 1 <= grid.length <= 100
 * 1 <= grid[0].length <= 100
 * grid[i][j] 为 0 或 1
 */
public class Largest1BorderedSquare_LK1139 {
    public static int largest1BorderedSquare(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // 构造二位前缀和数组
        int[][] prefixSum = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefixSum[i + 1][j + 1] = grid[i][j];
            }
        }
        // 构造二位前缀和数组
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefixSum[i][j] += prefixSum[i - 1][j] + prefixSum[i][j - 1] - prefixSum[i - 1][j - 1];
            }
        }
        // 如果原始数组中都没有出现过1，前缀和数组也都是0，不存在边全是1的正方形
        if (sumRegion(prefixSum, 0, 0, m - 1, n - 1) == 0) {
            return 0;
        }
        // sideLength：已经找到了边长为1的正方形，接下来只需要验证边长为2,3,4...Math.min(m,n)的正方形
        int sideLength = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 枚举以i,j为左上角的所有正方形
                // c,d代表正方形的右下角点，k代表边长
                for (int c = i + sideLength, d = j + sideLength, k = sideLength + 1; c < m && d < n; c++, d++, k++) {
                    if (sumRegion(prefixSum, i, j, c, d) - sumRegion(prefixSum, i + 1, j + 1, c - 1, d - 1) == (k - 1) << 2) {
                        sideLength = k;
                    }
                }
            }
        }
        return sideLength * sideLength;
    }

    // 返回二维前缀和数组中以row1,col1为左上角，row2,col2为右下角的矩形面积
    public static int sumRegion(int[][] prefixSum, int row1, int col1, int row2, int col2) {
        row2++;
        col2++;
        return row1 > row2 ? 0 : prefixSum[row2][col2] - prefixSum[row2][col1] - prefixSum[row1][col2] + prefixSum[row1][col1];
    }
}
