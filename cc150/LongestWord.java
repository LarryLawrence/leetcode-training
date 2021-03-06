package cc150;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 给定一组单词words，编写一个程序，找出其中的最长单词，且该单词由这组单词中的其他单词组合而成。若有多个长度相同的结果，返回其中字典序最小的一项，若没有符合要求的单词则返回空字符串。
 * <p>
 * 示例：
 * <p>
 * 输入： ["cat","banana","dog","nana","walk","walker","dogwalker"]
 * 输出： "dogwalker"
 * 解释： "dogwalker"可由"dog"和"walker"组成。
 * 提示：
 * 0 <= len(words) <= 200
 * 1 <= len(words[i]) <= 100
 * 20200808
 */
public class LongestWord {
    class Solution {
        /**
         * 题意：找出最长的由其他单词组成的单词，组成的单词可以重复用。
         * 解法：我一开始想加Memo，发现不行。看答案发现是先sort，从长的开始，然后把set中当前的单词remove掉，因为后面用不到这些更长的单词了。
         */
        public String longestWord(String[] words) {
            Arrays.sort(words, (o1, o2) -> {
                if (o1.length() == o2.length())
                    return o1.compareTo(o2); // 题目要求字典序最小
                else {
                    return Integer.compare(o2.length(), o1.length());
                }
            });

            HashSet<String> set = new HashSet<>(Arrays.asList(words));
            for (String word : words) {
                set.remove(word);
                if (canForm(word, set)) return word;
            }
            return "";
        }

        private boolean canForm(String word, HashSet<String> set) {
            if (word.equals("")) return true;
            for (int i = 1; i <= word.length(); i++) {
                String prev = word.substring(0, i), post = word.substring(i);
                if (set.contains(prev) && canForm(post, set)) {
                    return true;
                }
            }
            return false;
        }

        // WA!
        public String longestWord__WA(String[] words) {
            HashSet<String> set = new HashSet<>();
            for (String i : words) set.add(i);
            for (String word : words) {
                canForm(word, set, new HashMap<>());
            }
            return res;
        }

        String res = "";

        private boolean canForm(String word, HashSet<String> set, HashMap<String, Boolean> memo) {
            if (set.equals(word)) return true;
            if (memo.containsKey(word)) return memo.get(word);
            boolean can = false;
            for (int i = 1; i < word.length(); i++) {
                if (set.contains(word.substring(0, i)) && canForm(word.substring(i), set, memo)) {
                    if (word.length() > res.length()) res = word;
                    can = true;
                }
            }
            memo.put(word, can);
            return can;
        }
    }
}
