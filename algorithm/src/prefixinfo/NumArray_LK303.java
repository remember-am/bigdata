package prefixinfo;

/**
 * 303. 区域和检索 - 数组不可变
 * 给定一个整数数组  nums，处理以下类型的多个查询:
 * 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 * NumArray(int[] nums) 使用数组 nums 初始化对象
 * int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，
 * 包含 left 和 right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
 *
 * 提示：
 * 1 <= nums.length <= 10^4
 * -10^5 <= nums[i] <= 10^5
 * 0 <= i <= j < nums.length
 * 最多调用 10^4 次 sumRange 方法
 */
class NumArray_303 {

    int[] nums;

    public NumArray_303(int[] nums) {
        this.nums = new int[nums.length + 1];
        for (int i = 1; i < this.nums.length; i++) {
            this.nums[i] += this.nums[i - 1] + nums[i - 1];
        }
    }

    public int sumRange(int left, int right) {
        return nums[right + 1] - nums[left];
    }
}
