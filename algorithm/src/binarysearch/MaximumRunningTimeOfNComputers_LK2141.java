package binarysearch;

/**
 * 2141. 同时运行 N 台电脑的最长时间
 * 你有 n 台电脑。给你整数 n 和一个下标从 0 开始的整数数组 batteries ，其中第 i 个电池可以让一台电脑 运行 batteries[i] 分钟。
 * 你想使用这些电池让 全部 n 台电脑 同时 运行。
 * 一开始，你可以给每台电脑连接 至多一个电池 。然后在任意整数时刻，你都可以将一台电脑与它的电池断开连接，并连接另一个电池，
 * 你可以进行这个操作 任意次 。新连接的电池可以是一个全新的电池，也可以是别的电脑用过的电池。断开连接和连接新的电池不会花费任何时间。
 * 注意，你不能给电池充电。
 * 请你返回你可以让 n 台电脑同时运行的 最长 分钟数。
 *
 * 提示：
 * 1 <= n <= batteries.length <= 10^5
 * 1 <= batteries[i] <= 10^9
 */
public class MaximumRunningTimeOfNComputers_LK2141 {
    // 二分答案法 + 贪心剪枝
    // 首先确定n台电脑能同时运行的最小时间是0，最大时间不会超过电池数组batteries的总和
    // 找到电池数组batteries的最大值max，如果数组的累加和大于max*n，则说明n台电脑同时运行的时间一定是大于等于max的
    public long maxRunTime(int n, int[] batteries) {
        int max = 0;
        long sum = 0;
        for (int battery : batteries) {
            max = Math.max(max, battery);
            sum += battery;
        }
        // 如果数组的累加和大于max*n，则说明n台电脑同时运行的时间一定是大于等于max的
        // 能运行的最大时间就是sum / n
        if (sum > (long) max * n) {
            return sum / n;
        }
        // 如果上一步满足说明n台电脑的最大同时运行的时间是大于等于max的，反之，则n台电脑同时运行的最大时间是小于等于max的
        // 二分答案基于0到max，进一步缩小二分的范围
        int ans = 0;
        for (int l = 0, r = max, m; l <= r; ) {
            m = l + ((r - l) >> 1);
            if (f(batteries, n, m)) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    public boolean f(int[] batteries, int n, int m) {
        long sum = 0;
        for (int battery : batteries) {
            if (battery >= m) {
                n--;
            } else {
                sum += battery;
            }
            if (sum >= (long) n * m) {
                return true;
            }
        }
        return false;
    }

    // 常规二分答案法：电脑能够运行的时间范围最小是0，最大时电池数组的和
    public long maxRunTime1(int n, int[] batteries) {
        // 给定运行时间为m的情况下，问电池能不能撑到给定的m时长
        long sum = 0;
        for (int battery : batteries) {
            sum += battery;
        }
        long ans = 0;
        for (long l = 0, r = sum, m; l <= r; ) {
            m = l + ((r - l) >> 1);
            // 在给定运行时间为m的情况下，问电池数组是否能撑过给定时间m
            if (f1(batteries, n, m)) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    public boolean f1(int[] batteries, int n, long m) {
        long sum = 0;
        for (int battery : batteries) {
            if (battery >= m) {
                n--;
            } else {
                sum += battery;
            }
            if (sum >= m * n) {
                return true;
            }
        }
        return false;
    }
}
