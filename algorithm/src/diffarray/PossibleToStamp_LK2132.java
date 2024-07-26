package diffarray;

/**
 * 2132. 用邮票贴满网格图
 * 给你一个 m x n 的二进制矩阵 grid ，每个格子要么为 0 （空）要么为 1 （被占据）。
 * 给你邮票的尺寸为 stampHeight x stampWidth 。我们想将邮票贴进二进制矩阵中，且满足以下 限制 和 要求 ：
 *      覆盖所有 空 格子。
 *      不覆盖任何 被占据 的格子。
 *      我们可以放入任意数目的邮票。
 *      邮票可以相互有 重叠 部分。
 *      邮票不允许 旋转 。
 *      邮票必须完全在矩阵 内 。
 * 如果在满足上述要求的前提下，可以放入邮票，请返回 true ，否则返回 false 。
 *
 * 提示：
 * m == grid.length
 * n == grid[r].length
 * 1 <= m, n <= 10^5
 * 1 <= m * n <= 2 * 10^5
 * grid[r][c] 要么是 0 ，要么是 1 。
 * 1 <= stampHeight, stampWidth <= 10^5
 */
public class PossibleToStamp_LK2132 {
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        int m = grid.length;
        int n = grid[0].length;
        // 二维前缀和数组
        int[][] prefix = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefix[i + 1][j + 1] = grid[i][j];
            }
        }
        build(prefix);
        // 二维差分数组
        int[][] diff = new int[m + 2][n + 2];
        for (int i = 1, c = i + stampHeight - 1; c <= m; i++, c++) {
            for (int j = 1, d = j + stampWidth - 1; d <= n; j++, d++) {
                // 如果满足贴邮票，就往差分数组中操作一次
                if (sumRegion(prefix, i, j, c, d) == 0) {
                    add(diff, i, j, c, d, 1);
                }
            }
        }
        // 加工差分数组
        build(diff);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 差分数组中位置为0说明该区域没有贴邮票，原属数组中为0说明需要贴邮票
                if (grid[i][j] == 0 && diff[i + 1][j + 1] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // 构建二维前缀和数组
    public void build(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[0].length; j++) {
                arr[i][j] += arr[i - 1][j] + arr[i][j - 1] - arr[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int[][] prefix, int row1, int col1, int row2, int col2) {
        return prefix[row2][col2] - prefix[row2][col1 - 1] - prefix[row1 - 1][col2] + prefix[row1 - 1][col1 - 1];
    }

    public void add(int[][] diff, int row1, int col1, int row2, int col2, int val) {
        diff[row1][col1] += val;
        diff[row1][col2 + 1] -= val;
        diff[row2 + 1][col1] -= val;
        diff[row2 + 1][col2 + 1] += val;
    }
}
