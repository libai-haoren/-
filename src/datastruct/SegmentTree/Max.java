package datastruct.SegmentTree;
// 功能 ： 查找范围内的最大值  区间修改

public class Max {
    int MAXN = 100005;
    int[] arr = new int[MAXN];
    int[] add = new int[MAXN <<2];
    int[] max = new int[MAXN <<2];

    public static void main(String[] args) {

    }
    public void up(int i){
        max[i] = Math.max(max[i<<1],max[i<<1|1]);
    }
    public void down(int i){
        if(add[i] != 0){
            lazy(i<<1,add[i<<1]);
            lazy(i<<1|1,add[i<<1|1]);
            add[i] = 0;
        }
    }
    public void lazy(int i,int v ){
        max[i] += v;
        add[i] += v;
    }
    public void build(int l ,int r ,int i){
        if(l == r){
           max[i] = arr[l];
        }else{
            int mid = (l+r)>>1;
            build(l,mid,i<<1);
            build(mid+1,r,i<<1|1);
            up(i);
        }
    }
    public int query(int jobl ,int jobr, int l ,int r ,int i){
        if(jobl <= l && jobr >= r){
            return max[i];
        }
        down(i);
        int mid = (l+r)>>1;
        int ans = Integer.MIN_VALUE;
        if(jobl<=mid){
            ans = Math.max(ans,query(jobl,jobr,l,mid,i<<1));
        }
        if(jobr > mid){
            ans = Math.max(ans,query(jobl,jobr,mid+1,r,i<<1|1));
        }
        return ans;
    }
    public void add(int jobl ,int jobr ,int l ,int r ,int i,int v){
        if(jobl <= l && jobr >= r){
            lazy(i,v);
            return;
        }
        down(i);
        int mid = (l+r)>>1;
        if(jobl<=mid){
            add(jobl,jobr,l,mid,i<<1,v);
        }
        if(jobr > mid){
            add(jobl,jobr,mid+1,r,i<<1|1,v);
        }
        up(i);
    }
}
