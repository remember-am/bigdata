package sort;

/**
 * 912. 排序数组
 * 给你一个整数数组 nums，请你将该数组升序排列。
 */
public class SortArray_912 {
    public int[] sortArray(int[] nums) {
        selectSort(nums);
        return nums;
    }

    // 选择排序
    public static void selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int min = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[min]) {
                    min = j;
                }
            }
            swap(nums, i, min);
        }
    }

    // 冒泡排序
    public static void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length - i; j++) {
                if (nums[j] < nums[j - 1]) {
                    swap(nums, j - 1, j);
                }
            }
        }
    }

    // 插入排序
    public static void insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j > 0 && nums[j] > nums[j - 1]; j--) {
                swap(nums, j - 1, j);
            }
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
