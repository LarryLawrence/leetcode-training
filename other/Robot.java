package other;

import java.util.HashSet;

/**
 * 力扣团队买了一个可编程机器人，机器人初始位置在原点(0, 0)。小伙伴事先给机器人输入一串指令command，机器人就会无限循环这条指令的步骤进行移动。指令有两种：
 * <p>
 * U: 向y轴正方向移动一格
 * R: 向x轴正方向移动一格。
 * 不幸的是，在 xy 平面上还有一些障碍物，他们的坐标用obstacles表示。机器人一旦碰到障碍物就会被损毁。
 * <p>
 * 给定终点坐标(x, y)，返回机器人能否完好地到达终点。如果能，返回true；否则返回false。
 * 示例 1：
 * <p>
 * 输入：command = "URR", obstacles = [], x = 3, y = 2
 * 输出：true
 * 解释：U(0, 1) -> R(1, 1) -> R(2, 1) -> U(2, 2) -> R(3, 2)。
 * 示例 2：
 * <p>
 * 输入：command = "URR", obstacles = [[2, 2]], x = 3, y = 2
 * 输出：false
 * 解释：机器人在到达终点前会碰到(2, 2)的障碍物。
 * 示例 3：
 * <p>
 * 输入：command = "URR", obstacles = [[4, 2]], x = 3, y = 2
 * 输出：true
 * 解释：到达终点后，再碰到障碍物也不影响返回结果。
 * <p>
 * <p>
 * 限制：
 * <p>
 * 2 <= command的长度 <= 1000
 * command由U，R构成，且至少有一个U，至少有一个R
 * 0 <= x <= 1e9, 0 <= y <= 1e9
 * 0 <= obstacles的长度 <= 1000
 * obstacles[i]不为原点或者终点
 * 20190924，力扣秋季赛第三题
 */
public class Robot {
    /**
     * 这题也是模拟题，我一开始想把所有路径都模拟一遍，果然超时；
     * 然后发现一次command之后重复的路径都可以由公式算出来，思路对的。
     * 不过忘记算入("0#0")了，浪费了很多时间。。
     * <p>
     * 秋季赛第四第五题都是hard，我就先交卷了😄
     */
    public boolean robot(String command, int[][] obstacles, int x, int y) {
        int[] path = new int[]{0, 0};
        HashSet<String> dots = new HashSet<>();
        dots.add("0#0");
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == 'U') path[1]++;
            else path[0]++;
            dots.add(path[0] + "#" + path[1]);
        }
        for (int[] o : obstacles) {
            if (o[0] > x || o[1] > y) continue;
            int div = o[0] / path[0];
            String key = (o[0] - div * path[0]) + "#" + (o[1] - div * path[1]);
            if (dots.contains(key))
                return false;
        }
        int div = x / path[0];
        return dots.contains((x - div * path[0]) + "#" + (y - div * path[1]));
    }
}
