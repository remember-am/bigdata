package unionfind;

/*
685. 冗余连接 II
在本问题中，有根树指满足以下条件的 有向 图。该树只有一个根节点，所有其他节点都是该根节点的后继。该树除了根节点之外的每一个节点都有且只有一个父节点，而根节点没有父节点。
输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。附加的边包含在 1 到 n 中的两个不同顶点间，这条附加的边不属于树中已存在的边。
结果图是一个以边组成的二维数组 edges 。 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 vi 的一个父节点。
返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。

提示：
n == edges.length
3 <= n <= 1000
edges[i].length == 2
1 <= ui, vi <= n
 */

import java.util.ArrayList;
import java.util.List;

public class RedundantConnectionII_LK685 {

    static int MAXN = 1001;

    static int[] father = new int[MAXN];

    static int[] indegree = new int[MAXN];

    static int n;

    public static void init() {
        // 此处注意节点的值是1到n
        for (int i = 1; i <= n; i++) {
            father[i] = i;
            indegree[i] = 0;
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        father[find(x)] = find(y);
    }

    public static boolean isTreeAfterRemoveEdge(int[][] edges, int no) {
        init();
        for (int i = 0, u, v; i < n; i++) {
            // no下标对应的是需要删除的边
            if (i == no) {
                continue;
            }
            u = edges[i][0];
            v = edges[i][1];
            // 出现了环一定不是答案
            if (find(u) == find(v)) {
                return false;
            }
            union(u, v);
        }
        return true;
    }

    public static int[] getRemoveEdge(int[][] edges) {
        init();
        for (int i = 0, u, v; i < n; i++) {
            u = edges[i][0];
            v = edges[i][1];
            if (find(u) == find(v)) {
                return edges[i];
            }
            union(u, v);
        }
        return new int[]{};
    }

    public int[] findRedundantDirectedConnection(int[][] edges) {
        n = edges.length;
        for (int i = 0; i < n; i++) {
            // 统计每个节点的入度
            indegree[edges[i][1]]++;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // 存在入度为2的点，则把对应的边收集进入集合，删除集合中的某一条如果是有根树，那么就返回删除的那条边
            if (indegree[edges[i][1]] == 2) {
                list.add(i);
            }
        }
        // 是否存在入度为2的点
        if (list.size() == 2) {
            // 从后往前删除，因为可能删除两条边都符合要求，答案要求返回靠后的那条边
            if (isTreeAfterRemoveEdge(edges, list.get(1))) {
                return edges[list.get(1)];
            }
            return edges[list.get(0)];
        }
        // 如果不存在入度为2的点，则说明该图存在有向循环，把最后使得图存在有向环的边返回即可
        return getRemoveEdge(edges);
    }
}
