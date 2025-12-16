package sort;

public class insertSort {
    public static void main(String[] args) {
        int  [] arr = {2,3,4,6,1,0};
        solve(arr);
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
    public static void solve(int[] arr){
        for(int i=1; i<arr.length;i++){
            //选择基准数
            int base = arr[i];
            //从基准数的前一个数开始比较
            int j =i-1;
            //如果基准数比前一个数小，就将前一个数后移
            while(j>=0 && arr[j]>base){
                arr[j+1] = arr[j];
                j--;
            }
            //将基准数插入到正确位置
            arr[j+1] = base;
        }
    }
}
