package 单源最短路径;






import java.io.*;
import java.util.*;


public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static final int MOD = 1000000007;

    static int MAXN =(int) 1e5+3;
    static int MAXM = 100;

    static int[] to = new int[MAXM];
    static int[] next = new int[MAXM];
    static int[] weight = new int[MAXM];
    static int []head  = new int[MAXN];
    static int idx ;

    public static void add(int u ,int v ,int w){
        weight[idx] = w ;
        to[idx] = v;
        next[idx] = head[u];
        head[u] = idx++;
    }
    public static int n, m, k, s, t ;
    public static boolean[][]vis = new boolean[MAXN][k+1];
    public static int[][] dis = new int[MAXN][k+1];
    public static PriorityQueue<int[]> q = new PriorityQueue<>((a,b) -> a[2] -b[2]);
    public static void build(){
        idx = 1;
        for(int i = 0; i < n; i++){
            head[i] = 0;
            Arrays.fill(dis[i], Integer.MAX_VALUE);
            Arrays.fill(vis[i], false);
        }
        q.clear();
    }



    static int[][] dir = {{0,-1},{0,1},{1,0},{-1,0}};

    public static void main(String[] args) {
         n = nextInt();
         m = nextInt();
         k = nextInt();

         s = nextInt();
         t = nextInt();

         build();

        for(int i =0;i<m;i++){
            int ui = nextInt();
            int vi = nextInt();
            int w = nextInt();
            add(ui,vi,w);
            add(vi,ui,w);
        }

        dis[s][k] = 0;
        q.add(new int[]{k,s,0});
        int ans  = -1;
        while(!q.isEmpty()){
            int []x = q.poll();
            int curK = x[0];
            int curX = x[1];
            int curD = x[2];
            if(curX == t){
                ans = curD;
                break;
            }
            if(vis[curX][curK]) continue;
            vis[curX][curK] = true;
            for(int i = head[curX]; i > 0; i = next[i]){
                int nx = to[i];
                int nd = weight[i] + curD;
                // 本次免费
                if(curK > 0  && dis[nx][curK-1] > dis[nx][curK]){
                    q.add(new int[]{curK-1,nx,dis[nx][curK]});
                }
                // 本次收费
                if(nd<dis[nx][curK]){
                    q.add(new int[]{nd,nx});
                    dis[nx][curK] = nd;
                }
            }
        }
        pw.print(ans);



        pw.flush();


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

