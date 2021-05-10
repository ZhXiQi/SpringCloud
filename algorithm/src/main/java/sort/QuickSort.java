package sort;

/**
 * @author ZhXiQi
 * @Title: 快速排序
 * @date 2020/6/9 09:54
 */
public class QuickSort {

    public void quickSort(int[] arr,int left,int right){

        if (left < right){
            int i=left,j=right,pivot=arr[left];
            while (i<j){
                while (i<j && arr[j]>=pivot) --j;
                if (i<j) arr[i++] = arr[j];
                while (i<j && arr[i]<=pivot) ++i;
                if (i<j) arr[j--] = arr[i];
            }
            //跳出循环时，说明 i==j
            arr[j] = pivot;
            quickSort(arr,left,j-1);
            quickSort(arr,i+1,right);
        }

    }

    public static void main(String[] args) {
        int[] arr = new int[]{7,3,7,1,8,3,5,7,0,2};
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(arr,0,arr.length-1);
        System.out.println(arr);
    }
}
