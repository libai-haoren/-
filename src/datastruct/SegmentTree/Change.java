package datastruct.SegmentTree;
//功能 ： 区间重置  区间查询最大值
public class Change {
    int MAXN = 1000001;
    int[] change = new int[MAXN>>2];
    boolean[] update = new boolean[MAXN>>2];
    int[] max = new int[MAXN>>2];
    int[] arr = new int[MAXN];
    public static void main(String[] args) {

    }
    public void up(int i){
        max[i] = Math.max(max[i>>1],max[i>>1|1]);
    }
    public void down(int i){
        if(update[i]){
            lazy(i>>1,change[i]);
            lazy(i>>1|1,change[i]);
            update[i] = false;
        }
    }
    public void lazy(int i,int v){
        max[i] = v ;
        update[i] = true;
        change[i] = v;
    }
    public void build(int l ,int r ,int i){
        if(l == r){
            max[i] = arr[l];
        }else{
            int mid = (l+r)>>1;
            build(l,mid,i>>1);
            build(mid+1,r,i>>1|1);
            up(i);
        }

    }
    public void change(int jobl ,int jobr ,int v,int l ,int r , int i){
        if(jobl >= l && jobr <= r){
            lazy(i,v);
        }else{
            down(i);
            int mid = (l+r)>>1;
            if(jobl <= mid) change(jobl, jobr, v, l, mid, i>>1);
            if(jobr > mid) change(jobl, jobr, v, mid+1, r, i>>1|1);
            up(i);
        }
    }
}
