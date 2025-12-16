import graph.Prim;
import graph.test;

import java.io.*;
import java.util.*;


public class Main {
    static private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static private StringTokenizer st;
    static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static void main(String[]args) throws CloneNotSupportedException, ClassNotFoundException {
        test  a= new test();
        a.setName("test");
        a.setScores(Arrays.asList(10,20,30));
        test b = a.clone();
        System.out.println(a.getName() == b.getName());
        System.out.println(a.getScores());
        Class<?> clazz = Prim.class;
        Class<?> prim = Class.forName("Prim");



    }
    public static int binarySearch(int []num,int target){
        int left = -1;

        int right = num.length-1;
        while(left<=right){
            int mid = left+(right-left) /2;
            if(mid == -1) return -1;
            if(num[mid]<=target){
                left = mid+1;
            }else{
                right= mid-1;
            }
        }
        return left;
    }

    // 检查是否可以将字符串分成不超过k段，且每段权值不超过maxValue
    private static boolean canDivide(String s, int k, long maxValue) {
        int segments = 0;
        int count0 = 0, count1 = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '0') {
                count0++;
            } else {
                count1++;
            }

            // 如果当前段的权值超过maxValue，需要分段
            if ((long) count0 * count1 > maxValue) {
                segments++;
                if (segments >= k) {
                    return false;
                }

                // 开始新的一段，包含当前字符
                count0 = (c == '0') ? 1 : 0;
                count1 = (c == '1') ? 1 : 0;
            }
        }

        // 最后一段
        if (count0 > 0 || count1 > 0) {
            segments++;
        }

        return segments <= k;
    }

    // 输入输出工具方法
    public static String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public static int nextInt() {
        return Integer.parseInt(next());
    }

    public static long nextLong() {
        return Long.parseLong(next());
    }

    public static double nextDouble() {
        return Double.parseDouble(next());
    }

    public static String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}