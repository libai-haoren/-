package heap;

import java.util.PriorityQueue;

public class HeapClass {
    public static void main(String[] args) {
        int []stones=new int[]{2,2};
        int res=lastStoneWeight(stones);
        System.out.println(res);
    }
    public static  int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b)-> b-a);
        int len = stones.length;
        for(int i=0;i<len;i++){
            pq.add(stones[i]);
        }
        while(pq.size()!=1){
            int max=pq.poll();
            int min=pq.poll();
            if(max==min) continue;
            int newStone = max- min;
            pq.add(newStone);

        }
        return pq.isEmpty()? 0:pq.peek();

    }

}
