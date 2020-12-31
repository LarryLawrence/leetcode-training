package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import basics.Interval;

/**
 * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
 * Example 1:
 * Input: [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
 * Example 2:
 * <p>
 * Input: [[1,2],[1,2],[1,2]]
 * Output: 2
 * Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
 * Example 3:
 * <p>
 * Input: [[1,2],[2,3]]
 * Output: 0
 * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 * <p>
 * <p>
 * Note:
 * <p>
 * You may assume the interval's end point is always bigger than its start point.
 * Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
 * <p>
 * 56 Merge Intervals <- very similar, i did it with just 3 lines different
 * 252 Meeting Rooms
 * 253 Meeting Rooms II
 * 452 Minimum Number of Arrows to Burst Balloons
 * <p>
 * Practice them in a row for better understanding 😉
 */
public class NonoverlappingIntervals {
    /**
     * 题意：在一个数组里移除最小个数的区间，让剩下的区间都不重叠
     * 【官方解法】经典的interval scheduling问题，贪心做法，按照end排序，顺序地统计没有overlap的interval个数（https://en.wikipedia.org/wiki/Interval_scheduling#Interval_Scheduling_Maximization）
     * 相当于要先求最大的不重叠的interval的个数。
     * Actually, the problem is the same as "Given a collection of intervals, find the maximum number of intervals that are non-overlapping."
     * (the classic Greedy problem: Interval Scheduling). With the solution to that problem, guess how do we get the minimum number of intervals to remove? : )
     * Sorting Interval.end in ascending order is O(nlogn), then traverse intervals array to get the maximum number of non-overlapping intervals is O(n). Total is O(nlogn).
     * <p>
     * - Selecting the intervals that start earliest is not an optimal solution, because if the earliest interval happens to be very long, accepting it would make us reject many other shorter requests.
     * - The following greedy algorithm does find the optimal solution:
     * Select the interval, x, with the earliest finishing time.
     * Remove x, and all intervals intersecting x, from the set of candidate intervals.
     * Repeat until the set of candidate intervals is empty.
     * 20200115 review
     * 两个要点：
     * 1. 可以按照start time或者finish time排序，但是用start time排序的时候要额外加个条件保证结束早的interval在前面
     * 2. 统计的是【不重合的个数】，而非重合的个数；否则会fails [[1,100],[11,22],[1,11],[2,12]], output 3, expected 2
     * 对于2，可以想象如果有N个连续重合的interval，但是每相隔1个interval都彼此不重合，那么显然不能统计重合的个数。
     * Approach 1
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int end = Integer.MIN_VALUE, nonOverlaps = 0;
        for (int i = 0; i < intervals.length; i++) {
            int[] iv = intervals[i];
            if (iv[0] >= end) {
                nonOverlaps++;// 统计不重合的个数
                end = iv[1];
            }
        }
        return intervals.length - nonOverlaps;
    }

    /**
     * Approach 2
     * 用start time排序，需要加一个条件保证结束早的interval在前面
     */
    public int eraseOverlapIntervals__(int[][] intervals) {
        if (intervals.length <= 1) return 0;
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? a[0] - b[0] : a[1] - b[1]); // a[0] == b[0]的时候要保证结束早的interval在前面
        int nonOverLapped = 1, end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= end) {
                nonOverLapped++;
                end = intervals[i][1];
            }
        }
        return intervals.length - nonOverLapped;
    }

    /**
     * Approach 3
     * 记录需要移除的区间(重合的区间)
     * credit to: https://leetcode-cn.com/problems/non-overlapping-intervals/solution/wu-zhong-die-qu-jian-ji-bai-liao-100de-y-kkzr/
     */
    public int eraseOverlapIntervals___(int[][] intervals) {
        if (intervals.length == 0)
            return 0;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int end = intervals[0][1];
        int count = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) {
                // 如果重叠了，必须要移除一个，所以count要加1，
                // 然后更新尾部的位置，我们取尾部比较小的
                end = Math.min(end, intervals[i][1]);
                count++;
            } else {
                // 如果没有重叠，就不需要移除，只需要更新尾部的位置即可
                end = intervals[i][1];
            }
        }
        return count;
    }

    /**
     * 这题是说，在一个数组里移除最小个数的区间，让剩下的区间都不重叠
     * 我在纸上画了很多情况试图找规律，最后找到一个规律: 在一个区间内如果包含了其它区间的结尾，就把当前区间remove掉。
     * 【我的解法】
     */
    public int eraseOverlapIntervals_(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]);
        List<Pair> list = new ArrayList<>();
        int res = 0;
        for (int i = 0; i < intervals.length; i++) {
            list.add(new Pair(i, intervals[i][0], intervals[i][1]));
        }
        Iterator<Pair> iterator = list.iterator();
        while (iterator.hasNext()) {//for(Pair p : list)会产生“ConcurrentModificationException”，因为在iterate的时候remove了。所以改用iterator。
            Pair p = iterator.next();
            for (Pair q : list) {
                boolean flag = false;
                if (p.id == q.id) continue;
                for (int i = p.start; i <= p.end; i++) {
                    if (q.end > p.start && q.end <= p.end) {
                        iterator.remove();
                        res++;
                        flag = true;
                        break;
                    }
                }
                if (flag)
                    break;
            }
        }
        return res;
    }

    class Pair {
        int id;
        int start;
        int end;

        Pair(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }
    }
}
