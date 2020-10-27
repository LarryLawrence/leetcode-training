package dp;

/**
 * Given a string S and a string T, count the number of distinct subsequences of T in S.
 * <p>
 * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters
 * without disturbing the relative positions of the remaining characters. (ie, "ACE" is a subsequence of "ABCDE" while "AEC" is not).
 * Here is an example:
 * S = "rabbbit", T = "rabbit"
 * <p>
 * Return 3.
 * Created by DrunkPiano on 2017/3/1.
 */

public class DistinctSubsequences {

    /**
     * 题意：给你s和t两个string，问t在s中出现的个数。
     * 解法：dp[i][j]表示t的前i个字符串在s的前j个字符串中出现的个数。
     */
    public int numDistinct(String s, String t) {
        int[][] dp = new int[t.length() + 1][s.length() + 1];
        for (int j = 0; j < s.length() + 1; j++) dp[0][j] = 1;
        for (int i = 1; i < t.length() + 1; i++) {
            for (int j = 1; j < s.length() + 1; j++) {
                if (t.charAt(i - 1) == s.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + dp[i][j - 1]; // dp[i - 1][j - 1]: 先不考虑第i位，t[i-1]在s[j-1]中出现次数
                else
                    // dp[i][j - 1]: 已知s[j] != t[i]，s[j]中包含t[i]的个数等于s[j - 1]中包含t[i]的个数
                    dp[i][j] = dp[i][j - 1];
            }
        }
        return dp[t.length()][s.length()];
    }

    // 如果把i,j交换，dp[i][j]表示t的前j个字符串在s的前i个字符串中出现的个数的写法
    public int numDistinct__(String s, String t) {
        if (s.length() == 0 || t.length() == 0) return 0;
        int dp[][] = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i < s.length() + 1; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i < s.length() + 1; i++)
            for (int j = 1; j < t.length() + 1; j++) {
                if (s.charAt(i - 1) != t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    //dp[i - 1][j - 1]的意思是，既然s[i]和t[j]相等，那么在同时加上这两个数之前的结果都可以算进去
                    //dp[i - 1][j]的意思是，不考虑加上s[i]，但是加入t[j]，满足条件的个数。
                    //至于为什么不计算dp[i][j - 1]，因为是求s中有多少个子序列可以组成t，t是固定的。
                    //rabb, rab = rab # ra + rab # rab
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
                }
            }
        return dp[s.length()][t.length()];
    }
}
