package diffarray;

import java.io.*;

/**
 * https://www.luogu.com.cn/problem/P4231
 * 问题摘要：
 * N个柱子排成一排，一开始每个柱子损伤度为0。接下来勇仪会进行M次攻击，每次攻击可以用4个参数l,r,s,e来描述：
 * 表示这次攻击作用范围为第l个到第r个之间所有的柱子(包含l,r)，对第一个柱子的伤害为s，对最后一个柱子的伤害为e。
 * 攻击产生的伤害值是一个等差数列。若l=1,r=5,s=2,e=10，则对第1~5个柱子分别产生2,4,6,8,10的伤害。
 * 鬼族们需要的是所有攻击完成之后每个柱子的损伤度。
 *
 * 输入格式
 * 第一行2个整数N,M，用空格隔开，下同。
 * 接下来M行，每行4个整数l,r,s,e，含义见题目描述。
 * 数据保证对每个柱子产生的每次伤害值都是整数。
 *
 * 输出格式
 * 由于输出数据可能过大无法全部输出，为了确保你真的能维护所有柱子的损伤度，只要输出它们的异或和与最大值即可。
 *
 * 对于全部的数据:1⩽n⩽10^7，1⩽m⩽3×10^5，1⩽l<r⩽n.
 * 所有输入输出数据以及柱子受损伤程度始终在[0,9×10^18]范围内。
 */
public class ArithmeticSequenceDifference_LGP4231 {

    static final int MAX_LENGTH = (int) 1e7 + 10;

    static final long[] arr = new long[MAX_LENGTH];

    static int n, m;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            m = (int) st.nval;
            for (int i = 0, l, r, s, e, d; i < m; i++) {
                st.nextToken(); l = (int) st.nval;
                st.nextToken(); r = (int) st.nval;
                st.nextToken(); s = (int) st.nval;
                st.nextToken(); e = (int) st.nval;
                d = (e - s) / (r - l);
                set(l, r, s, e, d);
            }
            build();
            long max = 0, xor = 0;
            for (int i = 1; i <= n; i++) {
                max = Math.max(max, arr[i]);
                xor ^= arr[i];
            }
            out.println(xor + " " + max);
        }
        out.flush();
        out.close();
        in.close();
    }

    /**
     * 差分数据操作模板，数组l位置加等于s，数组l+1位置加等于（d-s），数组r+1位置减等于（d+e），数组r+2位置加等于e
     * @param l 数组l位置
     * @param r 数组r位置
     * @param s 等差数列的起始值
     * @param e 等差数列的末尾值
     * @param d 等差数列的等差值
     */
    public static void set(int l, int r, int s, int e, int d) {
        arr[l] += s;
        arr[l + 1] += d - s;
        arr[r + 1] -= d + e;
        arr[r + 2] += e;
    }

    /**
     * 差分数组进行两次的前缀和求值，即可得到一组操作后该数组的对应结果
     */
    public static void build() {
        for (int i = 1; i <= n; i++) {
            arr[i] += arr[i - 1];
        }
        for (int i = 1; i <= n; i++) {
            arr[i] += arr[i - 1];
        }
    }
}
