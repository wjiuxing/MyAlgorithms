package 栈_队列;

import java.util.Arrays;
import java.util.Stack;

/**
 * 请根据每日 气温 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 0 来代替。
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 * 
 * 链接：https://leetcode-cn.com/problems/daily-temperatures
 * @author wjx
 *
 */
public class _739_每日温度 {
    /**
     * 倒推发：最后一天没有再升温的可能，所以必然为 0，从倒数第二位开始倒序遍历数组，
     * 每次遍历到新元素时，从新元素后一位开始都是已知结果，可以从已知的结果推断。
     */
    public int[] dailyTemperatures(int[] T) {
        if (null == T || 0 == T.length) {
            return null;
        }
        
        int[] result= new int[T.length];
        
        for (int i = T.length - 2; i >= 0; --i) {
            int j = i + 1;
            while (true) {
                if (T[i] < T[j]) {
                    result[i] = j - i;
                    break;
                } else if (result[j] == 0) {
                    result[i] = 0;
                    break;
                }
                j += result[j];
            }
        }
        
        return result;
    }
    
    /**
     * 单调递减栈，遍历一遍数组，将索引压入栈，使其保证单调递减
     * 如果新压入的元素大于栈顶元素，那么新元素就是栈顶元素右边第一个大于它的元素。
     */
    public int[] dailyTemperatures2(int[] T) {
        if (null == T || 0 == T.length) {
            return null;
        }
        
        int[] result = new int[T.length];
        
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < T.length; ++i) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                result[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            
            stack.push(i);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        _739_每日温度 o = new _739_每日温度();
        int[] T =  { 73, 74, 75, 71, 69, 72, 76, 73 };
        int[] result = o.dailyTemperatures(T);
        System.out.println(Arrays.toString(result));
    }
}
