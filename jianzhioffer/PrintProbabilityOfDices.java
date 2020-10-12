package jianzhioffer;

/**
 * 把n个骰子扔在地上，所有骰子朝上一面的点数之和为s。输入n，打印出s的所有可能的值出现的概率。
 * 你需要用一个浮点数数组返回答案，其中第 i 个元素代表这 n 个骰子所能掷出的点数集合中第 i 小的那个的概率。
 * 示例 1:
 * 输入: 1
 * 输出: [0.16667,0.16667,0.16667,0.16667,0.16667,0.16667]
 * 示例 2:
 * 输入: 2
 * 输出: [0.02778,0.05556,0.08333,0.11111,0.13889,0.16667,0.13889,0.11111,0.08333,0.05556,0.02778]
 * 限制：
 * 1 <= n <= 11
 */
public class PrintProbabilityOfDices {
    /**
     * 题意：n个骰子随机扔，点数之和为s。输入n，打印出s的所有可能的值出现的概率。
     * 解法：概率dp；一开始写了个dfs+cache发现不太好写。这里是三个for，分别用来枚举
     * 1. [2,i]个骰子；
     * 2. i-1个骰子时的概率；
     * 3. 第i个骰子投出的数字
     */
    public double[] twoSum(int n) {
        double[] pre = new double[]{1 / 6d, 1 / 6d, 1 / 6d, 1 / 6d, 1 / 6d, 1 / 6d};
        for (int i = 2; i <= n; i++) { // i个骰子
            double[] cur = new double[6 * i - i + 1];// [n, 6n]
            for (int p = 0; p < pre.length; p++) { // 对于有i - 1个骰子时投出的所有的值的概率，枚举当次投掷出的数字
                for (int c = 0; c < 6; c++) {
                    cur[p + c] += pre[p] / 6;
                }
            }
            pre = cur;
        }
        return pre;
    }
}
