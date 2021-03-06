package dp;

/**
 * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
 * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
 * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
 * 示例:
 * 输入: [1,2,3,0,2]
 * 输出: 3
 * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
 * Created by DrunkPiano on 08/06/2017.
 */

public class BestTimetoBuyandSellStockwithCooldown {
	/**
	 * 题意：给你一串股票价格，有一个要求：卖出后第二天不能买；求最大收益。
	 * 解法：以前做过，今天又尝试做了一下，还是想了一阵子；我一开始想用当天是否卖出(sold/not_sold)来推转移方程，后来觉得不对，改用当天是否买入(buy/not_buy)；再后来又发现应该用是否持有(hold/not_hold)来做；
	 * 这样就迎刃而解地推出了转移方程；但是有个地方我写错了，那就是漏掉了hold[1]的状态；hold[1] = Math.max(-prices[0], -prices[1]);昨天买入或今天买入的最大收益。
	 * * 第i天结束时在持有   hold[i] = Math.max(hold[i - 1], not_hold[i - 2] - prices[i]);
	 * * 第i天结束时不在持有 not_hold[i] = Math.max(hold[i - 1] + prices[i], not_hold[i - 1]);
	 * 变量名很重要..
	 */
	public int maxProfit(int[] prices) {
		int n = prices.length;
		if (n <= 1) return 0;
		int[] hold = new int[n], not_hold = new int[n]; // 持有的状态，没持有的状态
		hold[0] = -prices[0];
		hold[1] = Math.max(-prices[0], -prices[1]); // 漏掉了
		not_hold[1] = Math.max(prices[1] - prices[0], 0);
		for (int i = 2; i < n; i++) {
			hold[i] = Math.max(hold[i - 1], not_hold[i - 2] - prices[i]);
			not_hold[i] = Math.max(hold[i - 1] + prices[i], not_hold[i - 1]);
		}
		return not_hold[n - 1];
	}
}