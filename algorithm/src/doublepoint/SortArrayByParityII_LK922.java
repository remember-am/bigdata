package doublepoint;

/**
 * 922. 按奇偶排序数组 II
 * 给定一个非负整数数组 nums，  nums 中一半整数是 奇数 ，一半整数是 偶数 。
 * 对数组进行排序，以便当 nums[i] 为奇数时，i 也是 奇数 ；当 nums[i] 为偶数时， i 也是 偶数 。
 * 你可以返回 任何满足上述条件的数组作为答案 。
 *
 * 提示：
 * 2 <= nums.length <= 2 * 10^4
 * nums.length 是偶数
 * nums 中一半是偶数
 * 0 <= nums[i] <= 1000
 */
public class SortArrayByParityII_LK922 {
    public int[] sortArrayByParityII(int[] nums) {
        // even代表偶数指针，永远只看偶数位置；odd代表奇数指针，永远只看奇数位置
        // 一直看数组末尾最后一个位置的数，如果是奇数则和odd位置的数交换，如果是偶数则和even位置的数交换，直到奇偶指针有一个越位为止
        int even = 0, odd = 1, n = nums.length - 1;
        while (even <= n && odd <= n) {
            if (nums[n] % 2 == 0) {
                swap(nums, even, n);
                even += 2;
            } else {
                swap(nums, odd, n);
                odd += 2;
            }
        }
        return nums;
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
