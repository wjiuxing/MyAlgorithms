package 栈_队列;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sliding-window-maximum
 * @author wjx
 * 
 */
public class _239_滑动窗口最大值 {
    
    /**
     * 先扫描前 K 个元素，找到最大值索引，记作 maxIndex。
     * 滑动窗口，如果 maxIndex 失效，再次扫描当前窗口；
     * 如果最大值小于新入窗口元素，则更新 maxIndex。
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (1 == nums.length || 1 == k) {
            return nums;
        }
        
        int[] result = new int[nums.length - k + 1];
        
        int maxIndex = 0;
        
        // 求出前 K 个元素的最大值的索引
        for (int i = 1; i < k; ++i) {
            if (nums[i] >= nums[maxIndex]) {
                maxIndex = i;
            }
        }
        result[0] = nums[maxIndex];
        
        // li 是滑动窗口的最左边的索引
        for (int li = 1; li < result.length; ++li) {
            // ri 是滑动窗口的最右边的索引
            int ri = li + k - 1;
            
            if (maxIndex < li) {
                maxIndex = li;
                for (int i = li + 1; i <= ri; ++i) {
                    if (nums[i] > nums[maxIndex]) {
                        maxIndex = i;
                    }
                }
            } else if (nums[ri] >= nums[maxIndex]) {
                maxIndex = ri;
            }
            
            result[li] = nums[maxIndex];
        }
        
        return result;
    }
    
    /**
     * 使用双端队列，递减顺序保存着窗口内的大值的索引，
     * 窗口每次滑动，尝试将右边新进入窗口的元素的索引放入队尾，
     * 如果新进入窗口的元素大于队尾元素，则将队尾出队，直到遇见一个比其大的值，
     * 将新进入窗口的元素的索引放入队尾，此时，队首就是窗口内最大值的索引。
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        if (1 == nums.length || 1 == k) {
            return nums;
        }
        
        int[] result = new int[nums.length - k + 1];
        
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            
            deque.offerLast(i);
            
            int l = i - k + 1;
            if (l < 0) {
                continue;
            }
            
            if (deque.peekFirst() < l) {
                deque.pollFirst();
            }
            
            result[l] = nums[deque.peekFirst()];
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        _239_滑动窗口最大值 o = new _239_滑动窗口最大值();
        
        int[] nums = { 1, 3, -1, -3, 5, 3, 6, 7};
        int[] result = o.maxSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(result));
    }
}
