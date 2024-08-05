package doublepoint;

import java.util.Arrays;

/**
 * 881. 救生艇
 * 给定数组 people 。people[i]表示第 i 个人的体重 ，船的数量不限，每艘船可以承载的最大重量为 limit。
 * 每艘船最多可同时载两人，但条件是这些人的重量之和最多为 limit。
 * 返回 承载所有人所需的最小船数 。
 *
 * 提示：
 * 1 <= people.length <= 5 * 10^4
 * 1 <= people[i] <= limit <= 3 * 10^4
 */
public class BoatsToSavePeople_LK881 {
    public int numRescueBoats(int[] people, int limit) {
        // 数组排序完后每一次看最重的一个人和最轻的一个人的总重量
        // 若重量已经超过限制，则当前最重的一个人是单独需要一个船的，否则当前最重和当前最轻的人一个船是最优方案
        Arrays.sort(people);
        int ans = 0, l = 0, r = people.length - 1, sum = 0;
        while (l <= r) {
            sum = l == r ? people[l] : people[l] + people[r];
            if (sum <= limit) {
                l++;
            }
            r--;
            ans++;
        }
        return ans;
    }
}
