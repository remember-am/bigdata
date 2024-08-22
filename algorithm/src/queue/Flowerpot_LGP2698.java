package queue;

import java.io.*;
import java.util.Arrays;

/**
 * https://www.luogu.com.cn/problem/P2698
 * 老板需要你帮忙浇花。给出 N 滴水的坐标，y 表示水滴的高度，x 表示它下落到 x 轴的位置。
 * 每滴水以每秒 1 个单位长度的速度下落。你需要把花盆放在 x 轴上的某个位置，使得从被花盆接着的第 1 滴水开始，到被花盆接着的最后
 * 1 滴水结束，之间的时间差至少为 D。我们认为，只要水滴落到 x 轴上，与花盆的边沿对齐，就认为被接住。给出 N 滴水的坐标和
 * D 的大小，请算出最小的花盆的宽度 W。
 *
 * 输入格式
 * 第一行 2 个整数 N 和 D。
 * 接下来 N 行每行 2 个整数，表示水滴的坐标 (x,y)。
 *
 * 输出格式
 * 仅一行 1 个整数，表示最小的花盆的宽度。如果无法构造出足够宽的花盆，使得在 D 单位的时间接住满足要求的水滴，则输出 −1。
 *
 * 说明/提示
 * 有 4 滴水，(6,3) ，(2,4) ，(4,10) ，(12,15) 。水滴必须用至少 5 秒时间落入花盆。花盆的宽度为= 2 是必须且足够的。把花盆放在
 * x=4…6 的位置，它可以接到 1 和 3 水滴, 之间的时间差为 10−3=7 满足条件。
 *
 * 【数据范围】
 * 40% 的数据：1≤N≤1000 ，1≤D≤2000 。
 * 100% 的数据：1≤N≤10^5 ，1≤D≤10^6 ，0≤x,y≤10^6 。
 */
public class Flowerpot_LGP2698 {

    static final int MAX_LENGTH = 100001;

    static final int[][] arr = new int[MAX_LENGTH][2];

    static final int[] maxDq = new int[MAX_LENGTH];

    static final int[] minDq = new int[MAX_LENGTH];

    static int maxh, maxt, minh, mint, n, d;

    public static void main(String[] args) throws IOException {
        BufferedReader in  = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            d = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken(); arr[i][0] = (int) st.nval;
                st.nextToken(); arr[i][1] = (int) st.nval;
            }
            out.println(compute());
        }
        out.flush();
        out.close();
        in.close();
    }

    public static int compute() {
        // 单调队列：先将数组按x坐标值排序，维护一个滑动窗口以及窗口中的最大值队列和最小值队列
        Arrays.sort(arr, 0, n, (a, b) -> (a[0] - b[0]));
        maxh = maxt = minh = mint = 0;
        int ans = Integer.MAX_VALUE;
        for (int l = 0, r = 0; l < n; l++) {
            // 维护一个滑动窗口，当前窗口是否满足最大值减最小值大于等于限制d，如果不满足则窗口需要向右扩大
            while (r < n && !ok()) {
                push(r++);
            }
            // 上面循环出来需要判断是否越界，不越界就计算答案
            if (ok()) {
                ans = Math.min(ans, arr[r - 1][0] - arr[l][0]);
            }
            // 窗口左边界吐出一个数
            pop(l);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 判断当前窗口是否满足，此时窗口是不需要往右边扩的，所以窗口内要么没有值，要么就一定会有最大最小值
    public static boolean ok() {
        int max = maxh < maxt ? arr[maxDq[maxh]][1] : 0;
        int min = minh < mint ? arr[minDq[minh]][1] : 0;
        return max - min >= d;
    }

    public static void push(int r) {
        while (maxh < maxt && arr[r][1] >= arr[maxDq[maxt - 1]][1]) {
            maxt--;
        }
        maxDq[maxt++] = r;
        while (minh < mint && arr[r][1] <= arr[minDq[mint - 1]][1]) {
            mint--;
        }
        minDq[mint++] = r;
    }

    public static void pop(int l) {
        if (maxh < maxt && l == maxDq[maxh]) {
            maxh++;
        }
        if (minh < mint && l == minDq[minh]) {
            minh++;
        }
    }
}
