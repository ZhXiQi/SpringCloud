package swordOfOffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * @author ZhXiQi
 * @Title: 剑指offer
 * @date 2021/1/10 11:17
 */
public class Solution {

    public int palindrome(String str,int k) {
        int len = str.length();
        List<String> list = new ArrayList<>();
        for(int i=0;i<len;++i) {
            for(int j=i;j<len;++j) {
                if(isPalindrome(str,i,j)) list.add(str.substring(i,j+1));
            }
        }
        list.sort((a,b)->b.length()-a.length());
        int size = list.size();
        int idx = 0;
        int result = 1;
        while (k>0 && idx < size) {
            String s = list.get(idx++);
            if (s.length()%2==1) {
                result = (result * s.length())%1000000007;
                --k;
            }
        }

        return result;
    }

    /**
     * 判断是否回文串
     * @param s
     * @param left
     * @param right
     * @return
     */
    public boolean isPalindrome(String s, int left, int right) {
        while (left<right) {
            if(s.charAt(left) != s.charAt(right)) return false;
            ++left;
            --right;
        }
        return true;
    }

    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int i=left,j=right,pivot=arr[i];
            while (i<j) {
                while (i<j && pivot < arr[j]) --j;
                if (i<j) arr[i++] = arr[j];
                while (i<j && pivot > arr[i]) ++i;
                if (i<j) arr[j--] = arr[i];
            }
            arr[i] = pivot;
            quickSort(arr,left, i-1);
            quickSort(arr, j+1,right);
        }
    }

    public void swap(int[] arr, int i, int j) {
        int tmp =  arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public ListNode reverse(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur!=null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return head;
    }

    public void fixDown(int[] arr, int len, int idx) {
        while (true) {
            int left = (idx<<1),right=left+1,parennt=idx;
            if (left<len && arr[parennt] < arr[left]) parennt=left;
            if (right<len && arr[parennt] < arr[right]) parennt = right;
            if (parennt==idx) break;
            swap(arr,parennt,idx);
            idx=parennt;
        }
    }

    public void buildHeap(int[] arr, int len) {
        int heapSize = (int) Math.floor(len/2);
        for (int i=heapSize;i>=0;--i) {
            fixDown(arr,len,i);
        }
    }

    public void heapSort(int[] arr) {
        int len = arr.length;
        buildHeap(arr,len);
        for (int i=len-1;i>=0;--i) {
            swap(arr,i,0);
            --len;
            fixDown(arr,len,0);
        }
    }


    /**
     * 优势洗牌
     * 田忌赛马
     * 对每个B[i]从A中找比B[i]大的最小的元素A[k]. 如果找到, 就用A[k]替换A[i]. 如果没找到, 就用A中的最小的元素替换A[i].
     * 例如: A = [12,24,8,32], B = [13,25,32,11]
     * B[0] = 13, A中比13大的最小元素是24. A[0]=24.
     * B[1]= 25, A中比25大的最小元素是32. A[1]=32.
     * B[2]= 32, A中比32大的最小元素, 没有. A[2]=8.
     * B[3]= 11, A中比11大的最小元素是13. A[3]=12.
     *
     * @param a
     * @param b
     * @return
     */
    public int[] advantageCount(int[] a, int[] b) {
        int n = b.length;
        //对A排序
        Arrays.sort(a);
        int[][] pair = new int[n][2];
        for (int i = 0; i < n; i++)
            pair[i] = new int[]{b[i], i};//把下标保存下来
        //根据B的值排序
        Arrays.sort(pair, (x, y)->x[0] - y[0]);

        int[] res = new int[n];
        for (int i = 0, r = n - 1, l = 0; i < n; i++)//r标明最大值，l标明最小值；
        {
            //a和pair都按照从小到大排好序了，如果当前a的值比pair最小值小，则a中没有比这个值大的数字了，所以a中的当前值就是对应的最小元素，放入到对应B数组的位置上
            if (a[i] <= pair[l][0]) res[pair[r--][1]] = a[i];//要放到原数组对应的位置上
            else res[pair[l++][1]] = a[i];////要放到原数组对应的位置上
        }
        return res;
    }

    public int ad(int[] a,int[] b) {
        int n = b.length;
        Arrays.sort(a);
        int[][] pair = new int[n][2];
        for(int i=0;i<n;++i)
            pair[i] = new int[]{b[i],i};
        Arrays.sort(pair,(x,y)->x[0]-y[0]);
        int result = 0;
        for (int i=0,r=n-1,l=0;i<n;++i) {
            if (a[i] <= pair[l][0]) r--;
            else {
                ++result;
                ++l;
            }
        }
        return result;
    }

    public int advantageCount(int n) {
        int[] A = new int[n];
        //模拟的输入
        int[] B = new int[]{1,3,1,4};
        for(int i=0;i<n;++i) A[i] = i+1;
        //调用得到排好的
        int result = ad(B, A);
        return result;
    }

    /**
     * 寻找数组的中心下标
     * 前缀和数组
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        int len = nums.length;
        int[] presum = new int[len+1];

        for(int i=0;i<len;++i) {
            presum[i+1] = presum[i] + nums[i];
        }

        for(int i=0;i<len;++i) {
            if(presum[i]==presum[len]-nums[i]-presum[i]) return i;
        }
        return -1;
    }

    /**
     * 滑动窗口的最大值
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSliderWindows(int[] nums, int k) {
        if(nums==null || nums.length==0) return new int[0];
        int len = nums.length;
        int left=0,right=0;
        List<Integer> result = new ArrayList<>();
        while (left <= (len-k)) {
            right = left;
            int max = Integer.MIN_VALUE;
            int tmpRight = left + k;
            for (;right < tmpRight;++right) {
                if (nums[right] > max) max = nums[right];
            }
            result.add(max);
            ++left;
        }
        int[] res = new int[result.size()];
        for(int i=0;i<result.size();++i) {
            res[i] = result.get(i);
        }
        return res;
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String c = in.next();
        System.out.println(c);
        int[] ints = {1,7,3,6,5,6};
        Solution solution = new Solution();
        int ababa = solution.palindrome("ababa", 3);
        System.out.println(ababa);
        solution.pivotIndex(ints);
        int ma = solution.advantageCount(4);
        System.out.println(ma);
        Map<String,Integer> map = new ConcurrentHashMap<>();
//        solution.quickSort(ints,0,ints.length-1);
//        solution.heapSort(ints);
//        System.out.println(Arrays.toString(ints));
    }
}

class ListNode {
    public int val;
    public ListNode next;


}