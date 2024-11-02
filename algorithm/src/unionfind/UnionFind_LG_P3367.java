package unionfind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.StreamTokenizer;

/*
https://www.luogu.com.cn/problem/P3367
 */

public class UnionFind_LG_P3367 {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        StreamTokenizer st = new StreamTokenizer(in);
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            init();
            for (int i = 0, x, y, z; i < m; i++) {
                st.nextToken(); z = (int) st.nval;
                st.nextToken(); x = (int) st.nval;
                st.nextToken(); y = (int) st.nval;
                if (z == 2) {
                    out.println(isSameSet(x, y) ? "Y" : "N");
                } else {
                    union(x, y);
                }
            }
        }
        out.flush();
        out.close();
        in.close();
    }

    static int MAXN = 10001;

    static int[] father = new int[MAXN];

    static int n, m;

    public static void init() {
        for (int i = 0; i <= n; i++) {
            father[i] = i;
        }
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static boolean isSameSet(int x, int y) {
        return find(x) == find(y);
    }

    public static void union(int x, int y) {
        father[find(x)] = find(y);
    }
}