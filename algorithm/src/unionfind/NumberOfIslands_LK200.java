package unionfind;

/*
200. 岛屿数量
给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
此外，你可以假设该网格的四条边均被水包围。

提示：
m == grid.length
n == grid[i].length
1 <= m, n <= 300
grid[i][j] 的值为 '0' 或 '1'
 */

public class NumberOfIslands_LK200 {

    static int MAXN = 90001;

    static int[] father = new int[MAXN];

    static int sets, col;

    public static void init(int n, int m, char[][] grid) {
        col = m;
        sets = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0, index; j < m; j++) {
                // 初始化并查集，i，j位置对应的位置位getIndex(i, j)
                if (grid[i][j] == '1') {
                    index = getIndex(i, j);
                    father[index] = index;
                    sets++;
                }
            }
        }
    }

    // 二位矩阵的坐标摊平成一维
    public static int getIndex(int i, int j) {
        return i * col + j;
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
            sets--;
        }
    }

    public int numIslands(char[][] grid) {
        // 并查集做法，初始时每个1都是一个集合，满足相邻条件就合并
        int n = grid.length;
        int m = grid[0].length;
        init(n, m, grid);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    if (i > 0 && grid[i - 1][j] == '1') {
                        union(getIndex(i - 1, j), getIndex(i, j));
                    }
                    if (j > 0 && grid[i][j - 1] == '1') {
                        union(getIndex(i, j - 1), getIndex(i, j));
                    }
                }
            }
        }
        return sets;
    }
}
