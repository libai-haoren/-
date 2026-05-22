package 树状数组;

import java.io.*;
import java.util.StringTokenizer;

/**
 *  使用树状数组的原理维护差分数组，实现区间修改，单点查询
 */
public class 区间修改单点查询 {

        private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        private static StringTokenizer st;
        static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        public static int MAXN = 500002;


        // 原始数组的信息维护在树状数组中
        public static int[] tree = new int[MAXN];

        public static int n , m;

        public static int lowbit(int i){
            return i & -i;
        }
        public static void add(int i, int v){
            while(i<=n){
                tree[i] += v;
                i += lowbit(i);
            }
        }
        public static int sum(int i){
            int ans = 0;
            while(i > 0){
                ans += tree[i];
                i -= lowbit(i);
            }
            return ans;
        }




        public static void main(String[] args) {
            //单点修改，区间求和
            n = nextInt();
            m = nextInt();
            for(int i =1;i<=n;i++){
                int x = nextInt();
               add(i,x);
               add(i+1,-x);
            }
            for(int i =0;i<m;i++){
                int op = nextInt();
                int x = nextInt();
                if(op == 1){
                    int y = nextInt();
                    int k = nextInt();
                    add(x,k);
                    add(y+1,-k);
                }else if(op == 2){
                    pw.println(sum(x));
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


