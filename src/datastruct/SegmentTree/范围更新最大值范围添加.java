package datastruct.SegmentTree;






import java.io.*;
import java.util.*;


public class 范围更新最大值范围添加 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static final int MOD = 1000000007;

    static int MAXN =(int) 1e6+3;


    static int[] arr = new int[MAXN];
    //static int[] sum  = new int[MAXN<<2];
    static   long[] add = new long[MAXN<<2];
    static long[] max = new long[MAXN<<2];
    static long[]change = new long[MAXN<<2];
    static boolean[] update = new boolean[MAXN<<2];

    public static void main(String[] args) {
        int  n = nextInt();
        int m = nextInt();
        for(int i =1;i<=n;i++){
            arr[i] = nextInt();
        }
        build(1,n,1);

        while(m-->0){
            int op = nextInt();
            int l = nextInt();
            int r = nextInt();

            if(op == 1){
                long x = nextLong();
                change(l,r,x,1,n,1);
            }else if(op == 2){
                int x = nextInt();
                add(l,r,x,1,n,1);
            }else{
                pw.println(query(l,r,1,n,1));;
            }

        }
        pw.flush();


    }
    public static void lazy(int i , long v){
        if(update[i]){
            change[i] += v;
        }else{
            add[i] += v;
        }
        max[i] += v;
        // sum[i] += v;
    }
    public static void updateLazy(int i ,long v){
        add[i] = 0;
        max[i] = v;
        update[i] = true;
        change[i] = v;
    }
    public static void up(int i){
        max[i] = Math.max(max[i<<1],max[i<<1|1] );
        //  sum[i] = sum[i<<1] + sum[i<<1|1];
    }
    public static void down(int i){
        if(update[i]){
            updateDown(i);
        }
        if(add[i] != 0){
            lazy(i<<1,add[i]);
            lazy(i<<1|1,add[i]);
            add[i] = 0;
        }
    }
    public static void build(int l ,int r , int i){
        if(l == r){
            max[i] = arr[l];
            //sum[i] = arr[l];
        }else{
            int mid = (l+r)>>1;
            build(l,mid,i<<1);
            build(mid+1,r,i<<1|1);
            up(i);
        }
    }
    public static void add(int jobl ,int jobr ,int v,   int l ,int r ,int i){
        if(jobl<= l && jobr>=r){
            lazy(i,v);
        }else{
            int mid = (r+l) >>1;
            down(i);
            if(jobl<=mid) add(jobl, jobr, v ,l, mid, i<<1);
            if(jobr>mid) add(jobl, jobr,v, mid+1, r, i<<1|1);
            up(i);
        }

    }
    public static long query(int jobl ,int jobr , int l ,int r ,int i){
        if(jobl<= l && jobr >= r){
            return max[i];
        }else{
            long ans = Long.MIN_VALUE;;
            int mid = (r+l) >>1;
            down(i);
            if(jobl<=mid) ans = Math.max(ans,query(jobl, jobr, l, mid, i<<1));
            if(jobr>mid) ans =Math.max(ans,query(jobl, jobr, mid+1, r, i<<1|1)) ;
            return ans;
        }
    }
    public static void updateDown(int i){
        if(update[i]){
            updateLazy(i<<1,change[i]);
            updateLazy(i<<1|1,change[i]);
            update[i] = false;
            add[i] = 0;
        }
    }
    public static void change(int jobl, int jobr ,long v ,int l ,int r ,int i){
        if(jobl<= l && jobr>=r){
            updateLazy(i,v);
        }else{
            int mid = (r+l) >>1;
            down(i);
            if(jobl<=mid) change(jobl, jobr, v ,l, mid, i<<1);
            if(jobr>mid) change(jobl, jobr,v, mid+1, r, i<<1|1);
            up(i);
        }

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
