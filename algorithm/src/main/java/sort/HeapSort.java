package sort;


import java.util.Arrays;

/**
 * @author ZhXiQi
 * @Title: 堆排序 是原地算法
 * 堆化有两种方式：
 *  · 自下而上式堆化（O(n)）：将节点与其父节点比较，如果节点大于父节点（大顶堆）或节点小于父节点（小顶堆），则节点与父节点交换调整位置
 *  · 自上而下式堆化（O(nlogn)）：将节点与其左右子节点比较，如果存在左右子节点大于该节点（大顶堆）或小于该节点（小顶堆），则将子节点的最大值（大顶堆）或最小值（小顶堆）与之交换
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
                int left = 2*index;//等同于 (index<<1)
                //右孩子
                int right = 2*index+1;//等同于 (index<<1)+1
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
                //找出元素，交换元素
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
            //1.建堆，堆顶会是需要的已排好位置的值
            buildHeap(arr,length,largest);
            //2.进行排序
            for (int i=length-1;i>0;--i){
                //堆顶是已经确定好的顺序值，把确定的值换到堆尾
                swap(arr,0,i);
                //堆大小减少一个，表示堆尾已经确定好顺序
                length--;
                //调整剩下的堆（从堆顶到新的堆尾）
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
            int tmp = arr[index];
            while ((fatherIndex = (int) Math.floor((index-1)/2)) >= 0 && arr[index] < arr[fatherIndex]){
                swap(arr,index, fatherIndex);
                index = fatherIndex;
            }
            arr[index] = tmp;
        }

        public void fixUp(int[] arr, int index) {
            int parent = 0;
            int tmp = arr[index];
            while ((parent = (index-1)/2) > 0) {
                if (arr[parent] < arr[index]) break;
                swap(arr,parent,index);
                index = parent;
            }
            arr[index] = tmp;
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
//        HeapSortFromDown heapSortFromDown = heapSort.new HeapSortFromDown();
//        heapSortFromDown.heapSort(arr);
        //自顶向下
        HeapSortFromUp heapSortFromUp = heapSort.new HeapSortFromUp();
        heapSortFromUp.heapSort(arr,false);
        System.out.println(Arrays.toString(arr));
    }


    /**
     * 自己实现一个优先队列
     * @param <Key>
     */
    class MaxPQ <Key extends Comparable<Key>> {
        //存储元素的数组
        private Key[] pq;
        //当前 Priority Queue 中的元素个数
        private int N = 0;

        public MaxPQ(int cap) {
            //索引 0 不用，所以多分配一个空间
            pq = (Key[]) new Comparable[cap+1];
        }

        /**
         * 返回当前队列中的最大元素
         * @return
         */
        public Key max() {
            return pq[1];
        }

        /**
         * 插入元素 e
         * @param e
         */
        public void insert(Key e) {
            N++;
            //先把元素加到最后
            pq[N] = e;
            //然后让他上浮到正确位置
            swim(N);
        }

        /**
         * 删除并返回当前队列中最大元素
         * @return
         */
        public Key delMax() {
            //最大堆的堆顶就是最大元素
            Key max = pq[1];
            //把这个最大元素换到最后，删除之
            exch(1,N);
            pq[N] = null;
            N--;
            //让 pq[1] 下沉到正确位置
            sink(1);
            return max;
        }

        private void swim(int k) {
            //如果浮到堆顶，就不再上浮
            while (k > 1 && less(parent(k),k)) {
                //如果第 k 个元素比上层大，将 k 换上去
                exch(parent(k),k);
                k = parent(k);
            }
        }

        private void sink(int k) {
            //如果沉到堆低，就沉不下去了
            while (left(k) <= N) {
                //先假设左边节点较大
                int older = left(k);
                //如果右边节点存在，比较一下大小
                if (right(k) <= N && less(older, right(k)))
                    older = right(k);
                //节点 k 比两孩子都大，就不必下沉了
                if (less(older,k)) break;
                //否则，不符合最大堆的结构，下沉 k 节点
                exch(k,older);
                k = older;
            }
        }

        private void exch(int i, int j) {
            Key tmp = pq[i];
            pq[i] = pq[j];
            pq[j] = tmp;
        }

        private boolean less(int i, int j) {
            return pq[i].compareTo(pq[j]) < 0;
        }
        private int parent(int root) {
            return root >> 1;
        }
        private int left(int root) {
            return root << 1;
        }
        private int right(int root) {
            return (root << 1) + 1;
        }
    }
}
