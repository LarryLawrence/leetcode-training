package dfs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import test.Test;

/**
 * There are N rooms and you start in room 0.  Each room has a distinct number in 0, 1, 2, ..., N-1, and each room may have some keys to access the next room.
 * <p>
 * Formally, each room i has a list of keys rooms[i], and each key rooms[i][j] is an integer in [0, 1, ..., N-1] where N = rooms.length.  A key rooms[i][j] = v opens the room with number v.
 * <p>
 * Initially, all the rooms start locked (except for room 0).
 * <p>
 * You can walk back and forth between rooms freely.
 * <p>
 * Return true if and only if you can enter every room.
 * <p>
 * Example 1:
 * <p>
 * Input: [[1],[2],[3],[]]
 * Output: true
 * Explanation:
 * We start in room 0, and pick up key 1.
 * We then go to room 1, and pick up key 2.
 * We then go to room 2, and pick up key 3.
 * We then go to room 3.  Since we were able to go to every room, we return true.
 * Example 2:
 * <p>
 * Input: [[1,3],[3,0,1],[2],[0]]
 * Output: false
 * Explanation: We can't enter the room with number 2.
 * Note:
 * <p>
 * 1 <= rooms.length <= 1000
 * 0 <= rooms[i].length <= 1000
 * The number of keys in all rooms combined is at most 3000.
 * <p>
 * 20190301
 * 这题让我想到口袋妖怪里的船上的房间..
 */
public class KeysAndRooms {

    /**
     * dfs；这题我一开始觉得是backtracking，但后来发现其实是floodfill，不需要重置状态的，
     * 因为离开已经打开的房间之后不需要再重新锁上的，比如对于[[2,3],[],[2],[1,3,1]]。
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if (rooms == null || rooms.size() == 0) return false;
        if (rooms.size() == 1) return true;
        boolean res = dfs(rooms, 0, 0, new boolean[rooms.size()]);
        System.out.print(res);
        return res;
    }

    private boolean dfs(List<List<Integer>> rooms, int key, int vCount, boolean[] visited) {
        //        if (vCount >= rooms.size())//不能这么判断，过不了[[2,3],[],[2],[1,3,1]] 这个case。vCount相当于递归栈的最大层数，那么如果一个房间里有100把钥匙，递归层数可能只有2，但也能打开全部房间
        //            return true;
        if (allVisited(visited)) return true;
        if (key < 0 || key >= rooms.size()) return false;
        if (!visited[key]) {
            visited[key] = true;
            List<Integer> room = rooms.get(key);
            if (room.size() == 0) {
                if (dfs(rooms, -1, vCount + 1, visited)) return true;
            } else
                for (int i = 0; i < room.size(); i++) {
                    if (dfs(rooms, rooms.get(key).get(i), vCount + 1, visited)) return true;//找到true就不再递归，相当于全局标志位
                }
            //            visited[key] = false;//不需要backtracking
        }
        return false;
    }

    private boolean allVisited(boolean[] visited) {
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) return false;
        }
        return true;
    }


    /**
     * solution用的stack，确实更巧妙，跟树的遍历类似，有node就push直到empty
     */
    public boolean canVisitAllRooms___OFFICIALSOLUTION(List<List<Integer>> rooms) {
        boolean[] seen = new boolean[rooms.size()];
        seen[0] = true;
        Stack<Integer> stack = new Stack();
        stack.push(0);

        //At the beginning, we have a todo list "stack" of keys to use.
        //'seen' represents at some point we have entered this room.
        while (!stack.isEmpty()) { // While we have keys...
            int node = stack.pop(); // Get the next key 'node'
            for (int nei : rooms.get(node)) // For every key in room # 'node'...
                if (!seen[nei]) { // ...that hasn't been used yet
                    seen[nei] = true; // mark that we've entered the room
                    stack.push(nei); // add the key to the todo list
                }
        }

        for (boolean v : seen)  // if any room hasn't been visited, return false
            if (!v) return false;
        return true;
    }


    /*
    -------------------------------------------------------------------------------------------------------------------
     */

    public static void main(String args[]) {
        List<List<Integer>> rooms = new ArrayList<>();
        List<Integer> room0 = new ArrayList<>();
        room0.add(1);
        room0.add(3);
        List<Integer> room1 = new ArrayList<>();
        room1.add(3);
        room1.add(0);
        room1.add(1);
        List<Integer> room2 = new ArrayList<>();
        room2.add(2);
        List<Integer> room3 = new ArrayList<>();
        room3.add(0);
        rooms.add(room0);
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        new KeysAndRooms().canVisitAllRooms(rooms);
    }
}
