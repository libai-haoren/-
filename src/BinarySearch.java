import java.util.List;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {0,1,2,2,4};

        int target = 2;
        int index= binarySearch(arr,target);
        int index2= upperBound(arr,target);
        System.out.println(index);
        System.out.println(index2);
    }
/**
 *  二分查找 ,闭区间,
 *  存在重复元素找target插入索引
 *  找到返回第一个target索引
 */
    private static  int binarySearch(int[] arr, int target) {
        int left = 1;
        int right = arr.length-1;
        while (left <= right) {
            int mid =(left+right) >>> 1;
            if(arr[mid] >= target){
                right = mid-1;
            }else left =mid +1;
        }
        return left;
    }
    /**
     * 不存在重复元素，给一个目标target，桉顺序返回插入索引，
     */
     private static  int insertIndex(int [] arr ,int target){
         int left =0;
         int right = arr.length - 1;
         while (left <= right){
             int mid =left +( right -left)/2;
             if(arr[mid]<target){
                 left =mid +1;
             } else if (arr[mid]>target) {
                 right =mid -1;
             }else {
                 return mid;
             }
         }

         return left;
     }

    private static int lowerBound(int []pos, int target) {
        int low = 0, high = pos.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (pos[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    private static int upperBound(int []pos, int target) {
        int low = 0, high = pos.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (pos[mid] <= target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    /**
     * 开区间写法
     */
     private static int kai(int[] pos, int target) {
         int low =-1 , high = pos.length;
          while( low +1 < high){
              int mid =(low+high) >>>1 ;
              if(pos[mid] <= target){
                  low =mid;
              }else {
                  high =mid;
              }
          }
          return high;
     }

    class Address {
        String city;
        // 构造方法、getter、setter省略
    }

    class Person implements Cloneable {
        String name; // 基本类型（包装类）
        Address address; // 引用类型

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone(); // 浅拷贝
        }
    }


}
