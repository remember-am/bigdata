package stack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

/**
 * https://www.nowcoder.com/practice/77199defc4b74b24b8ebf6244e1793de
 * 大鱼吃小鱼
 * 描述
 * 小明最近喜欢上了俄罗斯套娃、大鱼吃小鱼这些大的包住小的类型的游戏。于是小明爸爸给小明做了一个特别版的大鱼吃小鱼游戏，他希望通过这个游戏
 * 能够近一步提高牛牛的智商。
 * 游戏规则如下：
 * 现在有N条鱼，每条鱼的体积为Ai，从左到右排成一排。A数组是一个排列。小明每轮可以执行一次大鱼吃小鱼的操作
 * 一次大鱼吃小鱼的操作：对于每条鱼，它在每一次操作时会吃掉右边比自己小的第一条鱼
 * 值得注意的时，在一次操作中，每条鱼吃比自己小的鱼的时候是同时发生的。
 * 举一个例子，假设现在有三条鱼,体积为分别[5，4，3]，5吃4，4吃3，一次操作后就剩下[5]一条鱼。
 * 爸爸问小明，你知道要多少次操作，鱼的数量就不会变了嘛？
 *
 * 输入描述：
 * 给定N；
 * 给定A数组
 * １＜＝N＜＝１０＾５
 * １＜＝Ai＜＝Ｎ
 * 输出描述：
 * 一行, 正整数, 表示要多少次操作，鱼的数量就不会变了。
 *
 *
 */
public class BigFishEatSmallFish_NC {
    static final int MAXN = 100001;

    static final int[] arr = new int[MAXN];

    static final int[][] stack = new int[MAXN][2];

    static int r, n;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (int) st.nval;
            }
            out.println(compute());
        }
        out.flush();
        out.close();
        in.close();
    }

    public static int compute() {
        r = 0;
        int ans = 0;
        for (int i = n - 1, cur; i >= 0; i--) {
            cur = 0;
            while (r > 0 && stack[r - 1][0] < arr[i]) {
                cur = Math.max(cur + 1, stack[r - 1][1]);
                r--;
            }
            stack[r][0] = arr[i];
            stack[r++][1] = cur;
            ans = Math.max(ans, cur);
        }
        return ans;
    }
}
