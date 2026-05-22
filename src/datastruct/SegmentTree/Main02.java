package datastruct.SegmentTree;

import java.io.*;
import java.util.StringTokenizer;
// + \ * 混合
public class Main02 {
    public static int MAXN = (int) 1e5 + 10;
    public static int[] arr = new int[MAXN];
  //  public static long[] sum = new long[MAXN << 2];
//    public static long[] add = new long[MAXN << 2];
    public static int[] max = new int[MAXN << 2];

    // 向上更新：合并左右子树的和
    public static void pushUp(int i) {

    }

    // 构建线段树
    public static void build(int l, int r, int i) {
        if (l == r) {

            return;
        }
        int mid = (l + r) >> 1;
        build(l, mid, i << 1);
        build(mid + 1, r, i << 1 | 1);
        pushUp(i);
    }

    //
    public static void update(int index , int v ,int l, int r ,int i){
        if(l == r && l == index){

            return;
        }
        int mid = (l + r) >> 1;
        if (index <= mid) update(index, v, l, mid, i << 1);
        if (index > mid) update(index, v, mid + 1, r, i << 1 | 1);
        pushUp(i);
    }


    // 区间查询操作
    public static int query(int jobl, int jobr, int l, int r, int i) {
        if (jobl <= l && r <= jobr) {
            return max[i];
        }
        int mid = (l + r) >> 1;
        int ans = 0;
        if (jobl <= mid) ans += Math.max(0, query(jobl, jobr, l, mid, i << 1)) ;
        if (jobr > mid) ans += Math.max(0, query(jobl, jobr, mid + 1, r, i << 1 | 1)) ;
        return ans;
    }

    // 快速输入输出
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

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

    public static void main(String[] args) {
        int n = nextInt();
        int m = nextInt();

        for (int i = 1; i <= n; i++) {
            arr[i] = nextInt();
        }

        build(1, n, 1);

        while (m-- > 0) {
            int op = nextInt();
            int x = nextInt();
            int y = nextInt();
            if (op == 1) {
                if(x<y){
                    pw.println(query(x, y, 1, n, 1));
                }else{
                    pw.println(query(y, x, 1, n, 1));
                }
            } else if (op == 2) {
                update(x, y, 1, n, 1);
            }
        }

        pw.flush();
    }
}