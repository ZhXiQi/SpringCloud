package zju.ZhXiQi;


import java.util.Arrays;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/3/17 09:26
 */
public class HeapSort {

    //因为声明的多个函数都需要数据长度，所以把len设置为全局变量
    int len;

    /**
     * 建立大顶堆
     * @param arr
     */
    public void buildMaxHeap(int[] arr){
        len = arr.length;
        //向下取整
        for (int i = (int) Math.floor(len/2); i>= 0; --i){
            heapify(arr, i);
        }
    }

    /**
     * 堆调整
     * @param arr
     * @param i
     */
    public void heapify(int[] arr, int i){
        //从i结点的左子结点开始，也就是2i+1处开始
        int left = 2 * i + 1,
                //右孩子
                right = 2 * i + 2,
                //默认父节点
                largest = i;
        //左孩子比父节点大
        if (left < len && arr[left] > arr[largest]){
            largest = left;
        }
        //右孩子最大
        if (right < len && arr[right] > arr[largest]){
            largest = right;
        }
        //最大值不是父节点
        if (largest != i){
            //交换
            swap(arr, i, largest);
            //继续维护
            heapify(arr, largest);
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

    public int[] heapSort(int[] arr){
        //1.构建大顶堆
        buildMaxHeap(arr);
        for (int i=arr.length - 1; i>0;--i){
            swap(arr,0,i);
            len--;
            heapify(arr,0);
        }
        return arr;
    }

    //------------------------------------------------------------------------

    /**
     * 建立最小堆（一样的）
     * (len-1)/2 向下取整
     * @param arr
     * @param len
     */
    public void buildMinHeap(int[] arr, int len){
        for (int i = (len-1)/2; i>=0; --i){
            minHeapify(arr, len, i);
        }
    }

    /**
     * 维护最小堆
     * @param arr
     * @param len
     * @param i
     */
    public void minHeapify(int[] arr, int len, int i){
        //结点i的左孩子
        int left = i * 2 + 1;
        //结点i的右孩子
        int right = left + 1;
        //默认父节点
        int largest = i;
        //左孩子比父节点小
        if (left < len && arr[largest] > arr[left]){
            largest = left;
        }
        //右孩子最小
        if (right < len && arr[largest] > arr[right]){
            largest = right;
        }
        //最小值不是父节点
        if (i != largest){
            //交换
            swap(arr,i,largest);
            //继续维护
            minHeapify(arr,len,largest);
        }
    }

    public void minHeapSort(int[] arr, int len){
        //建堆
        buildMinHeap(arr, len);
        //最后一个肯定是最小的
        for (int i=len;i>=1;){
            //交换堆的第一个元素和堆的最后一个元素
            swap(arr,i,0);
            //堆大小减一
            i--;
            //调整堆
            minHeapify(arr,i,0);
        }
    }

    public static void main(String[] args) {
        HeapSort heapSort = new HeapSort();
        int[] arr = {8,9,7,6,1,4,3,2,1};
        heapSort.minHeapSort(arr,arr.length);
        System.out.println(Arrays.toString(arr));
    }
}
