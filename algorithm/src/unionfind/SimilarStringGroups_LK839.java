package unionfind;

/*
839. 相似字符串组
如果交换字符串 X 中的两个不同位置的字母，使得它和字符串 Y 相等，那么称 X 和 Y 两个字符串相似。如果这两个字符串本身是相等的，那它们也是相似的。
例如，"tars" 和 "rats" 是相似的 (交换 0 与 2 的位置)； "rats" 和 "arts" 也是相似的，但是 "star" 不与 "tars"，"rats"，或 "arts" 相似。
总之，它们通过相似性形成了两个关联组：{"tars", "rats", "arts"} 和 {"star"}。注意，"tars" 和 "arts" 是在同一组中，即使它们并不相似。
形式上，对每个组而言，要确定一个单词在组中，只需要这个词和该组中至少一个单词相似。
给你一个字符串列表 strs。列表中的每个字符串都是 strs 中其它所有字符串的一个字母异位词。请问 strs 中有多少个相似字符串组？

提示：
1 <= strs.length <= 300
1 <= strs[i].length <= 300
strs[i] 只包含小写字母。
strs 中的所有单词都具有相同的长度，且是彼此的字母异位词。
 */

public class SimilarStringGroups_LK839 {

    static int MAXN = 301;

    static int[] father = new int[MAXN];

    static int sets;

    public static void init(int n) {
        for (int i = 0; i < n; i++) {
            father[i] = i;
        }
        sets = n;
    }

    public static int find(int i) {
        if (i != father[i]) {
            father[i] = find(father[i]);
        }
        return father[i];
    }

    public static void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if (fx != fy) {
            father[fx] = fy;
            sets--;
        }
    }

    public int numSimilarGroups(String[] strs) {
        int n = strs.length;
        // 将每个单词所在的下标看作的它的集合编号，初始化并查集
        init(n);
        int m = strs[0].length();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 如果当前这两个单词不在同一个集合，则有可能需要合并，但是还需要这两个单词相似
                if (find(i) != find(j)) {
                    int diff = 0;
                    // 两个异构词直接判断它们不同字符的个数
                    for (int k = 0; k < m && diff < 3; k++) {
                        if (strs[i].charAt(k) != strs[j].charAt(k)) {
                            diff++;
                        }
                    }
                    // 从上面循环出来需要看不同位置的字符是否位0或者2，只有0或者2才是相似的，相似则需要合并集合
                    if (diff == 0 || diff == 2) {
                        union(i, j);
                    }
                }
            }
        }
        return sets;
    }
}
