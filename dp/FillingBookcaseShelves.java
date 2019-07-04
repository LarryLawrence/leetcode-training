package dp;

import test.Test;

/**
 * We have a sequence of books: the i-th book has thickness books[i][0] and height books[i][1].
 * <p>
 * We want to place these books in order onto bookcase shelves that have total width shelf_width.
 * <p>
 * We choose some of the books to place on this shelf (such that the sum of their thickness is <= shelf_width), then build another level of shelf of the bookcase so that the total height of the bookcase has increased by the maximum height of the books we just put down.  We repeat this process until there are no more books to place.
 * <p>
 * Note again that at each step of the above process, the order of the books we place is the same order as the given sequence of books.  For example, if we have an ordered list of 5 books, we might place the first and second book onto the first shelf, the third book on the second shelf, and the fourth and fifth book on the last shelf.
 * <p>
 * Return the minimum possible height that the total bookshelf can be after placing shelves in this manner.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * <p>
 * Input: books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelf_width = 4
 * Output: 6
 * Explanation:
 * The sum of the heights of the 3 shelves are 1 + 3 + 2 = 6.
 * Notice that book number 2 does not have to be on the first shelf.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= books.length <= 1000
 * 1 <= books[i][0] <= shelf_width <= 1000
 * 1 <= books[i][1] <= 1000
 * <p>
 * 20190704
 */
public class FillingBookcaseShelves {


    /**
     * //dp[i]表示前i本书放在架子上的最小高度
     * //1. book[i]放在新的一层：dp[i] = dp[i - 1] + height
     * //2. book[i]放在上一层：dp[i] = min(dp[j] + max(height[j + 1], height[j + 2].. height[i])), where width[j + 1] + widht[j + 2].. +width[i] <= shelf_width
     * 这题思路理解起来不难，但是被序号搞死。。仔细体会一下。
     * 错误代码：
     */
    public int minHeightShelves(int[][] books, int shelf_width) {
        int dp[] = new int[books.length + 1];
        dp[0] = 0;
        for (int i = 0; i < books.length; i++) {
            int width = books[i][0], height = books[i][1];
            dp[i + 1] = dp[i] + height;//放在新的一层
            for (int j = i; j > 0 && width + books[j][0] <= shelf_width; j--) {
                width += books[j][0];
                height = Math.max(height, books[j][1]);
                //if (width > shelf_width) break;
                System.out.println("加上厚度：" + books[j][0]);
                dp[i + 1] = Math.min(dp[i + 1], dp[j - 1] + height);
            }
        }
        return dp[dp.length - 1];
    }

    public int minHeightShelves__(int[][] books, int shelf_width) {
        int[] dp = new int[books.length + 1];

        dp[0] = 0;

        for (int i = 1; i <= books.length; ++i) {
            int width = books[i - 1][0];
            int height = books[i - 1][1];
            dp[i] = dp[i - 1] + height;
            for (int j = i - 1; j > 0 && width + books[j - 1][0] <= shelf_width; --j) {
                height = Math.max(height, books[j - 1][1]);
                width += books[j - 1][0];
                System.out.println("*加上厚度：" + books[j - 1][0]);
                dp[i] = Math.min(dp[i], dp[j - 1] + height);
            }
        }
        return dp[books.length];
    }

    public static void main(String args[]) {
        int[][] arr = new int[][]{{7, 3}, {8, 7}, {2, 7}, {2, 5}};
        new Test().minHeightShelves(arr, 10);
        new Test().minHeightShelves__(arr, 10);
    }
}