package recursion;

/*
827. 最大人工岛
给你一个大小为 n x n 二进制矩阵 grid 。最多 只能将一格 0 变成 1 。
返回执行此操作后，grid 中最大的岛屿面积是多少？
岛屿 由一组上、下、左、右四个方向相连的 1 形成。

提示：
n == grid.length
n == grid[i].length
1 <= n <= 500
grid[i][j] 为 0 或 1
 */

import java.util.HashMap;
import java.util.Map;

public class MakingALargeIsland_LK827 {

    int n;

    int[][] grid;

    boolean[] direction;

    static Map<Integer, Integer> map = new HashMap<>();

    public int dfs(int i, int j, int val) {
        if (i < 0 || i >= n || j < 0 || j >= n || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = val;
        return 1 + dfs(i - 1, j, val) + dfs(i + 1, j, val) + dfs(i, j - 1, val) + dfs(i, j + 1, val);
    }

    public int connect(int i, int j) {
        int res = 1;
        // 找到四个方向上对应的值，如果值不为0，则一定是岛屿
        int up = i >= 1 ? grid[i - 1][j] : 0;
        int down = i < n - 1 ? grid[i + 1][j] : 0;
        int left = j >= 1 ? grid[i][j - 1] : 0;
        int right = j < n - 1 ? grid[i][j + 1] : 0;
        // 如果当前方向上的岛屿没有被连接过，则加上当前方向上岛屿的大小
        if (!direction[up]) {
            res += map.getOrDefault(up, 0);
            direction[up] = true;
        }
        if (!direction[down]) {
            res += map.getOrDefault(down, 0);
            direction[down] = true;
        }
        if (!direction[left]) {
            res += map.getOrDefault(left, 0);
            direction[left] = true;
        }
        if (!direction[right]) {
            res += map.getOrDefault(right, 0);
            direction[right] = true;
        }
        direction[up] = false;
        direction[down] = false;
        direction[left] = false;
        direction[right] = false;
        return res;
    }

    public int largestIsland(int[][] grid) {
        n = grid.length;
        this.grid = grid;
        map.clear();
        int ans = 0;
        int val = 2;
        for (int i = 0; i < n; i++) {
            for (int j = 0, cnt; j < n; j++) {
                if (grid[i][j] == 1) {
                    // 将当前i,j所在的岛屿值变成val，并且返回当前i,j所在岛屿的大小，即当前连成一片1的个数
                    cnt = dfs(i, j, val);
                    ans = Math.max(ans, cnt);
                    // 缓存当前岛屿大小
                    map.put(val, cnt);
                    val++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    // 上一步中已经把每个单独的岛屿都赋予了一个val值，现在考虑每个0位置，将当前i,j位置的0如果变成1能连接的最大岛屿面积
                    // 当前i,j位置如果变成1，那么考虑当前位置的上下左右四个方向，如果这四个方向都有对应的岛屿，则从map中加上各自岛屿的值
                    // 需要注意的是，可能四个方向中，有不止一个方向连接的是同一个岛屿，需要去重
                    ans = Math.max(ans, connect(i, j));
                }
            }
        }
        return ans;
    }
}
