package zju.sort;

import java.util.Arrays;

/**
 * @author ZhXiQi
 * @Title: 堆排序
 * @date 2020/6/6 16:27
 */
public class HeapSort {

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        int[] arr = {9,5,7,1,564,3,2,10};
        int[] ints = heapSort.heapSort(arr,true);
        System.out.println(Arrays.toString(ints));
    }

    private int[] heapSort(int[] arr,boolean max){
        //构建堆
        buildHeap(arr,max);
        int len = arr.length;
        for (int i=len-1;i>0;--i){
            //堆顶元素和最后的交换
            swap(arr,0,i);
            len--;
            //从堆顶开始继续调整
            heapify(arr,0,len,max);
        }
        return arr;
    }

    /**
     * 建堆
     * @param arr 堆元素
     * @param max true-大顶堆/false-小顶堆
     */
    public void buildHeap(int[] arr, boolean max){
        int len = arr.length;
        //向下取整 Math.floor(len/2) 等同于 (len-1)/2
        for (int i = (int) Math.floor(len/2); i>=0; --i){
            heapify(arr,i,len,max);
        }
    }
    /**
     * 堆调整
     * @param arr 待调整堆元素
     * @param i 当前节点
     * @param len 数组长度
     * @param max true-大顶堆/false-小顶堆
     */
    public void heapify(int[] arr, int i, int len, boolean max){

        //i节点的左孩子
        int left = 2*i+1;
        //i节点的右孩子
        int right = 2*i+2;
        //默认父节点
        int father = i;

        if (max){
            //左孩子比父节点大，交换
            if (left < len && arr[left] > arr[father]) father = left;
            //右孩子比父节点大，交换
            if (right < len && arr[right] > arr[father]) father = right;
        }else {
            //左孩子比父节点小，交换
            if (left < len && arr[left] < arr[father]) father = left;
            //右孩子比父节点小，交换
            if (right < len && arr[right] < arr[father]) father = right;
        }
        //最大值or最小值不是父节点
        if (father != i){
            //交换元素
            swap(arr,father,i);
            //继续调整
            heapify(arr,father,len,max);
        }
    }

    /**
     * 交换元素
     * @param arr
     * @param i
     * @param j
     */
    public void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
