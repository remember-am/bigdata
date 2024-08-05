package doublepoint;

import java.util.Arrays;

/**
 * 475. 供暖器
 * 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
 * 在加热器的加热半径范围内的每个房屋都可以获得供暖。
 * 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，请你找出并返回可以覆盖所有房屋的最小加热半径。
 * 注意：所有供暖器 heaters 都遵循你的半径标准，加热的半径也一样。
 *
 * 提示：
 * 1 <= houses.length, heaters.length <= 3 * 10^4
 * 1 <= houses[i], heaters[i] <= 10^9
 */
public class Heaters_LK475 {
    public int findRadius(int[] houses, int[] heaters) {
        // 滑动窗口：需要先将房屋和供暖器排序，贪心过程：每个房屋只需要找离自己最近的供暖器供暖即可
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int ans = 0;
        for (int i = 0, j = 0; i < houses.length; i++) {
            // 判断当前i位置的房屋选择j位置的供暖器是不是最优选择，如果不是最优的，那i位置的房屋继续就看j+1位置的供暖器
            while (!isBest(houses, heaters, i, j)) {
                j++;
            }
            ans = Math.max(ans, Math.abs(houses[i] - heaters[j]));
        }
        return ans;
    }

    // 判断j位置的供暖器给i位置的房屋供暖是不是最好的选择
    public boolean isBest(int[] houses, int[] heaters, int i, int j) {
        return j == heaters.length - 1 || Math.abs(houses[i] - heaters[j + 1]) > Math.abs(houses[i] - heaters[j]);
    }
}
