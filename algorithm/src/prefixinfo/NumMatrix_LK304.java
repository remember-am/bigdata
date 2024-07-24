package prefixinfo;

/**
 * 304. 二维区域和检索 - 矩阵不可变
 * 给定一个二维矩阵 matrix，以下类型的多个请求：
 * 计算其子矩形范围内元素的总和，该子矩阵的 左上角 为 (row1, col1) ，右下角 为 (row2, col2) 。
 * 实现 NumMatrix 类：
 * NumMatrix(int[][] matrix) 给定整数矩阵 matrix 进行初始化
 * int sumRegion(int row1, int col1, int row2, int col2) 返回左上角(row1, col1)、右下角(row2, col2)所描述的子矩阵的元素总和 。
 *
 * 提示：
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 200
 * -10^5 <= matrix[i][j] <= 10^5
 * 0 <= row1 <= row2 < m
 * 0 <= col1 <= col2 < n
 * 最多调用 10^4 次 sumRegion 方法
 */
public class NumMatrix_LK304 {

    int[][] matrix;

    public NumMatrix_LK304(int[][] matrix) {
        // 二维前缀和数组：坐标(i,j)的和等于(i-1,j-1)-(i-1,j)-(i,j-1)+原始数组(i,j)
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] += get(matrix, i, j - 1) + get(matrix, i - 1, j) - get(matrix, i - 1, j - 1);
            }
        }
        this.matrix = matrix;
    }

    private int get(int[][] matrix, int i, int j) {
        return i < 0 || j < 0 ? 0 : matrix[i][j];
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return get(matrix, row2, col2)
                - get(matrix, row2, col1 - 1)
                - get(matrix, row1 - 1, col2)
                + get(matrix, row1 - 1, col1 - 1);
    }
}
