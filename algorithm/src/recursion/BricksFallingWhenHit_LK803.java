package recursion;

/*
803. 打砖块
有一个 m x n 的二元网格 grid ，其中 1 表示砖块，0 表示空白。砖块 稳定（不会掉落）的前提是：
一块砖直接连接到网格的顶部，或者至少有一块相邻（4 个方向之一）砖块 稳定 不会掉落时
给你一个数组 hits ，这是需要依次消除砖块的位置。每当消除 hits[i] = (rowi, coli) 位置上的砖块时，
对应位置的砖块（若存在）会消失，然后其他的砖块可能因为这一消除操作而 掉落 。
一旦砖块掉落，它会 立即 从网格 grid 中消失（即，它不会落在其他稳定的砖块上）。
返回一个数组 result ，其中 result[i] 表示第 i 次消除操作对应掉落的砖块数目。
注意，消除可能指向是没有砖块的空白位置，如果发生这种情况，则没有砖块掉落。

提示：
m == grid.length
n == grid[i].length
1 <= m, n <= 200
grid[i][j] 为 0 或 1
1 <= hits.length <= 4 * 10^4
hits[i].length == 2
0 <= xi <= m - 1
0 <= yi <= n - 1
所有 (xi, yi) 互不相同
 */

public class BricksFallingWhenHit_LK803 {

    static int m, n;

    public static int dfs(int[][] grid, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = 2;
        return 1 + dfs(grid, i - 1, j) + dfs(grid, i + 1, j) + dfs(grid, i, j - 1) + dfs(grid, i, j + 1);
    }

    public int[] hitBricks(int[][] grid, int[][] hits) {
        m = grid.length;
        n = grid[0].length;
        // 初始时将所有需要消除的位置都减1
        for (int[] hit : hits) {
            grid[hit[0]][hit[1]]--;
        }
        // 网格的顶部如果有1就把所有能连到的1都变成2
        for (int j = 0; j < n; j++) {
            if (grid[0][j] == 1) {
                dfs(grid, 0, j);
            }
        }
        int l = hits.length;
        int[] ans = new int[l];
        // 时光回溯，需要消除的位置从后往前看
        for (int k = l - 1, i, j; k >= 0; k--) {
            i = hits[k][0];
            j = hits[k][1];
            // 把需要消除的地方减去的1加回去
            grid[i][j]++;
            // 如果加回去当前的位置是1则说明当前位置是一个有效的消除位置，小于1则代表无效消除位置
            // 如果当前位置是1，则如果它的上下左右四个方向只要出现2，则代表之前是会连到网格顶部的，将当前位置1进行洪水填充，能到的所有1的格子
            // 都该为2，并且记录除了自身还能找到多少个1即是当前hit对应的答案
            // 如果它的上下左右没有2但是它本身处于网格顶部，那也值得洪水填充
            if (grid[i][j] == 1 &&
                    (i == 0 || (grid[i - 1][j] == 2) ||
                    (i < m - 1 && grid[i + 1][j] == 2) ||
                    (j > 0 && grid[i][j - 1] == 2) ||
                    (j < n - 1 && grid[i][j + 1] == 2))) {
                ans[k] = dfs(grid, i, j);
            }
        }
        return ans;
    }
}
