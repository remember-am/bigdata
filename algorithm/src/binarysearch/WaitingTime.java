package binarysearch;

import java.util.PriorityQueue;

/**
 * 计算等位时间
 * 给定一个数组waiters长度为n，表示n个服务员，每服务一个人的时间
 * 给定一个正数m，表示有m个人等位，每一个人都遵循有空闲服务员就选的原则，如果你是刚来的人，请问你需要等多久？
 * 假设m远远大于n，比如n <= 10^3, m <= 10^9，该怎么做是最优解？
 *
 * 例如：waiters = [1,5,3]，m = 5，那么你只需要等待3个时间点就可以获得服务。
 * 假设有A,B,C,D,E五个人都遵循有空位就上的原则，那么在
 *    0时刻：A可以选择0号服务员，B选择1好服务员，C选择2号服务员
 *    1时刻：根据三个服务员服务的时间可知当前只有0号服务员完成了服务，空闲的也只有0号服务员，所有D可以选择0号服务员
 *    2时刻：同上是有0号服务员空闲，E号服务员可以选择0号服务员
 *    3时刻：此时有0号服务员和2号服务员空闲，你前面已经没有排队的人了，所以你可以3时刻获得服务
 */
public class WaitingTime {
    // 小根堆：往小根堆里放入每个服务员的开始服务时间以及服务时长。m个人遵循有空位就上的原则，其实和选哪个服务员没有关系
    public static int waitingTime1(int[] waiters, int m) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((o1, o2) -> (o1[0] - o2[0]));
        for (int waiter : waiters) {
            heap.offer(new int[] {0, waiter});
        }
        for (int i = 0; i < m; i++) {
            int[] cur = heap.poll();
            cur[0] += cur[1];
            heap.offer(cur);
        }
        return heap.peek()[0];
    }

    // 二分答案法：最少等待的时间是0，最多等待的时间是waiters数组中最小值的m倍，因为假设只让服务时长最短的服务员服务，
    // 那在等待m倍的时间后也一定会获得服务，况且还有其它的服务员
    public static int waitingTime2(int[] waiters, int m) {
        int min = Integer.MAX_VALUE;
        for (int waiter : waiters) {
            min = Math.min(min, waiter);
        }
        int ans = 0;
        // 二分答案法：等待的时间最小值是0，最大值是min*m
        for (int l = 0, r = min * m, mid; l <= r; ) {
            mid = l + ((r - l) >> 1);
            // 问等待mid的时长，能服务多少个人（注意，你要获得服务，所以m个人外需要加上你）
            if (f(waiters, mid) >= m + 1) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    public static int f(int[] waiters, int mid) {
        int ans = 0;
        for (int waiter : waiters) {
            // 等待mid的时长，一个人可以服务mid/waiter+1个人
            // 因为就算mid/waiter存在余数，那余数的时间段也是在服务人的
            // 如果mid/waiter不存在余数，说明正好服务完mid/waiter个人，但是服务完后已经可以开始服务其它人了
            ans += (mid / waiter) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("测试开始");
        int N = 50;
        int V = 30;
        int M = 3000;
        int testTime = 20000;
        // 对数器验证
        for (int i = 0; i < testTime; i++) {
            int n = (int) (Math.random() * N) + 1;
            int[] arr = randomArray(n, V);
            int m = (int) (Math.random() * M);
            int ans1 = waitingTime1(arr, m);
            int ans2 = waitingTime2(arr, m);
            if (ans1 != ans2) {
                System.out.println("出错了!");
                break;
            }
        }
        System.out.println("测试结束");
    }

    // 生成随机数组
    public static int[] randomArray(int n, int v) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * v) + 1;
        }
        return arr;
    }
}
