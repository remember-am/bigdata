package trie;

/**
 * 208. 实现 Trie (前缀树)
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
 * 这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 * 请你实现 Trie 类：
 * Trie() 初始化前缀树对象。
 * void insert(String word) 向前缀树中插入字符串 word 。
 * boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
 * boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
 *
 * 提示：
 * 1 <= word.length, prefix.length <= 2000
 * word 和 prefix 仅由小写英文字母组成
 * insert、search 和 startsWith 调用次数总计不超过 3 * 10^4 次
 */
public class Trie_208 {
    static class TrieNode {
        int pass;
        int end;
        TrieNode[] next;

        TrieNode() {
            pass = 0;
            end = 0;
            next = new TrieNode[26];
        }
    }

    private final TrieNode root;

    public Trie_208() {
        root = new TrieNode();
    }

    // 向前缀树中插入字符串 word
    public void insert(String word) {
        TrieNode cur = root;
        cur.pass++;
        for (int i = 0, idx; i < word.length(); i++) {
            idx = word.charAt(i) - 'a';
            if (cur.next[idx] == null) {
                cur.next[idx] = new TrieNode();
            }
            cur = cur.next[idx];
            cur.pass++;
        }
        cur.end++;
    }

    // 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false
    public boolean search(String word) {
        TrieNode cur = root;
        for (int i = 0, idx; i < word.length(); i++) {
            idx = word.charAt(i) - 'a';
            if (cur.next[idx] == null) {
                return false;
            }
            cur = cur.next[idx];
        }
        return cur.end > 0;
    }

    // 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (int i = 0, idx; i < prefix.length(); i++) {
            idx = prefix.charAt(i) - 'a';
            if (cur.next[idx] == null) {
                return false;
            }
            cur = cur.next[idx];
        }
        return true;
    }
}
