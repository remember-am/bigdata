package stack;

import java.io.*;

/**
 * https://www.nowcoder.com/practice/2a2c00e7a88a498693568cef63a4b7bb
 * 单调栈结构(进阶)
 * 给定一个可能含有重复值的数组 arr，找到每一个 i 位置左边和右边离 i 位置最近且值比 arr[i] 小的位置。返回所有位置相应的信息。
 * 输入描述：
 * 第一行输入一个数字 n，表示数组 arr 的长度。
 * 以下一行输入 n 个数字，表示数组的值
 * 输出描述：
 * 输出n行，每行两个数字 L 和 R，如果不存在，则值为 -1，下标从 0 开始。
 *
 * 备注：
 * 1≤n≤1000000
 * −1000000≤arri≤1000000
 */
public class MonotoneStackTemplate_NC {
    static final int MAX_LENGTH = 1_000_001;

    static final int[] arr = new int[MAX_LENGTH];

    static final int[] stack = new int[MAX_LENGTH];

    static final int[][] ans = new int[MAX_LENGTH][2];

    static int r, n;

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer st = new StreamTokenizer(in);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (st.nextToken() != StreamTokenizer.TT_EOF) {
            n = (int) st.nval;
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (int) st.nval;
            }
            // 单调栈
            calculate();
            for (int i = 0; i < n; i++) {
                out.println(ans[i][0] + " " + ans[i][1]);
            }
        }
        out.flush();
        out.close();
        in.close();
    }

    public static void calculate() {
        r = 0;
        int cur;
        // 遍历阶段：栈里永远是严格大压小（根据元素大小），但是栈里放的是元素的下标而不是元素
        for (int i = 0; i < n; i++) {
            // 当前元素大于等于栈顶的元素，栈顶元素就需要弹出，此时就可以计算当前弹出元素的左右两边的第一个比它小的数
            // 左边比当前弹出位置元素小的数就是栈顶元素对应下标的数组元素
            // 谁让当前元素弹出的谁就是当前被弹出元素的右边第一个小的值（可能是不正确的，后续步骤修复）
            while (r > 0 && arr[stack[r - 1]] >= arr[i]) {
                cur = stack[--r];
                ans[cur][0] = r == 0 ? -1 : stack[r - 1];
                ans[cur][1] = i;
            }
            stack[r++] = i;
        }
        // 弹出栈里剩余元素阶段：弹出的元素左边第一个比它小的就是剩余栈顶的元素
        //                   弹出的元素右边没有比它小的，所以填-1
        while (r > 0) {
            cur = stack[--r];
            ans[cur][0] = r == 0 ? -1 : stack[r - 1];
            ans[cur][1] = -1;
        }
        // 修正阶段：每一个元素的左边第一个比它小的一定是正确的，只有右边会存在错误可能
        // 修正的逻辑就是判断得到的当前元素的右边第一个比它小的元素是否和当前元素相等，相等就把右边这个数对应的答案赋值给当前元素
        for (int i = n - 2; i >= 0; i--) {
            if (ans[i][1] != -1 && arr[i] == arr[ans[i][1]]) {
                ans[i][1] = ans[ans[i][1]][1];
            }
        }
    }
}
