package math;

import java.util.*;

/**
 * 统计素数 埃式筛法
 */
public class countPrimes {

    /**
     *  对于一个素数来说，他的每一个倍数都不是素数
     *  1. 创建一个长度为n的数组，初始化都为true
     *  2. 遍历数组，将数组中为true的数，都标记为false
     *  3. 遍历数组，统计素数的个数
     *  4. 返回素数的个数
     */
    public static int solve(int n) {
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);
        for(int i= 2;i*i<n;i++){
            if(isPrime[i]){
                for(int j=i*i;j<n;j+=i){
                    isPrime[j]=false;
                }
            }
        }

        HashMap<Integer,Integer> map = new HashMap<>();
        map.computeIfAbsent(1, k->1);
        TreeSet<Integer> set = new TreeSet<>();
        int count = 0;
        for(int i=2;i<n;i++){
            if(isPrime[i]){
                count++;
            }
        }
        return count;
    }
}
