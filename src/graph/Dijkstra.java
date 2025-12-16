package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    class Solution {
        public int networkDelayTime(int[][] times, int n, int k) {
            //创建邻接表并初始化
            List<int []>[] grip = new ArrayList[n];
            Arrays.setAll(grip, i->new ArrayList<>());

            for(int []t:times){
                grip[t[0]-1].add(new int[]{t[1]-1,t[2]});
            }
            // 使用一维数组来记录源节点到其他节点的距离
            int [] dis = new int [n];
            Arrays.fill(dis,Integer.MAX_VALUE);
            dis[k-1] =0;

            // 使用优先队列来存储到当前节点的最近的节点
            PriorityQueue<int []> pq= new PriorityQueue<>();
            int maxDis = 0 ;
            int left = n; // 判断是否可以连接到全部的节点
            // 存入当前节点
            pq.offer(new int[]{0,k-1});
            while(!pq.isEmpty()){
                int []cur=pq.poll();
                int x = cur[1];
                int curDis = cur[0];
                if(curDis > dis[x]){
                    continue;
                }
                // 记录当前最短路径
                maxDis = curDis;
                left--;
                for(int []e : grip[x]){
                    int y = e[0];
                    int newDis = curDis + e[1];
                    if (newDis < dis[y]) {
                        dis[y] = newDis; // 更新 x 的邻居的最短路
                        pq.offer(new int[]{newDis, y});
                    }
                }
            }
            return left ==0 ? maxDis :-1;
        }
    }
}
