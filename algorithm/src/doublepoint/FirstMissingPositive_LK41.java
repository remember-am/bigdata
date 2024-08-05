package doublepoint;

/**
 * 41. 缺失的第一个正数
 * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
 * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -2^31 <= nums[i] <= 2^31 - 1
 */
public class FirstMissingPositive_LK41 {
    public int firstMissingPositive(int[] nums) {
        // 定义l代表1到l的数已经找到，并且这些数需要放在数组的左侧，例如：nums[0]=1,nums[1]=2,nums[2]=3...
        // 定义r代表数组nums中垃圾数据的区域以及在数组中可以找到1-r的正整数，例如数组nums的长度位5，那么初始值就是5，
        // r的第一层含义表示可以在数组中找到1-5的正整数
        // r的第二层含义位数组中5位置往后都是垃圾数据区域，后续r可能变成4,3,2...等等数字，则代表nums数组中4（3,2...）位置起后面的都是垃圾数据

        // 1、当前在来到l位置，说明1到l的正整数已经找到，如果nums[l] <= l，则nums[l]的数为垃圾数据
        // 2、当前预期只能找到1到r的正整数，如果nums[l]的数据大于r，则nums[l]的数为垃圾数据
        // 3、当前nums[l]的数据为l+1到r，则需要把这个数放到对应的数组下标nums[l]-1的位置去，如果nums[l]和nums[nums[l]-1]相等，则说明出现重复数字，出现的这个重复数字也是垃圾数据
        int l = 0, r = nums.length;
        while (l < r) {
            if (nums[l] == l + 1) {
                l++;
            } else if (nums[l] <= l || nums[l] > r || nums[l] == nums[nums[l] - 1]) {
                swap(nums, l, --r);
            } else {
                swap(nums, l, nums[l] - 1);
            }
        }
        return l;
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
