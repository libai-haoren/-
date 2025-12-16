package sort;

import java.util.HashMap;

public class quickSort {
    public static void main(String[] args) {
        int[] arr = {2, 3, 4, 6, 1, 0};
        solve(arr, 0, arr.length - 1);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);

        }
    }

    public static void solve(int[] arr, int left, int right) {
        if (left >= right) return;
        int pivot = partition(arr, left, right);
        solve(arr, left, pivot - 1);
        solve(arr, pivot + 1, right);
    }

    public static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && arr[j] >= pivot) j--;
            while (i < j && arr[i] <= pivot) i++;
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i];
        arr[i] = arr[left];
        arr[left] = temp;
        return i;
    }
}

