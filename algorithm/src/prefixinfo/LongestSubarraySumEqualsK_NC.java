package prefixinfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * https://www.nowcoder.com/practice/36fb0fd3c656480c92b569258a1223d5
 * 未排序数组中累加和为给定值的最长子数组长度
 * 给定一个无序数组arr, 其中元素可正、可负、可0。给定一个整数k，求arr所有子数组中累加和为k的最长子数组长度
 * 输入描述：
 *      第一行两个整数N, k。N表示数组长度，k的定义已在题目描述中给出
 *      第二行N个整数表示数组内的数
 * 输出描述：
 *      输出一个整数表示答案
 * 备注：
 * 1 ⩽ N ⩽ 10^5
 * −10^9 ⩽ k ⩽ 10^9
 * −100 ⩽ arr i ⩽ 100
 */
public class LongestSubarraySumEqualsK {

    static final int MAX_LENGTH = (int) 1e5 + 1;

    static final int[] arr = new int[MAX_LENGTH];

    static int n, k;

    static final Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            st.nextToken();
            k = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (int) st.nval;
            }
            out.println(findSubarraySumEqualsK());
        }
        out.flush();
        out.close();
        in.close();
    }

    public static int findSubarraySumEqualsK() {
        map.clear();
        // 代表0这个前缀和在没有数组元素的时候就已经存在了
        map.put(0, -1);
        int ans = 0;
        for (int i = 0, sum = 0; i < n; i++) {
            sum += arr[i];
            // 类似余 两数之和 的逻辑
            if (map.containsKey(sum - k)) {
                ans = Math.max(ans, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return ans;
    }
}
