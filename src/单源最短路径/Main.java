package 单源最短路径;






import java.io.*;
import java.util.*;


public class Main {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static final int MOD = 1000000007;

    static int MAXN =(int) 1e5+3;


    static int[] arr = new int[MAXN];
    static class Edge{
        int to;
        int w ;
        public Edge(int t ,int w){
            to = t ;
            this.w =w;
        }
    }


    public static void main(String[] args) {
        int n = nextInt();
        int m = nextInt();
        int s = nextInt();
        ArrayList<Edge>[] grip = new ArrayList[n+1];
        Arrays.setAll(grip,i -> new ArrayList<>());
        for(int i =0;i<m;i++){
            int ui = nextInt();
            int vi = nextInt();
            int w = nextInt();
            grip[ui].add(new Edge(vi,w));
        }
        PriorityQueue<int[]> q = new PriorityQueue<>((a,b) -> a[0] -b[0]);
        int[] dis = new int[n+1];
        boolean[] vis = new boolean[n+1];
        Arrays.fill(dis,Integer.MAX_VALUE);
        dis[s] = 0;
        q.add(new int[]{0,s});
        while(!q.isEmpty()){
            int []x = q.poll();
            int curd = x[0];
            int curX = x[1];
            if(vis[curX]) continue;
            vis[curX] = true;
            for(Edge e : grip[curX]){
                int nx = e.to;
                int nd = e.w + curd;
                if(!vis[nx] && nd<dis[nx]){
                    q.add(new int[]{nd,nx});
                    dis[nx] = nd;
                }
            }
        }
        for(int i =1 ;i<=n;i++){
            pw.print(dis[i]+" ");
        }


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

