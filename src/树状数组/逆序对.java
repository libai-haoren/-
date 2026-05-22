package 树状数组;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *  如果数组长度过长 ， 使用离散化进行优化
 *  离散化：将数组中的元素进行离散化处理，使得数组中的元素值域缩小，从而降低树状数组的长度
 *  树状数组维护 :  某个值出现的次数
 */
public class 逆序对 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static int[] tree;
    public static int n;

    public static int lowbit(int i){
        return i & -i;
    }
    public static void add(int i,int v){
        while(i <= n){
            tree[i] += v ;
            i += lowbit(i);
        }
    }
    public static int query(int i){
        int ans = 0;
        while(i > 0){
            ans += tree[i];
            i -= lowbit(i);
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        n = Integer.parseInt(br.readLine());

        tree = new int[n + 1];
        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < n; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // ===== 离散化 =====

        int[] b = arr.clone();

        Arrays.sort(b);

        Map<Integer,Integer> map = new HashMap<>();

        int idx = 1;

        for(int x : b){
            if(!map.containsKey(x)){
                map.put(x, idx++);
            }
        }

        // ===== 树状数组统计逆序对 =====

        long ans = 0;

        for(int i = 0; i < n; i++){

            int rank = map.get(tree[i]);

            // 前面比它大的数量
            ans += i - query(rank);

            add(rank, 1);
        }



    }
}
