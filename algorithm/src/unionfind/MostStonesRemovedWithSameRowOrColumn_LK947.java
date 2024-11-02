package unionfind;

/*
947. 移除最多的同行或同列石头
n 块石头放置在二维平面中的一些整数坐标点上。每个坐标点上最多只能有一块石头。
如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头。
给你一个长度为 n 的数组 stones ，其中 stones[i] = [xi, yi] 表示第 i 块石头的位置，返回 可以移除的石子 的最大数量。

提示：
1 <= stones.length <= 1000
0 <= xi, yi <= 10^4
不会有两块石头放在同一个坐标点上
 */

import java.util.HashMap;
import java.util.Map;

public class MostStonesRemovedWithSameRowOrColumn_LK947 {

    static int MAXN = 1001;

    static int[] father = new int[MAXN];

    static int sets;

    // 记录哪些行上有石头
    static Map<Integer, Integer> rowMap = new HashMap<>();

    // 记录哪些列上有石头
    static Map<Integer, Integer> colMap = new HashMap<>();

    public static void init(int n) {
        rowMap.clear();
        colMap.clear();
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
        sets = n;
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

    public int removeStones(int[][] stones) {
        int n = stones.length;
        for (int i = 0, x, y; i < n; i++) {
            x = stones[i][0];
            y = stones[i][0];
            // 如果当前石头的所在的行上已经存在石头，那么可以合并，否则不合并，将当前行记录已存在石头
            if (rowMap.containsKey(x)) {
                union(i, rowMap.get(x));
            } else {
                rowMap.put(x, i);
            }
            // 如果当前石头的所在的列上已经存在石头，那么可以合并，否则不合并，将当前列记录已存在石头
            if (colMap.containsKey(y)) {
                union(i, colMap.get(y));
            } else {
                colMap.put(y, i);
            }
        }
        return n - sets;
    }
}
