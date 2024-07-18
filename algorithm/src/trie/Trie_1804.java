package trie;

import java.util.Arrays;

/**
 * 1804. 实现 Trie (前缀树)
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
 * 这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 * 请你实现 Trie 类：
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * int countWordsEqualTo(String word) 返回前缀树中字符串 word 的实例个数 。
 * int countWordsStartingWith(String prefix) 返回前缀树中以 prefix 字符串为前缀的字符串个数 。
 * void erase(String word) 从前缀中移除字符串 word
 */
public class Trie_1804 {
    static final int MAX_SIZE = (int) 1e5 + 10;

    static int[][] tree = new int[MAX_SIZE][26];

    static int[] pass = new int[MAX_SIZE];

    static int[] end = new int[MAX_SIZE];

    static int cnt = 1;

    public Trie_1804() {

    }

    // 初始化所有空间信息
    public static void clear() {
        for (int i = 1; i <= cnt; i++) {
            Arrays.fill(tree[i], 0);
            pass[i] = 0;
            end[i] = 0;
        }
        cnt = 1;
    }

    // 向前缀树中插入字符串 word
    public void insert(String word) {
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
        end[cur]++;
    }

    // 返回前缀树中字符串 word 的实例个数
    public int countWordsEqualTo(String word) {
        int cur = 1;
        for (int i = 0, idx; i < word.length(); i++) {
            idx = word.charAt(i) - 'a';
            if (tree[cur][idx] == 0) {
                return 0;
            }
            cur = tree[cur][idx];
        }
        return end[cur];
    }

    // 返回前缀树中以 prefix 字符串为前缀的字符串个数
    public int countWordsStartingWith(String prefix) {
        int cur = 1;
        for (int i = 0, idx; i < prefix.length(); i++) {
            idx = prefix.charAt(i) - 'a';
            if (tree[cur][idx] == 0) {
                return 0;
            }
            cur = tree[cur][idx];
        }
        return pass[cur];
    }

    // 从前缀中移除字符串 word
    public void erase(String word) {
        if (countWordsEqualTo(word) > 0) {
            int cur = 1;
            pass[cur]--;
            for (int i = 0, idx; i < word.length(); i++) {
                idx = word.charAt(i) - 'a';
                if (--pass[tree[cur][idx]] == 0) {
                    tree[cur][idx] = 0;
                }
                cur = tree[cur][idx];
            }
            end[cur]--;
        }
    }
}
