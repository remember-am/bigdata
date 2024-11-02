package unionfind;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;

/*
描述
https://www.nowcoder.com/practice/e7ed657974934a30b2010046536a5372
给定一个没有重复值的整形数组arr，初始时认为arr中每一个数各自都是一个单独的集合。请设计一种叫UnionFind的结构，并提供以下两个操作。
boolean isSameSet(int a, int b): 查询a和b这两个数是否属于一个集合
void union(int a, int b): 把a所在的集合与b所在的集合合并在一起，原本两个集合各自的元素以后都算作同一个集合
[要求]
如果调用isSameSet和union的总次数逼近或超过O(N)，请做到单次调用isSameSet或union方法的平均时间复杂度为O(1)
输入描述：
第一行两个整数N, M。分别表示数组大小、操作次数
接下来M行，每行有一个整数opt
若opt = 1，后面有两个数x, y，表示查询(x, y)这两个数是否属于同一个集合
若opt = 2，后面有两个数x, y，表示把x, y所在的集合合并在一起
输出描述：
对于每个opt = 1的操作，若为真则输出"Yes"，否则输出"No"
 */

public class UnionFind_NC {

    static int MAXN = 1000001;

    static int[] father = new int[MAXN];

    static int[] size = new int[MAXN];

    static int[] stack = new int[MAXN];

    static int n, m;

    public static void init() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
            size[i] = 1;
        }
    }

    public static int find(int i) {
        int size = 1;
        while (i != father[i]) {
            stack[size++] = i;
            i = father[i];
        }
        // 路径压缩，扁平化
        while (size > 0) {
            father[stack[--size]] = i;
        }
        return i;
    }

    public static boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        // 小挂大优化
        if (fx != fy) {
            if (size[fx] >= size[fy]) {
                father[fy] = fx;
                size[fx] += size[fy];
            } else {
                father[fx] = fy;
                size[fy] += size[fx];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        StreamTokenizer st = new StreamTokenizer(in);
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            init();
            for (int i = 0, opt, x, y; i < m; i++) {
                st.nextToken(); opt = (int) st.nval;
                st.nextToken(); x = (int) st.nval;
                st.nextToken(); y = (int) st.nval;
                if (opt == 1) {
                    out.println(isSameSet(x, y) ? "Yes" : "No");
                } else {
                    union(x, y);
                }
            }
        }
        out.flush();
        out.close();
        in.close();
    }
}
