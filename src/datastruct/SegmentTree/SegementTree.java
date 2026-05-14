package datastruct.SegmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 线段树
public class SegementTree {
    static int MAXN = 100001;
    // 线段树数组  最多4倍的节点数
    static long[] sum = new long[MAXN<<2];
    // 懒惰标记数组
    static long[] add = new long[MAXN<<2];

    //原始数组
    static int[] arr = new int[MAXN];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int n;

    public static void main(String[] args) {
        n = nextInt();
        int m = nextInt();

        for(int i =1 ;i<=n;i++){
            arr[i] = nextInt();
        }
        build(1,n,1);
        while(m-->0){
            int op = nextInt();
            System.out.println(op);
            if(op == 1) {
                int x = nextInt();
                int y = nextInt();
                int k = nextInt();
                add(x,y,k,1,n,1);
            }else{
                int x = nextInt();
                int y = nextInt();
                long ans = query(x,y,1,n,1);
                System.out.println(ans);
            }
            System.out.println(m);
        }

    }
    // 建树过程  r 表示右边界  l 表示左边界  i 表示当前节点
    public  static void build(int l, int r , int i){
        if(l == r){
            sum[i] = arr[l];
        }else{
            int mid = (l+r)>>1;
            build(l,mid,i<<1);
            build(mid+1,r,i<<1|1);
            // 合并子树信息
            up(i);
        }
        add[i] = 0;
    }
    // 合并子树信息
    public static void up(int i){
         sum[i] = sum[i<<1] + sum[i<<1|1];
    }
    // 懒更新  i 表示当前节点  v 表示更新值  len 表示当前节点的长度
    public static void lazy(int i,long v , int len){
        sum[i] += v * len;
        add[i] += v;
    }
    // 下发懒更新  i 表示当前节点  ln 表示左子树长度  rn 表示右子树长度
    public static void down(int i,int ln,int rn){
        if(add[i] != 0){
            lazy(i<<1,add[i],ln);
            lazy(i<<1|1,add[i],rn);
           add[i] = 0;
        }
    }

    // 范围添加 jobl 表示左边界  jobr 表示右边界  v 表示更新值  l 表示当前节点的左边界  r 表示当前节点的右边界  i 表示当前节点
    public static void add(int jobl ,int jobr , int v , int l , int r ,int i){
        //如果全部包含，触发懒更新
        if(jobl <= l && jobr >= r){
            lazy(i,v,r-l+1);
            return;
        }
        int mid = (l+r)>>1;
        down(i,mid-l+1,r-mid);
        // 递归更新左子树
        if(jobl <= mid) add(jobl,jobr,v,l,mid,i<<1);
        // 递归更新右子树
        if(jobr > mid) add(jobl,jobr,v,mid+1,r,i<<1|1);
        // 合并子树信息
        up(i);
    }
    // 范围查询
    public static long query(int jobl ,int jobr , int l , int r ,int i){
        //如果全部包含，返回当前节点的和
        if(jobl <= l && jobr >= r){
            return sum[i];
        }
        long ans = 0 ;
        int mid = (l+r)>>1;
        // 否则，下发懒更新
        down(i,mid - l +1 , r - mid );
        // 递归查询左子树
        if(jobl <= mid) ans+=query(jobl,jobr,l,mid,i<<1);
        // 递归查询右子树
        if(jobr > mid) ans+=query(jobl,jobr,mid+1,r,i<<1|1);
        return ans;
    }
    public static int nextInt(){
        return Integer.parseInt(next());
    }
    public static String next(){
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
