package prefixinfo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * https://www.nowcoder.com/practice/545544c060804eceaed0bb84fcd992fb
 * 未排序数组中累加和为给定值的最长子数组系列问题补1
 * 给定一个无序数组arr，其中元素可正、可负、可0。求arr所有子数组中正数与负数个数相等的最长子数组的长度。
 * 要求时间复杂度为O(n)，空间复杂度为O(n)
 * 输入描述：
 *      第一行一个整数N，表示数组长度
 *      接下来一行有N个数表示数组中的数
 * 输出描述：
 *      输出一个整数表示答案
 * 备注：
 * 1 ⩽ N ⩽ 10^5
 * −100 ⩽ arri ⩽ 100
 */
public class LongestSubarraySignEquals {
    static final int MAX_LENGTH = (int) 1e5 + 1;

    static final int[] arr = new int[MAX_LENGTH];

    static int n;

    static final Map<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0, cur; i < n; i++) {
                st.nextToken();
                cur = (int) st.nval;
                if (cur > 0) {
                    arr[i] = 1;
                } else if (cur < 0) {
                    arr[i] = -1;
                } else {
                    arr[i] = 0;
                }
            }
            out.println(longestSubarraySignEquals());
        }
        out.flush();
        out.close();
        in.close();
    }

    public static int longestSubarraySignEquals() {
        map.clear();
        map.put(0, -1);
        int ans = 0;
        for (int i = 0, sum = 0; i < n; i++) {
            sum += arr[i];
            if (map.containsKey(sum)) {
                ans = Math.max(ans, i - map.get(sum));
            } else {
                map.put(sum, i);
            }
        }
        return ans;
    }
}
