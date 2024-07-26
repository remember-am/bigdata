package diffarray;

/**
 * 1109. 航班预订统计
 * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
 * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti
 * （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
 * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
 *
 * 提示：
 * 1 <= n <= 2 * 10^4
 * 1 <= bookings.length <= 2 * 10^4
 * bookings[i].length == 3
 * 1 <= firsti <= lasti <= n
 * 1 <= seatsi <= 10^4
 */
public class CorpFlightBookings_LK1109 {
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // 初始化差分数组
        int[] diff = new int[n + 2];
        // 构建差分数组，l到r范围加x，则diff[l] + x, diff[r + 1] - x
        for (int[] booking : bookings) {
            diff[booking[0]] += booking[2];
            diff[booking[1] + 1] -= booking[2];
        }
        // 对差分数组求前缀和
        for (int i = 1; i < n + 2; i++) {
            diff[i] += diff[i - 1];
        }
        int[] ans = new int[n];
        System.arraycopy(diff, 1, ans, 0, n);
        return ans;
    }
}
