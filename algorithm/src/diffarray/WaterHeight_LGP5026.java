package diffarray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

/**
 * https://www.luogu.com.cn/problem/P5026
 * 我们把山顶上的湖泊看作一条长度为 m 的直线，一开始水深都在水平线上，我们视作此时的水深为 '0'
 * 接下来，在一瞬间，小正方形的"朋友"们跳起并扎入水中，导致在入水点的水降低而远离入水点的水升高，注意两个 "朋友" 可能在同一地点入水。
 * 小正方形的每个朋友有一个体积数值 v，当体积为 v 的一个朋友跳入水中，我们设入水点为 i，将会导致 i−v+1 到 i 的水位依次降低 1,2,⋯,v
 * 同样地，第 i 到 i+v−1 的水位会依次降低 v,v−1,⋯,1.
 * 相对应地，i−v 的水位不变， i−v−1 到 i−2*v 水位依次增加 1,2,⋯,v,i−2*v 到 i−3*v+1 水位依次增加 v,v−1,⋯,1
 * 同样，i+v 水位不变，i+v+1 到 i+2*v 水位增加 1,2,⋯,v,i+2*v 到 i+3*v−1 水位依次增加 v,v−1,⋯,1
 * 现在小正方形想要穿过这个湖，他想要知道在这 n 个"朋友"跳入水中后湖上每个节点的水位，你能帮帮它吗？
 *
 * 输入格式
 * 第一行为两个整数 n,m，表示"朋友"的数目与湖泊的宽度。
 * 接下来 n 行，一行两个整数 v,x，表示第 i+1 个朋友的体积与入水点。
 *
 * 输出格式
 * 一行 m 个整数，第 i 个整数表示 i 号位的水深。
 *
 * 说明提示
 * n <= 10^6, m <= 10^6, 1 <= v <= 10000, 1 <= x <= m
 */
public class WaterHeight_LGP5026 {

    static final int OFFSET = (int) 3e4 + 1;

    static final int MAX_LENGTH = (int) 1e6 + 1;

    static final int[] arr = new int[MAX_LENGTH + 2 * OFFSET];

    static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            for (int i = 0, v, x; i < n; i++) {
                st.nextToken(); v = (int) st.nval;
                st.nextToken(); x = (int) st.nval;
                operation(v, x);
            }
            build();
            int start = OFFSET + 1;
            out.print(arr[start++]);
            for (int i = 1; i < m; i++) {
                out.print(" " + arr[start++]);
            }
            out.println();
        }
        out.flush();
        out.close();
        in.close();
    }

    // 核心点在于每一个体积v下降都有四个等差数列需要计算
    public static void operation(int v, int x) {
        set(x - 3 * v + 1, x - 2 * v, 1, v, 1);
        set(x - 2 * v + 1, x, v - 1, -v, -1);
        set(x + 1, x + 2 * v, -v + 1, v, 1);
        set(x + 2 * v + 1, x + 3 * v - 1, v - 1, 1, -1);
    }

    public static void set(int l, int r, int s, int e, int d) {
        arr[l + OFFSET] += s;
        arr[l + 1 + OFFSET] += d - s;
        arr[r + 1 + OFFSET] -= d + e;
        arr[r + 2 + OFFSET] += e;
    }

    public static void build() {
        for (int i = 1; i <= m + OFFSET; i++) {
            arr[i] += arr[i - 1];
        }
        for (int i = 1; i <= m + OFFSET; i++) {
            arr[i] += arr[i - 1];
        }
    }
}
