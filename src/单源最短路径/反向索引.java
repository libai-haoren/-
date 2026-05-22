package 单源最短路径;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 反向索引 {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;
    static private PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    static final  int MAXN = (int) 1e5+2;
    static final int MAXM = (int) 2e5+2; // 边的数量

   // =====  链式前向星 ========
    static int[] to = new int[MAXM];   //第i条边指向的节点
    static int[] weight = new int[MAXM];    // 第i条边的权重
    static int[] next = new int[MAXM]; //下一条边的编号
    static int[] head = new int[MAXN]; // 节点u的第一条边的编号
    static int idx;              //当前边的编号
    public static void add(int u ,int v ,int w){
        to[idx] = v;
        weight[idx] = w;
        // 头插法
        next[idx] = head[u];
        head[u] = idx++;
    }
    // ===== 反向索引==========
    static int[] heap = new int[MAXN];  // 堆数组
    static int[] pos = new int[MAXN];   //反向索引数组 ：记录每一个节点在堆中的索引
    static int[] dis = new int[MAXN];
    static int  heapSize;


    static int n , m , s;







    public static void main(String[] args) {
           n = nextInt();
           m = nextInt();
           s = nextInt();
        build();

        while(m-- > 0){
            int u = nextInt();
            int v = nextInt();
            int w = nextInt();
            add(u,v,w);
        }

        addOrUpdateOrIgnore(s,0);

        while(!isEmpty()){

            int v = pop();

            for(int ei = head[v]; ei > 0; ei = next[ei]){

                addOrUpdateOrIgnore(
                        to[ei],
                        dis[v] + weight[ei]
                );
            }
        }
           for(int i = 1;i<=n;i++){
               pw.print(dis[i] +" ");
           }
           pw.println();
           pw.flush();


    }
    //初始化
    public static void build(){
        idx = 1;
        heapSize = 0;
        Arrays.fill(head, 1, n + 1, 0);
        Arrays.fill(pos, 1, n + 1, -1);
        Arrays.fill(dis, 1, n + 1, Integer.MAX_VALUE);
    }
    public static void addOrUpdateOrIgnore(int v ,int w){
        if(pos[v] == -1){
            heap[heapSize] = v;
            pos[v] = heapSize++;
            dis[v] = w;
            up(pos[v]);
        }else if(pos[v] >= 0){
            dis[v]  = Math.min(w,dis[v]);
            up(pos[v]);
        }
    }



    // 抛出元素
    public static int pop(){
        int ans = heap[0];
        swap(0,--heapSize);
        siftDown(0);
        pos[ans] = -2;
        return ans;
    }
    // 上升
    public static void up(int i){
        while(dis[heap[i]] < dis[heap[(i-1)/2]]){
            swap(i,(i-1)/2);
            i= (i-1)/2;
        }
    }
    //下沉
    public static void siftDown(int i){
        int l = i * 2 + 1;
        while(l < heapSize){
            // 找到左右孩子中最小的那个
            int best = l + 1 < heapSize && dis[heap[l + 1]] < dis[heap[l]] ? l + 1 : l;
            //和自己进行比较
            best = dis[heap[best]] < dis[heap[i]] ? best : i;
            //如果不需要下沉，结束循环
            if (best == i) {
                break;
            }
            //需要下称，交换
            swap(best, i);
            i = best;
            l = i * 2 + 1;
        }
    }
    public static boolean isEmpty(){
        return heapSize == 0;
    }
    // 交换 i 和 j
    public static void swap(int i ,int j){
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        pos[heap[i]] = i;
        pos[heap[j]] = j;
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



}
