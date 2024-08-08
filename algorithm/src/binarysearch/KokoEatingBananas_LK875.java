package binarysearch;

/**
 * 875. 爱吃香蕉的珂珂
 * 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
 * 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。
 * 如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
 * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
 * 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
 *
 * 提示：
 * 1 <= piles.length <= 10^4
 * piles.length <= h <= 10^9
 * 1 <= piles[i] <= 10^9
 */
public class KokoEatingBananas_LK875 {
    public int minEatingSpeed(int[] piles, int h) {
        // 二分答案法：吃香蕉的速度可能的范围为1到piles的最大值，在这个范围内尽可能的找到满足条件的数字
        int l = 0, r = 1, m = 0;
        for (int pile : piles) {
            r = Math.max(r, pile);
        }
        while (l < r) {
            m = l + ((r - l) >> 1);
            // 判断速度定成m的情况下，能不能再规定时间内吃完所有香蕉
            // 如果速度m能吃完，那答案只可能是小于等于m，往左侧微分
            // 如果熟读m吃不完，那必须提高配速，往右侧二分
            if (eat(piles, h, m)) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    public boolean eat(int[] piles, int h, int k) {
        int sum = 0;
        for (int pile : piles) {
            // a/b向上取整：这种写法必须a和b都是正整数
            sum += (pile + k - 1) / k;
        }
        return sum <= h;
    }
}
