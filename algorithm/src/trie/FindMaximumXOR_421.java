package trie;

/**
 * 421. 数组中两个数的最大异或值
 * 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
 *
 * 提示：
 * 1 <= nums.length <= 2 * 10^5
 * 0 <= nums[i] <= 2^31 - 1
 */
public class FindMaximumXOR_421 {
    // 前缀树做法
    public int findMaximumXOR(int[] nums) {
        int max = 0;
        for (int num : nums) {
            max = Math.max(max, num);
        }
        // 数组中最大值二进制状态下最高位的1在哪个位置
        int high = 31 - Integer.numberOfLeadingZeros(max);
        buildTree(nums, high);
        int ans = 0;
        for (int num : nums) {
            ans = Math.max(ans, maxXOR(num, high));
        }
        clear();
        return ans;
    }

    private static final int MAX_LENGTH = (int) 3e6 + 10;

    private static final int[][] tree = new int[MAX_LENGTH][2];

    private static int cnt = 1;

    public static void clear() {
        for (int i = 1; i <= cnt; i++) {
            tree[i][0] = tree[i][1] = 0;
        }
        cnt = 1;
    }

    // 根据位信息构建前缀树
    public static void buildTree(int[] nums, int high) {
        for (int num : nums) {
            int cur = 1;
            for (int i = high, idx; i >= 0; i--) {
                idx = (num >> i) & 1;
                if (tree[cur][idx] == 0) {
                    tree[cur][idx] = ++cnt;
                }
                cur = tree[cur][idx];
            }
        }
    }

    public static int maxXOR(int num, int high) {
        int ans = 0, cur = 1;
        for (int i = high, self, want; i >= 0; i--) {
            // self代表当前数在i位的状态，想要得到较大的值，那么需要在i位异或上与self不同的值
            self = (num >> i) & 1;
            // want代表想要的值，若self为1，则want为0，若self为0，则want为1
            want = self ^ 1;
            // 如果前缀树不存在当前想要的want这条路，则只能选择异或上与self相同的值
            if (tree[cur][want] == 0) {
                want ^= 1;
            }
            // 更新ans在i为的值
            ans |= (self ^ want) << i;
            cur = tree[cur][want];
        }
        return ans;
    }
}
