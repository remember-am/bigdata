package unionfind;

/*
2092. 找出知晓秘密的所有专家
给你一个整数 n ，表示有 n 个专家从 0 到 n - 1 编号。另外给你一个下标从 0 开始的二维整数数组 meetings ，
其中 meetings[i] = [xi, yi, timei] 表示专家 xi 和专家 yi 在时间 timei 要开一场会。一个专家可以同时参加 多场会议 。
最后，给你一个整数 firstPerson 。
专家 0 有一个 秘密 ，最初，他在时间 0 将这个秘密分享给了专家 firstPerson 。接着，这个秘密会在每次有知晓这个秘密的专家参加会议时进行传播。
更正式的表达是，每次会议，如果专家 xi 在时间 timei 时知晓这个秘密，那么他将会与专家 yi 分享这个秘密，反之亦然。
秘密共享是 瞬时发生 的。也就是说，在同一时间，一个专家不光可以接收到秘密，还能在其他会议上与其他专家分享。
在所有会议都结束之后，返回所有知晓这个秘密的专家列表。你可以按 任何顺序 返回答案。

提示：
2 <= n <= 10^5
1 <= meetings.length <= 10^5
meetings[i].length == 3
0 <= xi, yi <= n - 1
xi != yi
1 <= timei <= 10^5
1 <= firstPerson <= n - 1
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllPeopleWithSecret_LK2092 {

    static int MAXN = 100001;

    static int[] father = new int[MAXN];

    static boolean[] secret = new boolean[MAXN];

    public static void init(int n, int firstPerson) {
        for (int i = 0; i < n; i++) {
            father[i] = i;
            secret[i] = false;
        }
        // 初始化并查集时，0号专家知晓秘密并且会告诉firstPerson，所以这两个初始时是同一个集合
        father[firstPerson] = 0;
        secret[0] = true;
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
            // 合并x，y集合，把fx指向fy，此时如果fx知晓秘密，则fy会获得fx知晓的秘密
            secret[fy] |= secret[fx];
        }
    }

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        init(n, firstPerson);
        // 会议按时间排序
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
        for (int l = 0, r; l < meetings.length; l = r + 1) {
            r = l;
            // 找到同一时刻的所有会议
            while (r + 1 < meetings.length && meetings[l][2] == meetings[r + 1][2]) {
                r++;
            }
            for (int i = l; i <= r; i++) {
                // l到r范围是同一时刻的所有会议，按照会议合并专家
                union(meetings[i][0], meetings[i][1]);
            }
            // 同一时刻的会议结束，如果不知晓还是不知晓秘密的专家需要重新单独成为一个集合
            for (int i = l, x, y; i <= r; i++) {
                x = meetings[i][0];
                y = meetings[i][1];
                if (!secret[find(x)]) {
                    father[x] = x;
                }
                if (!secret[find(y)]) {
                    father[y] = y;
                }
            }
        }
        // 会议结束，收集答案
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // 这里专家需要找的是所在集合的代表元素
            if (secret[find(i)]) {
                ans.add(i);
            }
        }
        return ans;
    }
}
