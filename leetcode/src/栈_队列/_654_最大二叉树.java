package 栈_队列;

import java.util.Arrays;
import java.util.Stack;

import common.TreeNode;

/**
 * 给定一个不含重复元素的整数数组 nums 。一个以此数组直接递归构建的 最大二叉树 定义如下：
 * 二叉树的根是数组 nums 中的最大元素。
 * 左子树是通过数组中 最大值左边部分 递归构造出的最大二叉树。
 * 右子树是通过数组中 最大值右边部分 递归构造出的最大二叉树。
 * 返回有给定数组 nums 构建的 最大二叉树 。
 * 
 * 链接：https://leetcode-cn.com/problems/maximum-binary-tree
 * @author wjx
 *
 */
public class _654_最大二叉树 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return null;
        }
        
        return root(nums, 0, nums.length);
    }
    
    /**
     * 找出 [l, r) 的根节点
     */
    private TreeNode root(int[] nums, int l, int r) {
        if (l == r) {
            return null;
        }
        
        int maxIndex = l;
        for (int i = l + 1; i < r; ++i) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }
        
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = root(nums, l, maxIndex);
        root.right = root(nums, maxIndex + 1, r);
        
        return root;
    }
    
    public int[] parentIndexes(int[] nums) {
        if (null == nums || 0 == nums.length) {
            return null;
        }
        
        int[] lis = new int[nums.length];
        int[] ris = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < nums.length; ++i) {
            lis[i] = ris[i] = -1;
            
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                ris[stack.pop()] = i;
            }
            
            if (!stack.isEmpty()) {
                lis[i] = stack.peek();
            }
            
            stack.push(i);
        }
        
        int[] pis = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            if (-1 == lis[i] && -1 == ris[i]) {
                pis[i] = -1;
            } else if (-1 == lis[i]) {
                pis[i] = ris[i];
            } else if (-1 == ris[i]) {
                pis[i] = lis[i];
            } else if (lis[i] < ris[i]) {
                pis[i] = lis[i];
            } else {
                pis[i] = ris[i];
            }
        }
        
        return pis;
    }
    
    public static void main(String[] args) {
        _654_最大二叉树 o = new _654_最大二叉树();
        int[] nums = { 3, 2, 1, 6, 0, 5 };
        System.out.println(Arrays.toString(o.parentIndexes(nums)));
    }
}
