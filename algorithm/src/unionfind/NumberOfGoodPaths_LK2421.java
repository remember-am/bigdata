package unionfind;

/*
2421. 好路径的数目
给你一棵 n 个节点的树（连通无向无环的图），节点编号从 0 到 n - 1 且恰好有 n - 1 条边。
给你一个长度为 n 下标从 0 开始的整数数组 vals ，分别表示每个节点的值。同时给你一个二维整数数组 edges ，
其中 edges[i] = [ai, bi] 表示节点 ai 和 bi 之间有一条 无向 边。
一条 好路径 需要满足以下条件：
开始节点和结束节点的值 相同 。
开始节点和结束节点中间的所有节点值都 小于等于 开始节点的值（也就是说开始节点的值应该是路径上所有节点的最大值）。
请你返回不同好路径的数目。
注意，一条路径和它反向的路径算作 同一 路径。比方说， 0 -> 1 与 1 -> 0 视为同一条路径。单个节点也视为一条合法路径。

提示：
n == vals.length
1 <= n <= 3 * 10^4
0 <= vals[i] <= 10^5
edges.length == n - 1
edges[i].length == 2
0 <= ai, bi < n
ai != bi
edges 表示一棵合法的树。
 */

import java.util.Arrays;

public class NumberOfGoodPaths_LK2421 {

    static int MAXN = 30001;

    static int[] father = new int[MAXN];

    static int[] maxCnt = new int[MAXN];

    public static void init(int n) {
        for (int i = 0; i < n; i++) {
            father[i] = i;
            // 记录对应集合最大值的个数，初始是每个元素单独一个集合，最大值是自己，个数都是1
            maxCnt[i] = 1;
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static int union(int x, int y, int[] vals) {
        int fx = find(x);  // fx代表x所在的集合的代表元素，也是该集合最大值所在的下标
        int fy = find(y);  // fy代表x所在的集合的代表元素，也是该集合最大值所在的下标
        int goodPath = 0;
        // 两个集合的最大值如果不一致是没法产生好路径的，且需要最大值较小的集合指向最大值较大的集合
        if (vals[fx] > vals[fy]) {
            father[fy] = fx;
        } else if (vals[fx] < vals[fy]) {
            father[fx] = fy;
        } else {
            // 两个集合的最大值都一致，则会产生好路径，好路径的数量等于两个集合的最大值的数量的乘积
            goodPath = maxCnt[fx] * maxCnt[fy];
            // 谁都可以指向谁，但被指向的需要更新最大值的数量
            father[fx] = fy;
            maxCnt[fy] += maxCnt[fx];
        }
        return goodPath;
    }

    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int ans = vals.length;
        init(ans);
        // 边按照两端值中的最大值排序
        Arrays.sort(edges, (a, b) -> Math.max(vals[a[0]], vals[a[1]]) - Math.max(vals[b[0]], vals[b[1]]));
        for (int[] edge : edges) {
            // 每条边的连接的两个点合并
            ans += union(edge[0], edge[1], vals);
        }
        return ans;
    }
}
