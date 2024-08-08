package binarysearch;

import java.io.*;

/**
 * https://www.nowcoder.com/practice/7037a3d57bbd4336856b8e16a9cafd71
 * 机器人跳跃问题
 * 描述
 * 机器人正在玩一个古老的基于DOS的游戏。游戏中有N+1座建筑——从0到N编号，从左到右排列。编号为0的建筑高度为0个单位，编号为i的建筑的高度为H(i)个单位。
 * 起初， 机器人在编号为0的建筑处。每一步，它跳到下一个（右边）建筑。假设机器人在第k个建筑，且它现在的能量值是E, 下一步它将跳到第个k+1建筑。
 * 它将会得到或者失去正比于与H(k+1)与E之差的能量。如果 H(k+1) > E 那么机器人就失去 H(k+1) - E 的能量值，否则它将得到 E - H(k+1) 的能量值。
 * 游戏目标是到达第个N建筑，在这个过程中，能量值不能为负数个单位。现在的问题是机器人以多少能量值开始游戏，才可以保证成功完成游戏？
 *
 * 输入描述：
 * 第一行输入，表示一共有 N 组数据.
 * 第二个是 N 个空格分隔的整数，H1, H2, H3, ..., Hn 代表建筑物的高度
 *
 * 输出描述：
 * 输出一个单独的数表示完成游戏所需的最少单位的初始能量
 *
 * 备注：
 * 数据约束：
 * 1 <= N <= 10^5
 * 1 <= H(i) <= 10^5
 */
public class RobotJump_NC {

    static final int MAX_LENGTH = (int) 1e5 + 1;

    static final int[] h = new int[MAX_LENGTH];

    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            // 二分答案法：通关的初始能量最少为0，最多为所有建筑高度的最大值（当初始能量为所有建筑高度的最大值时，所有移动过程中，能量只会增加不会减少）
            int maxE = 0;
            for (int i = 1; i <= n; i++) {
                st.nextToken();
                h[i] = (int) st.nval;
                maxE = Math.max(maxE, h[i]);
            }
            // 方法一：二分答案法，从0到maxE范围内寻找最小满足通关的能量
            out.println(needMinE1(maxE));
            // 方法二：倒推：
            //        记当前能量为e，跳到下一个h(k+1)建筑后能量记为newE，则有
            //        1、当h(k+1)>e时，newE=e-(h(k+1)-e) --> newE=2e-h(k+1) --> e=(h(k+1)+newE+1)/2，加1是为了向上取整
            //        2、当h(k+1)<=e时，newE=e+(e-h(k+1)) --> newE=2e-h(k+1) --> e=(h(k+1)+newE+1)/2，加1是为了向上取整
            //        由上述可知，当机器人走到最后的建筑时能量恰好为0，以此倒推则就能找到初始最小能量值
            // out.println(needMinE2());
        }
        out.flush();
        out.close();
        in.close();
    }

    public static int needMinE1(int maxE) {
        // 二分答案法：maxE的能量一定能通关，先记答案为maxE，再从0到maxE-1范围内二分寻找其它答案
        int ans = maxE, l = 0, r = maxE - 1, m = 0;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            // 初始能量值为m时能不能走到最后的建筑上，能说明找到一个可行方案
            if (isPass(m, maxE)) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    public static boolean isPass(int e, int maxE) {
        for (int i = 1; i <= n; i++) {
            // 按照题意直接计算新的能量值
            if (h[i] > e) {
                e -= h[i] - e;
            } else {
                e += e - h[i];
            }
            // maxE的能量一定能通关，若当前能量值已经大于等于maxE，则必定能通关
            if (e >= maxE) {
                return true;
            }
            // 如果当前能量已经小于0了，则不能通关
            if (e < 0) {
                return false;
            }
        }
        return true;
    }

    public static int needMinE2() {
        int ans = 0;
        // 走到最后的建筑能量恰好为0，以上面所推的公式进行倒推，即可得到初始时所需的最小能量值
        for (int i = n; i > 0; i--) {
            ans = (h[i] + ans + 1) / 2;
        }
        return ans;
    }
}
