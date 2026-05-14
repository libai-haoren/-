package sort;


/**
 *  归并排序
 *
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 6, 1, 0};
         mergeSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

    public  static void mergeSort(int[] arr){
        mergeSort(arr,0,arr.length-1);
    }
    static void mergeSort(int[]arr,int l ,int r){
        if(l>=r) return;
        int mid = l + ((r-l)>>1);
        mergeSort(arr,l,mid);
        mergeSort(arr,mid+1,r);
        merge(arr,l,mid,r);
    }
   static void merge(int[]arr,int l ,int mid ,int r){
        int[] temp = new int[r-l+1];
        int i = l, j =mid+1, k = 0;
        while(i<=mid && j <= r){
            if(arr[i] <arr[j]){
                temp[k++] = arr[i++];
            }else{
                temp[k++] = arr[j++];
            }
        }
        while(i<=mid){
            temp[k++] = arr[i++];
        }
        while(j<=r){
            temp[k++] = arr[j++];
        }
        for(k=0,i=l;i<=r;i++,k++){
            arr[i] = temp[k];
        }


    }


}
