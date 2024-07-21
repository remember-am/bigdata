package prefixinfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 1124. 表现良好的最长时间段
 * 给你一份工作时间表 hours，上面记录着某一位员工每天的工作小时数。
 * 我们认为当员工一天中的工作小时数大于 8 小时的时候，那么这一天就是「劳累的一天」。
 * 所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
 * 请你返回「表现良好时间段」的最大长度。
 *
 * 提示：
 * 1 <= hours.length <= 10^4
 * 0 <= hours[i] <= 16
 */
public class LongestWPI_1124 {
    public int longestWPI(int[] hours) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int ans = 0;
        for (int i = 0, sum = 0; i < hours.length; i++) {
            sum += hours[i] > 8 ? 1 : -1;
            if (sum > 0) {
                ans = i + 1;
            } else {
                // 如果sum <= 0 ，只需要找比sum小1的前缀和是否出现
                // 例如当前来到i位置，和是-3，那么以i结尾的子数组如果存在满足的「表现良好时间段」，
                // 那么必然之前的前缀个必然存在比-3小的数，因为只有比-3小的负数加上某一段以i结尾的满足「表现良好时间段」的子数组和
                // 当前的和才能到达-3，那么比-3小的负数有很多个，应该找哪一个呢？答案是-4。为什么不是-5，-6...呢
                // 当数组没有元素时累加和是0，数组只存在1和-1的情况，累加和想要增加或者是减少都是一点一点的增加或减少的
                // 即想要累加和到达-5，则累加和必须先到达-4，所以-4出现的位置一定比-5还早，同理-4出现的位置也比-6还早
                // 所以当前累加和为负数的时候，只需要找比当前累加和小1的累加和出现的位置即可
                if (map.containsKey(sum - 1)) {
                    ans = Math.max(ans, i - map.get(sum - 1));
                }
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return ans;
    }
}
