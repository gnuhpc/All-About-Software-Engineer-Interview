package org.gnuhpc.bigdata.leetcode;

import org.gnuhpc.bigdata.leetcode.utils.Utils;

import java.util.Arrays;

public class SortColors75 {
    public static void sortColors(int[] nums){
        int[] counts = new int[3];

        for (int i = 0; i < nums.length; i++) {
            counts[nums[i]]++;
        }

        int k = 0;
        for (int i = 0; i < counts.length; i++) {
            for (int j = k; j < counts[i]+k; j++) {
                nums[j] = i;
            }
            k += counts[i];
        }
    }

    public static void sortColors2(int[] nums){
        int zero = -1, two = nums.length;// nums[0...zero]==0, nums[two...j-1]==2

        for (int j = 0; j < two; ) {
            if (nums[j]==1){
                j++;
            } else if (nums[j]==2){
                Utils.swap(nums,--two,j);
                //不j++的原因是：换过来的数有可能比1大。
            } else {
                Utils.swap(nums,++zero,j);
                j++; //直接j++的原因是从zero换过来的一定是
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,2,1,2,1,0,2};
        sortColors(nums);

        int[] nums2 = new int[]{2,0,2,1,1,2};
        sortColors2(nums2);
        Arrays.stream(nums).forEach(System.out::print);
        System.out.println();
        Arrays.stream(nums2).forEach(System.out::print);
    }

    // add by tina, 双指针思想
    public void sortColors3(int[] nums) {
        if(nums == null || nums.length == 0 || nums.length == 1)  return;
        // nums[0...red) == 0,nums(blue..length-1] == 2
        int red = 0;
        int blue = nums.length-1;
        for(int cur = 0;cur<=blue;cur++){
            if(nums[cur] == 0) {
                swap(nums,cur,red);
                red++;
            }else if(nums[cur] == 2){
                swap(nums,cur,blue);
                blue--;cur--;
            }
        }
    }

    public void swap(int[] nums, int i, int j){
        int temp = 0;
        temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
