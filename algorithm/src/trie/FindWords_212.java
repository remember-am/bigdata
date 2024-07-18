package trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 212. 单词搜索 II
 * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。
 * 单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母在一个单词中不允许被重复使用。
 *
 * 提示：
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 12
 * board[i][j] 是一个小写英文字母
 * 1 <= words.length <= 3 * 10^4
 * 1 <= words[i].length <= 10
 * words[i] 由小写英文字母组成
 * words 中的所有字符串互不相同
 */
public class FindWords_212 {
    public List<String> findWords(char[][] board, String[] words) {
        buildTree(words);
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, 1, ans);
            }
        }
        return ans;
    }

    private static final int MAX_LENGTH = (int) 1e4 + 10;

    private static final int[][] tree = new int[MAX_LENGTH][26];

    private static final int[] pass = new int[MAX_LENGTH];

    private static final String[] end = new String[MAX_LENGTH];

    private static int cnt = 1;

    public static void clear() {
        for (int i = 1; i <= cnt; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = 0;
            end[i] = null;
        }
    }

    public static void buildTree(String[] words) {
        for (String word : words) {
            int cur = 1;
            pass[cur]++;
            for (int i = 0, idx; i < word.length(); i++) {
                idx = word.charAt(i) - 'a';
                if (tree[cur][idx] == 0) {
                    tree[cur][idx] = ++cnt;
                }
                cur = tree[cur][idx];
                pass[cur]++;
            }
            end[cur] = word;
        }
    }

    public static int dfs(char[][] board, int i, int j, int cur, List<String> ans) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] == '0') {
            return 0;
        }
        char tmp = board[i][j];
        int idx = tmp - 'a';
        cur = tree[cur][idx];
        if (pass[cur] == 0) {
            return 0;
        }
        int fix = 0;
        if (end[cur] != null) {
            fix++;
            ans.add(end[cur]);
            end[cur] = null;
        }
        board[i][j] = '0';
        fix += dfs(board, i + 1, j, cur, ans);
        fix += dfs(board, i - 1, j, cur, ans);
        fix += dfs(board, i, j + 1, cur, ans);
        fix += dfs(board, i, j - 1, cur, ans);
        pass[cur] -= fix;
        board[i][j] = tmp;
        return fix;
    }
}
