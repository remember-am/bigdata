package slidingwindow;

/**
 * 134. 加油站
 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
 * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
 *
 * 提示:
 * gas.length == n
 * cost.length == n
 * 1 <= n <= 10^5
 * 0 <= gas[i], cost[i] <= 10^4
 */
public class GasStation_LK134 {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        // 根据题意，gas数组和cost数组可以处理为一个数组，例如gas = [2,3,4], cost = [3,4,3]，则处理后数组为[-1,-1,1]
        // 那么就转化为求[-1,-1,1]这个数组中，从哪一个位置开始遍历一圈后和大于等于0，并且每一个位置的和都是大于等于0的
        // 滑动窗口：len代表窗口的长度，sum代表窗口中数据的总和，r代表即将进窗口的位置
        int n = gas.length;
        for (int l = 0, r = 0, len = 0, sum = 0; l < n; l++) {
            // 如果窗口中sum大于等于0，则证明可以到达当前，尝试继续去下一个加油站
            while (sum >= 0) {
                // 当前窗口长度为n，则已经走完一圈，返回窗口的左边界l
                if (len == n) {
                    return l;
                }
                // 尝试去下一个油站，注意是循环的，去往下一个油站，则窗口长度加一，sum加上下一个进窗口的数
                r = (l + (len++)) % n;
                sum += gas[r] - cost[r];
            }
            // 从while里出来，说明以l为出发点不能走完一圈，敞口长度减一，对应减去窗口l对应的数。l加一，去判断以l+1为起点能不能走完一圈
            len--;
            sum -= gas[l] - cost[l];
        }
        return -1;
    }
}
