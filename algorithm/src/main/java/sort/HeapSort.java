package sort;


import java.util.Arrays;

/**
 * @author ZhXiQi
 * @Title: 堆排序
 * 堆化有两种方式：
 *  · 自下而上式堆化：将节点与其父节点比较，如果节点大于父节点（大顶堆）或节点小于父节点（小顶堆），则节点与父节点交换调整位置
 *  · 自上而下式堆化：将节点与其左右子节点比较，如果存在左右子节点大于该节点（大顶堆）或小于该节点（小顶堆），则将子节点的最大值（大顶堆）或最小值（小顶堆）与之交换
 * @date 2020/6/9 10:04
 */
public class HeapSort {

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

    /**
     * 自顶向下式
     */
    class HeapSortFromUp {
        /**
         * 建堆
         * @param arr 原始序列
         * @param len 初始有效序列长度
         * @param largest true-构建大顶堆/false-构建小顶堆
         */
        public void buildHeap(int[] arr, int len, boolean largest){
            //Math.floor(len/2) 从这里开始，减少多余计算
            for (int i = (int) Math.floor(len/2);i>=0;--i){
                heapify(arr,len,i,largest);
            }
        }

        /**
         * 自上而下式堆化
         * @param arr 堆数组
         * @param len 堆大小/数组大小
         * @param index 节点索引
         * @param largest true-构建大顶堆/false-构建小顶堆
         */
        public void heapify(int[] arr, int len, int index, boolean largest){
            //节点与左右子节点比较，不断向下
            while (true){
                //从index结点的左孩子结点开始，也就是2i+1处开始
                int left = 2*index+1;
                //右孩子
                int right = 2*index+2;
                //默认父节点
                int fatherIndex = index;
                if (largest){
                    //左孩子比父节点大(大顶堆)
                    if (left < len && arr[left] > arr[fatherIndex]) fatherIndex = left;
                    //右孩子比父节点大(大顶堆)
                    if (right < len && arr[right] > arr[fatherIndex]) fatherIndex = right;
                }else {
                    //左孩子比父节点小(小顶堆)
                    if (left < len && arr[fatherIndex] > arr[left]) fatherIndex = left;
                    //右孩子比父节点小(小顶堆)
                    if (right < len && arr[fatherIndex] > arr[right]) fatherIndex = right;
                }
                //终止条件
                if (fatherIndex == index) break;
                //交换元素
                swap(arr,index,fatherIndex);
                //继续调整堆
                index = fatherIndex;
            }
        }

        /**
         * 排序
         * @param arr
         * @param largest true-构建大顶堆/false-构建小顶堆
         * @return
         */
        public int[] heapSort(int[] arr,boolean largest){
            int length = arr.length;
            //1.建堆
            buildHeap(arr,length,largest);
            //2.进行排序
            for (int i=length-1;i>0;--i){
                swap(arr,0,i);
                length--;
                heapify(arr,length,0,largest);
            }
            return arr;
        }
    }

    /**
     * 自底向上式
     */
    class HeapSortFromDown {
        /**
         * 建堆
         * @param arr
         * @param heapSize 当前堆大小，初始为1
         */
        public void buildHeap(int[] arr, int heapSize){
            int len = arr.length-1;
            while (heapSize++ < len){
                heapify(arr,heapSize);
            }
        }

        /**
         * 自下而上式堆化
         * 节点与父节点比较，不断向上
         * @param arr
         * @param index 当前堆的最后一个节点元素索引
         */
        public void heapify(int[] arr, int index){
            //获取当前 index 节点的父节点
            int fatherIndex = 0;
            //小顶堆
            while ((fatherIndex = (int) Math.floor((index-1)/2)) >= 0 && arr[index] < arr[fatherIndex]){
                swap(arr,index, fatherIndex);
                index = fatherIndex;
            }
        }

        /**
         * 排序
         * @param arr
         * @return
         */
        public int[] heapSort(int[] arr){
            int length = arr.length-1;
            //建堆
            buildHeap(arr,0);
            for (int i=length;i>=0;--i){
                swap(arr,0,i);
                length--;
                heapify(arr,length);
            }
            return arr;
        }
    }


    public static void main(String[] args) {
        int[] arr = {8,9,7,6,1,4,3,2,1};
        HeapSort heapSort = new HeapSort();
        //自底向上
        HeapSortFromDown heapSortFromDown = heapSort.new HeapSortFromDown();
        heapSortFromDown.heapSort(arr);
        //自顶向下
//        HeapSortFromUp heapSortFromUp = heapSort.new HeapSortFromUp();
//        heapSortFromUp.heapSort(arr,false);
        System.out.println(Arrays.toString(arr));
    }
}
