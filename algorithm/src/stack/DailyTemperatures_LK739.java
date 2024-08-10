package stack;

/**
 * 739. 每日温度
 * 给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
 * 如果气温在这之后都不会升高，请在该位置用 0 来代替。
 *
 * 提示：
 * 1 <= temperatures.length <= 10^5
 * 30 <= temperatures[i] <= 100
 */
public class DailyTemperatures_LK739 {

    static final int MAX_LENGTH = 100001;

    static final int[] stack = new int[MAX_LENGTH];

    static int r, n;

    public int[] dailyTemperatures(int[] temperatures) {
        n = temperatures.length;
        int[] ans = new int[n];
        // 单调栈：不严格小压大
        for (int i = 0, cur; i < n; i++) {
            // 如果当前温度比当前栈顶的温度要高，则可以结算当前栈顶元素对应的答案
            while (r > 0 && temperatures[i] > temperatures[stack[r - 1]]) {
                cur = stack[--r];
                ans[cur] = i - cur;
            }
            stack[r++] = i;
        }
        return ans;
    }
}
