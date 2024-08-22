package queue;

import java.util.Arrays;

/**
 * 2071. 你可以安排的最多任务数目
 * 给你 n 个任务和 m 个工人。每个任务需要一定的力量值才能完成，需要的力量值保存在下标从 0 开始的整数数组 tasks 中，
 * 第 i 个任务需要 tasks[i] 的力量才能完成。每个工人的力量值保存在下标从 0 开始的整数数组 workers 中，第 j 个工人的力量值为 workers[j] 。
 * 每个工人只能完成 一个 任务，且力量值需要 大于等于 该任务的力量要求值（即 workers[j] >= tasks[i] ）。
 * 除此以外，你还有 pills 个神奇药丸，可以给 一个工人的力量值 增加 strength 。你可以决定给哪些工人使用药丸，但每个工人 最多 只能使用 一片 药丸。
 * 给你下标从 0 开始的整数数组tasks 和 workers 以及两个整数 pills 和 strength ，请你返回 最多 有多少个任务可以被完成。
 *
 * 提示：
 * n == tasks.length
 * m == workers.length
 * 1 <= n, m <= 5 * 10^4
 * 0 <= pills <= m
 * 0 <= tasks[i], workers[j], strength <= 10^9
 */
public class MaximumNumberOfTasksYouCanAssign_LK2071 {

    static final int MAX_LENGTH = 50001;

    static final int[] deque = new int[MAX_LENGTH];

    static int h, t;

    public int maxTaskAssign(int[] tasks, int[] workers, int pills, int strength) {
        // 整体思路：二分答案法 + 单调队列
        // 考虑完成的任务数是有范围的，最少一个任务也完成不了，最多只能完成工人个数和任务个数的最小值，因为一个工人只能完成一个任务
        // 二分答案法：如果一定要完成m个任务，告诉我能不能完成
        Arrays.sort(tasks);
        Arrays.sort(workers);
        int ans = 0;
        for (int l = 0, r = Math.min(tasks.length, workers.length), m; l <= r; ) {
            m = l + ((r - l) >> 1);
            // f函数：如果一定要完成m个任务，能不能成功
            if (f(tasks, workers, pills, strength, m)) {
                ans = m;
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    public boolean f(int[] tasks, int[] workers, int pills, int strength, int m) {
        // 想一个自然智慧：如果一定要完成m个任务，那就只在乎完成的任务数量
        // 所以优先选则完成需要力量较小的前m个任务，用力量较大的m个工人去完成
        // 即tasks数组排序后取前m个，workers数据排序后取后m个
        for (int i = workers.length - m, j = 0; i < workers.length; i++) {
            // i是工人的下标，j是任务的下标，每一轮只看较小的那个工人
            // 看i号工人不吃药是否能解锁更多任务，把能够解锁的任务放入队列
            for ( ; j < m && workers[i] >= tasks[j]; j++) {
                deque[t++] = j;
            }
            // i号工人去完成队列中的最小力量的任务，如果没有任务给i号工人，则i号工人吃药去解锁任务
            if (h < t && workers[i] >= tasks[deque[h]]) {
                h++;
            } else {
                // i号工人吃药解锁任务
                for ( ; j < m && workers[i] + strength >= tasks[j]; j++) {
                    deque[t++] = j;
                }
                if (h < t) {
                    // i号工人已经吃药了，需要优先完成队列中最大力量的任务
                    t++;
                    pills--;
                } else {
                    return false;
                }
            }
        }
        // 如果能够跑完流程，就要吃的药丸有没有超过pills
        return pills >= 0;
    }
}
