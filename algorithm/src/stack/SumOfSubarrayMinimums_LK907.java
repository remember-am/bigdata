package stack;

/**
 * 907. 子数组的最小值之和
 * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
 *
 * 提示：
 * 1 <= arr.length <= 3 * 10^4
 * 1 <= arr[i] <= 3 * 10^4
 */
public class SumOfSubarrayMinimums_LK907 {

    static final int MOD = 1000000007;

    static final int MAX_LENGTH = 30001;

    static final int[] stack = new int[MAX_LENGTH];

    static int r, n;

    public int sumSubarrayMins(int[] arr) {
        // 严格单调栈：考虑必须以i位置的数做最小值时的连续子数组的个数有多少个，
        // 那么就转化为求i位置的左边第一个比它小的数的位置和右边第一个比i位置小的位置
        n = arr.length;
        r = 0;
        // 计算过程存在溢出int类型的可能，先用long类型接住答案
        long ans = 0;
        // cur代表必须以cur位置的元素为最小值，left代表cur左边第一个比cur小的数的位置
        int cur, left;
        for (int i = 0; i < n; i++) {
            while (r > 0 && arr[i] <= arr[stack[r - 1]]) {
                cur = stack[--r];
                left = r > 0 ? stack[r - 1] : -1;
                // 此时有：left是cur的左边第一个比cur小的元素位置，i是cur右边第一个比cur小的元素的位置
                // 则连续子数组必须以cur位置元素为最小值的个数为(cur - left) * (i - cur)
                // 计算过程存在溢出风险，使用同余原理
                ans = (ans + (long) (cur - left) * (i - cur) * arr[cur] % MOD) % MOD;
            }
            stack[r++] = i;
        }
        // 此时栈里的元素都是单调递增的，则每一元素的右边都不存在比它小的数，左边第一个比它小的数就是它压着的这个数
        while (r > 0) {
            cur = stack[--r];
            left = r > 0 ? stack[r - 1] : -1;
            // 同上
            ans = (ans + (long) (cur - left) * (n - cur) * arr[cur] % MOD) % MOD;
        }
        return (int) ans;
    }
}
