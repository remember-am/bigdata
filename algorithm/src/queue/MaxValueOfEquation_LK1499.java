package queue;

/**
 * 1499. 满足不等式的最大值
 * 给你一个数组 points 和一个整数 k 。数组中每个元素都表示二维平面上的点的坐标，并按照横坐标 x 的值从小到大排序。
 * 也就是说 points[i] = [xi, yi] ，并且在 1 <= i < j <= points.length 的前提下， xi < xj 总成立。
 * 请你找出 yi + yj + |xi - xj| 的 最大值，其中 |xi - xj| <= k 且 1 <= i < j <= points.length。
 * 题目测试数据保证至少存在一对能够满足 |xi - xj| <= k 的点。
 *
 * 提示：
 * 2 <= points.length <= 10^5
 * points[i].length == 2
 * -10^8 <= points[i][0], points[i][1] <= 10^8
 * 0 <= k <= 2 * 10^8
 * 对于所有的1 <= i < j <= points.length ，points[i][0] < points[j][0] 都成立。也就是说，xi 是严格递增的。
 */
public class MaxValueOfEquation_LK1499 {

    static final int MAX_LENGTH = 100001;

    // 单调队列：存储坐标点(x,y)，严格大压小，按照y-x的大小组织队列
    static final int[][] deque = new int[MAX_LENGTH][2];

    static int h, t;

    public int findMaxValueOfEquation(int[][] points, int k) {
        h = t = 0;
        int ans = Integer.MIN_VALUE;
        for (int i = 0, x, y; i < points.length; i++) {
            // 当前的坐标点
            x = points[i][0];
            y = points[i][1];
            // 如果队列头部的坐标点离当前坐标点距离超过k了，那队列头部的元素就需要出队列了
            while (h < t && deque[h][0] + k < x) {
                h++;
            }
            // 当前队列里的坐标点是和当前的x，y坐标点的距离都是小于等于k的，可以计算最大的一个答案
            if (h < t) {
                ans = Math.max(ans, x + y + deque[h][1] - deque[h][0]);
            }
            // 当前x，y点需要从尾部进队列
            while (h < t && y - x >= deque[t - 1][1] - deque[t - 1][0]) {
                t--;
            }
            deque[t][0] = x;
            deque[t++][1] = y;
        }
        return ans;
    }
}
