package graph;

public class union {
    int [] parent;
    int [] rank; //记录树高

    public static void main(String[] args) {

    }
    /**
     * 递归查找
     */
    public int find01(int [] parent,int x){
        if(x!=parent[x]){
            parent[x] = find01(parent,parent[x]);
        }
        return parent[x];
    }
    /**
     * 迭代查找
     */
    public int find02(int [] parent,int x){
        while(x!=parent[x]){
            parent[x] =parent[parent[x]];
            x= parent[x];
        }
        return x;
    }
    /**
     * 随意合并
     */
    public union(int [] parent , int x ,int y){
        parent[find01(parent,x)] = find02(parent,x);
    }
    /**
     * 合并时高度小的合并到高度大的:
     * 小树合并到大树
     * 高度相等时，任意合并都可以
     * 合并后，大树的高度增加1
     */
    public void union01(int [] parent , int x ,int y){
        int xRoot = find01(parent,x);
        int yRoot = find01(parent,y);
        if(xRoot==yRoot){
            return;
        }
        if(parent[xRoot]<parent[yRoot]){
            parent[xRoot] = yRoot;
        }else{
            parent[yRoot] = xRoot;
        }
    }


}
