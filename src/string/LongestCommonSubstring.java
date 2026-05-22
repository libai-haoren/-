package string;

import java.util.HashSet;
import java.util.Random;

public class LongestCommonSubstring {
    public static void main(String[] args) {
        int len1 = 100000, len2 = 100000;
        s1 = randomString(len1);
        s2 = randomString(len2);

        long startTime = System.currentTimeMillis();
        int left = StringHash(); // 字符串哈希
        //System.out.println(longestCommonSubstring(s1, s2));
        System.out.println(left);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }

    private static int StringHash() {
        int n = s1.length();
        int m = s2.length();

        int max = Math.max(n,m);

        p = new long[max+1];  // p[i] = p^i

        h1 = new long[n+1];  // h1[i] = s1[1..i] 的哈希值
        h2 = new long[m+1];  // h2[i] = s2[1..i] 的哈希值

        // p[i] = p^i
        p[0] = 1;
        for (int i = 1; i <= max; i++) {
            p[i] = p[i - 1] * P;
        }
        // h1[i] = s1[1..i] 的哈希值
        /**
         * 比如 s1 = "abcdef"
         * h1[1] = s1[1] * P^(len-1) + s1[2] * P^(len-2) + s1[3] * P^(len-3) + s1[4] * P^(len-4) + s1[5] * P^(len-5) + s1[6] * P^(len-6)
         */
        h1[0] = 0;
        for (int i = 1; i <= n; i++) {
            h1[i] = h1[i - 1] * P + s1.charAt(i - 1);
        }
        // h2[i] = s2[1..i] 的哈希值
        h2[0] = 0;
        for (int i = 1; i <= m; i++) {
            h2[i] = h2[i - 1] * P + s2.charAt(i - 1);
        }
        //二分答案
        int left = 0 ;
        int right = Math.min(n,m);
        while(left < right){
            int mid = (left + right + 1) >> 1;
            if(check(mid)){
                left = mid;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }

    public static String longestCommonSubstring(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1]; // dp数组，dp[i][j]表示s1的前i个字符和s2的前j个字符的最长公共子串长度
        int maxLength = 0;
        int endIndex = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    if (dp[i][j] > maxLength) {
                        maxLength = dp[i][j];
                        endIndex = i;
                    }
                }
            }
        }
        return s1.substring(endIndex - maxLength, endIndex);
    }
    //======== 字符串哈希==========
    public static final int P = 131; // 祖传经验值，哈希冲突概率低

    static long[] p;
    static long[] h1;
    static long[] h2;

    static  String s1, s2 ;


    // 获取哈希值 l : 左边界 r : 右边界  h : 哈希数组
    // 对其前缀部分后删去
    static long getHash(long[] h ,int l , int r){
        return h[r] - h[l-1] * p[r-l+1];
    }

    static String randomString(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append((char) ('a' + rand.nextInt(26)));
        }
        return sb.toString();
    }

    static boolean check(int len) {

        HashSet<Long> set = new HashSet<>(); // 哈希集合，用于存储 s1 的所有长度为 len 的子串的哈希值

        int n = s1.length();
        int m = s2.length();

        // s1 所有长度 len 子串 hash
        for (int i = 1; i + len - 1 <= n; i++) {

            int j = i + len - 1;

            set.add(getHash(h1, i, j));
        }

        // s2 检查
        for (int i = 1; i + len - 1 <= m; i++) {

            int j = i + len - 1;

            long hash = getHash(h2, i, j);

            if (set.contains(hash)) {
                return true;
            }
        }

        return false;
    }


}
