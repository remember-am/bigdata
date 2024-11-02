package unionfind;

/*
765. 情侣牵手
n 对情侣坐在连续排列的 2n 个座位上，想要牵到对方的手。
人和座位由一个整数数组 row 表示，其中 row[i] 是坐在第 i 个座位上的人的 ID。情侣们按顺序编号，第一对是 (0, 1)，
第二对是 (2, 3)，以此类推，最后一对是 (2n-2, 2n-1)。
返回 最少交换座位的次数，以便每对情侣可以并肩坐在一起。 每次交换可选择任意两人，让他们站起来交换座位。

提示:
2n == row.length
2 <= n <= 30
n 是偶数
0 <= row[i] < 2n
row 中所有元素均无重复
 */

public class CouplesHoldingHands_LK765 {

    public int minSwapsCouples(int[] row) {
        // 1、明确一个结论：如果有x对情侣弄混在一起，那么至少需要交换x-1次情侣才能挨着
        // 2、实际只需要关注情侣对的编号是否一致，每对情侣最开始都是一个集合，如果两个挨着的偶数位和奇数位的情侣编号不一致，则说明需要合并
        // 例如：row = [0,2,1,3]，则0，2，1，3的情侣编号分别为0，1，0，1
        // 此时第一个偶数位和奇数位的情侣编号为0和1，不是同一队，需要合并，则把0和1所在的集合合并
        int n = row.length;
        init(n / 2);
        for (int i = 0; i < n; i += 2) {
            // 第i位是偶数位置，第i+1位是奇数位置，这两个位置的情侣需要合并
            union(row[i] / 2, row[i + 1] / 2);
        }
        // 假设合并完之后的集合是3个，假设第一个集合情侣对数量是a，第二个是b，第三个是c，那么最终答案是(a-1)+(b-1)+(c-1)
        // a+b+c就是总共的情侣对数，3就是合并完之后集合数量；
        return n / 2 - sets;
    }

    static int MAXN = 30;

    static int[] father = new int[MAXN];

    static int sets;

    public static void init(int m) {
        for (int i = 0; i < m; i++) {
            father[i] = i;
        }
        sets = m;
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
        // 如果相邻的偶数位和奇数位的两个不是同一队情侣，则需要合并，且合并意味着集合数量减少一个
        if (fx != fy) {
            father[fx] = fy;
            sets--;
        }
    }
}
