package 树状数组;

import java.io.*;
import java.util.StringTokenizer;

public class 单点修改区间求和 {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    public static int MAXN = 500001;

    // 原始数组的信息维护在树状数组中
    public static long[] tree = new long[MAXN];

    public static int n , m;

    public static int lowbit(int i){
        return i & -i;
    }

    // 返回1~i范围累加和
    public static long sum(int i) {
        long ans = 0;
        while (i > 0) {
            ans += tree[i];
            i -= lowbit(i);
        }
        return ans;
    }

    public static long range(int l, int r) {
        return sum(r) - sum(l - 1);
    }

    public static void add(int i ,int v){
        while(i <= n){
            tree[i]+=v;
            i += lowbit(i);
        }
    }



    public static void main(String[] args) {
          //单点修改，区间求和
           n = nextInt();
           m = nextInt();
          for(int i =1;i<=n;i++){
              add(i,nextInt());
          }
          for(int i =0;i<m;i++){
              int op = nextInt();
              int x = nextInt();
              int kOry = nextInt();
              if(op == 1){
                  add(x,kOry);
              }else if(op == 2){
                  pw.println(range(x,kOry));
              }
          }
          pw.flush();
    }

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

}
