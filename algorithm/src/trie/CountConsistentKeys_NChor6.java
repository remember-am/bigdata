package trie;

import java.util.Arrays;

/**
 * hor6：接头密匙
 * 牛牛和他的朋友们约定了一套接头密匙系统，用于确认彼此身份。密匙由一组数字序列表示，两个密匙被认为是一致的，如果满足以下条件：
 * 密匙 b 的长度不超过密匙 a 的长度。
 * 对于任意 0 <= i < length(b)，有 b[i+1] - b[i] == a[i+1] - a[i]。
 * 现在给定了m个密匙 b 的数组，以及n个密匙 a 的数组。请你返回一个长度为 m 的结果数组 ans，表示每个密匙b都有多少一致的密匙a。
 *
 * 说明：
 * 数组 a 和数组 b 中的元素个数均不超过 10^5。
 * 1 <= m, n <= 1000
 *
 * https://www.nowcoder.com/practice/c552d3b4dfda49ccb883a6371d9a6932
 */
public class CountConsistentKeys_NChor6 {
    public int[] countConsistentKeys (int[][] b, int[][] a) {
        // write code here
        StringBuilder s = new StringBuilder();
        for (int[] arr : a) {
            s.setLength(0);
            for (int i = 1; i < arr.length; i++) {
                s.append(arr[i] - arr[i - 1]);
                s.append('#');
            }
            insert(s.toString());
        }
        int[] ans = new int[b.length];
        for (int i = 0; i < b.length; i++) {
            s.setLength(0);
            for (int j = 1; j < b[i].length; j++) {
                s.append(b[i][j] - b[i][j - 1]);
                s.append('#');
            }
            ans[i] = prefixCount(s.toString());
        }
        clear();
        return ans;
    }

    static int MAX = (int) 1e3 + 10;

    static int[][] tree = new int[MAX][12];

    static int[] pass = new int[MAX];

    static int[] end = new int[MAX];

    static int cnt = 1;

    private static void clear() {
        for (int i = 0; i < MAX; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = 0;
            end[i] = 0;
        }
        cnt = 1;
    }

    // 两个元素相减可能存在负数，所以字符串可能出现12中字符
    private static int getPath(char c) {
        return switch (c) {
            case '-' -> 10;
            case '#' -> 11;
            default -> c - '0';
        };
    }

    private static void insert(String word) {
        int cur = 1;
        pass[cur]++;
        for (int i = 0, idx; i < word.length(); i++) {
            idx = getPath(word.charAt(i));
            if (tree[cur][idx] == 0) {
                tree[cur][idx] = ++cnt;
            }
            cur = tree[cur][idx];
            pass[cur]++;
        }
        end[cur]++;
    }

    private static int prefixCount(String pre) {
        int cur = 1;
        for (int i = 0, idx; i < pre.length(); i++) {
            idx = getPath(pre.charAt(i));
            if (tree[cur][idx] == 0) {
                return 0;
            }
            cur = tree[cur][idx];
        }
        return pass[cur];

    }
}
