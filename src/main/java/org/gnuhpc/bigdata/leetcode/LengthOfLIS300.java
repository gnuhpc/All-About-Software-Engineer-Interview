package org.gnuhpc.bigdata.leetcode;

import org.junit.Test;

import java.util.Arrays;

public class LengthOfLIS300 {

    /*
    Method1: 暴力搜索, LTE
     */

    public int lengthOfLIS(int[] nums) {
        return lengthofLIS(nums, Integer.MIN_VALUE, 0);
    }

    /*
    we consider two cases:

The current element is larger than the previous element included in the LIS.
In this case, we can include the current element in the LIS.
 Thus, we find out the length of the LIS obtained by including it.
 Further, we also find out the length of LIS possible by not including the current element in the LIS.
 The value returned by the current function call is,
 thus, the maximum out of the two lengths.

The current element is smaller than the previous element included in the LIS.
In this case, we can't include the current element in the LIS.
Thus, we find out only the length of the LIS possible by not including the current element in the LIS,
 which is returned by the current function call.
     */
    public int lengthofLIS(int[] nums, int prev, int curpos) {
        if (curpos == nums.length) {
            return 0;
        }
        int taken = 0, nottaken = 0;
        if (nums[curpos] > prev) {
            taken = 1 + lengthofLIS(nums, nums[curpos], curpos + 1);
        }
        nottaken = lengthofLIS(nums, prev, curpos + 1);
        return Math.max(taken, nottaken);
    }

    /*
    Method2 : memo search ,TODO: 二维记忆搜索数组舒初始化方法
     */

    public int lengthOfLIS2(int[] nums) {
        int memo[][] = new int[nums.length + 1][nums.length];
        for (int[] l : memo) {
            Arrays.fill(l, -1);
        }
        return lengthofLIS(nums, -1, 0, memo);
    }

    public int lengthofLIS(int[] nums, int previndex, int curpos, int[][] memo) {
        if (curpos == nums.length) {
            return 0;
        }
        if (memo[previndex + 1][curpos] >= 0) {
            return memo[previndex + 1][curpos];
        }
        int taken = 0;
        if (previndex < 0 || nums[curpos] > nums[previndex]) {
            taken = 1 + lengthofLIS(nums, curpos, curpos + 1, memo);
        }

        int nottaken = lengthofLIS(nums, previndex, curpos + 1, memo);
        int result = Math.max(taken, nottaken);
        memo[previndex + 1][curpos] = result;
        return result;
    }


    /*
    Method3: DP
     */
    //https://www.geeksforgeeks.org/longest-increasing-subsequence-dp-3/
    // 如果要求这个子序列，则参考：
    // https://www.hackerrank.com/challenges/longest-increasing-subsequent/problem
    /*
    动态规划DP，类似brute force的解法，维护一个一维dp数组，其中dp[i]表示以nums[i]为结尾的最长递增子串的长度。Time: O(n^2)
设长度为N的数组为{a0，a1, a2, ...an-1)，
则假定以aj结尾的数组序列的最长递增子序列长度为L(j)，
则L(j)={ max(L(i))+1, i<j且a[i]<a[j] }。也就是说，需要遍历在j之前的所有位置i(从0到j-1)，
找出满足条件a[i]<a[j]的L(i)，求出max(L(i))+1即为L(j)的值。
最后，我们遍历所有的L(j)（从0到N-1），找出最大值即为最大递增子序列。时间复杂度为O(N^2)。

例如给定的数组为{5，6，7，1，2，8}，则L(0)=1, L(1)=2, L(2)=3, L(3)=1, L(4)=2, L(5)=4。
所以该数组最长递增子序列长度为4，序列为{5，6，7，8}。
     */
    //还有一种方法是先生成一个排序序列，然后求这个排序序列和原序列的最长公共子序列，方法为：
    //https://www.geeksforgeeks.org/longest-common-subsequence-dp-4/
    public int lengthOfLIS3(int[] nums) {
        int[] dp = new int[nums.length];
        // dp 数组全都初始化为 1
        Arrays.fill(dp, 1);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j])
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        int res = 0;
        for (int i = 0; i < dp.length; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }


    /*
    Method4: 二分法
    https://mp.weixin.qq.com/s?__biz=MzU2MDY5NTE4NA==&mid=2247484036&idx=1&sn=0c64b2fe828e76402cdd471ebd3b9834&chksm=fc05507bcb72d96d458a272437703410387e4f774f9541ba74bdcd12932af88713577eb3dac0&mpshare=1&scene=1&srcid=0917gmCIi8IKZYZkQ0AwHuyy&sharer_sharetime=1568712419437&sharer_shareid=c6956170fd4fb4420832b556d7c1fdc5#rd
     */
    public int lengthOfLIS4(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // minLast[-1] is alwyas Integer.MAX_VALUE
        int[] minLast = new int[nums.length + 1];
        for (int i = 0; i <= nums.length; i++) {
            minLast[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < nums.length; i++) {
            int index = binarySearch(minLast, nums[i]);
            minLast[index] = nums[i];
        }

        return binarySearch(minLast, Integer.MAX_VALUE);
    }

    // find the first number >= num
    private int binarySearch(int[] minLast, int num) {
        int start = 0, end = minLast.length - 1;
        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (minLast[mid] < num) {
                start = mid;
            } else {
                end = mid;
            }
        }
        if (minLast[start] >= num) {
            return start;
        }
        return end;
    }

    /*
    Method5: 终极大法，
    贪心，不但获取长度还获取这个序列，https://www.ideserve.co.in/learn/longest-increasing-subsequence-nlogn
     */

    public int lengthOfLIS5(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int len = nums.length;

        //保存子序列中上一个数字的位置
        int[] parent = new int[len];

        //跟踪每个上升子序列的尾部数值的idx
        int[] tails = new int[len + 1];

        //记录最长上升子序列的长度
        int length = 0;

        //处理第一个
        tails[1] = 0;
        length++;

        for (int i = 1; i < len; i++) {
            int start = 1;
            int end = length;

            int pos = 0;
            if (nums[tails[length]]<nums[i]){
                pos = length+1;
            } else{
                while (start +1 < end) {
                    int mid = (end-start)/2 + start;
                    if (nums[tails[mid]] < nums[i]) //向右找
                        start = mid;
                    else // 向左找
                        end = mid;
                }

                if (nums[tails[start]]>=nums[i]){
                    pos = start;
                } else if (nums[tails[end]]>=nums[i]){
                    pos = end;
                }
            }

            //replace or append
            tails[pos] = i;

            //update parent
            parent[i] = tails[pos - 1];

            if (pos > length)
                length = pos;
        }

        //倒着把所有最长上升子序列还原
        int[] LIS = new int[length];
        int k = tails[length];
        for (int j = length - 1; j >= 0; j--) {
            LIS[j] = nums[k];
            k = parent[k];
        }

        return length;
    }


    @Test
    public void test() {
        System.out.println(lengthOfLIS5(new int[]{3,1,5,2,6,4,9}));
        System.out.println(lengthOfLIS5(new int[]{2,2}));
    }
}
