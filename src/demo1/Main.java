package demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.*;

public class Main {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static StringTokenizer st ;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n =scan.nextInt();
        int m =scan.nextInt();
        int[][] g = new int[n][m];
        for(int i =0;i<n;i++){
            for(int k =0;k<m;k++){
                g[i][k] =scan.nextInt();
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->(a[2]-b[2]));
        int[][]dis = new int[n][m];
        int[][] dir = {{1,0},{-1,0},{0,-1},{0,1}};
        pq.add(new int[]{0,0,g[0][0]});
        for(int i =0;i<n;i++) Arrays.fill(dis[i],-1);
        dis[0][0] = g[0][0];
        while(!pq.isEmpty()){
            int[] p = pq.poll();
            int x = p[0];
            int y = p[1];
            int t = p[2];

            for(int i =0;i<4;i++){
                int nx = x+dir[i][0];
                int ny = y+dir[i][1];
                int nt = t+g[nx][ny];
                if(dis[nx][ny] == -1 || dis[nx][ny] > nt ){
                    dis[nx][ny] = nt;
                    pq.add(new int[]{nx,ny,nt});
                }
            }

        }
        int ans =0 ;
        for(int i =0 ; i< n;i++){
            for(int j =0;j<m;j++){
                ans = Math.max(ans,dis[i][j]);
            }
        }
        System.out.println(ans);

        scan.close();

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
}
