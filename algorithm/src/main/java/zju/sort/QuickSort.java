package zju.sort;

import java.util.Arrays;

/**
 * @author ZhXiQi
 * @Title: 快排
 * @date 2020/6/6 12:52
 */
public class QuickSort {
    public void quickSort(int[] arr,int left, int right){
        //默认取第一个元素为枢纽元素

        //两边还没相遇，说明还没排序完成
        if (left < right){
            int i = left,j = right,pivot = arr[left];
            while (i<j){
                //右边的元素比枢纽大
                while (i<j && arr[j]>=pivot) --j;
                if (i<j) arr[i++] = arr[j];
                //左边的元素比枢纽小
                while (i<j && arr[i]<=pivot) ++i;
                if (i<j) arr[j--] = arr[i];
                //此时 i 位置已经确定位置,即 pivot 的值已经确定所处位置
            }
            //把pivot 枢纽的值放到它排序后的位置中
            arr[i] = pivot;
            //以 pivot 的值为界，即 i 位置为界，分两端分别进行排序
            quickSort(arr,left,i-1);
            quickSort(arr,i+1,right);
        }
    }
    public static void main(String[] args) {
        int[] arr = new int[]{7,3,7,1,8,3,5,7,0,2};
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
