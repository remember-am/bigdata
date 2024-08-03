package binarysearch;

/**
 * 3143. 正方形中的最多点数
 * 给你一个二维数组 points 和一个字符串 s ，其中 points[i] 表示第 i 个点的坐标，s[i] 表示第 i 个点的 标签 。
 * 如果一个正方形的中心在 (0, 0) ，所有边都平行于坐标轴，且正方形内 不 存在标签相同的两个点，那么我们称这个正方形是 合法 的。
 * 请你返回 合法 正方形中可以包含的 最多 点数。
 * 注意：
 *   如果一个点位于正方形的边上或者在边以内，则认为该点位于正方形内。
 *   正方形的边长可以为零。
 *
 * 提示：
 * 1 <= s.length, points.length <= 10^5
 * points[i].length == 2
 * -10^9 <= points[i][0], points[i][1] <= 10^9
 * s.length == points.length
 * points 中的点坐标互不相同。
 * s 只包含小写英文字母。
 */
public class MaximumPointsInsideTheSquare_LK3143 {

    private int ans;

    public int maxPointsInsideSquare(int[][] points, String s) {
        // 二分正方形边长的一半，根据题意点的x坐标或者y坐标最大值10的9次方
        // 所以，正方形的边长的一半可能的值是区间[0,1_000_000_000]，二分边长的一半，看点是否都在所属的边长围成的正方形内
        int l = 0, r = (int) 1e9 + 1, m = 0;
        while (l <= r) {
            m = l + ((r - l) >> 1);
            if (check(points, s, m)) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        return ans;
    }

    public boolean check(int[][] points, String s, int radius) {
        // 由于只有26种标签，直接使用整数位去重即可
        int kind = 0;
        for (int i = 0, idx; i < points.length; i++) {
            // radius是正方形边长的一半，如果点的x，y坐标都小于radius，则该点在正方形内
            if (Math.abs(points[i][0]) <= radius && Math.abs(points[i][1]) <= radius) {
                idx = s.charAt(i) - 'a';
                // 判断该点的标签在此之前出现过没有，出现过则说明以2 * radius为边长的正方不是合法的
                if (((kind >> idx) & 1) == 1) {
                    return false;
                }
                kind |= (1 << idx);
            }
        }
        // 找到一个合法的正方形，更新答案
        ans = Integer.bitCount(kind);
        return true;
    }
}
