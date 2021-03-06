package array;

/**
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 * <p>
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 * For example,
 * <p>
 * Consider the following matrix:
 * <p>
 * [
 * [1,   3,  5,  7],
 * [10, 11, 16, 20],
 * [23, 30, 34, 50]
 * ]
 * Given target = 3, return true.
 * Created by DrunkPiano on 2017/2/8.
 */

public class Searcha2DMatrix {

    /**
     * 题意：一个二维矩阵，它每一行都是递增的，并且下一行第一个比上一行最后一个大。问target是否在matrix里。
     * 解法：BinarySearch。
     * Don't treat it as a 2D matrix, just treat it as a sorted list，
     * 这题跟240. Search a 2D Matrix II的区别是，这题可以当做一个折行的list，时间是O(log n)；240那题时间是O(m + n)
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) return false;
        int length = matrix.length * matrix[0].length;
        int left = 0;
        int right = length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int row = mid / matrix[0].length;
            int col = mid % matrix[0].length;
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                left = mid + 1;
            } else right = mid - 1;

        }
        return false;
    }
}
