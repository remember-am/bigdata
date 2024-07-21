package prefixinfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 1590. 使数组和能被 P 整除
 * 给你一个正整数数组 nums，请你移除 最短 子数组（可以为 空），使得剩余元素的 和 能被 p 整除。 不允许 将整个数组都移除。
 * 请你返回你需要移除的最短子数组的长度，如果无法满足题目要求，返回 -1 。
 * 子数组 定义为原数组中连续的一组元素。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 1 <= p <= 10^9
 */
public class MinSubarray_LK1590 {
    public int minSubarray(int[] nums, int p) {
        // 求出整个数组累加和的值和p取模的结果
        // 此处题目数据量直接做加法可能存在数值溢出，需要使用同余原理求取模
        int mod = 0;
        for (int num : nums) {
            mod = (mod + num) % p;
        }
        if (mod == 0) {
            return 0;
        }
        // key：前缀和的值与p取模的结果，value：数组下标
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int ans = nums.length;
        for (int i = 0, curMod = 0, want; i < nums.length; i++) {
            // 当前前缀和与p取模的结果
            curMod = (curMod + nums[i]) % p;
            // 想要查询的值
            want = (curMod + p - mod) % p;
            if (map.containsKey(want)) {
                ans = Math.max(ans, i - map.get(want));
            }
            map.put(curMod, i);
        }
        return ans == nums.length ? -1 : ans;
    }
}
