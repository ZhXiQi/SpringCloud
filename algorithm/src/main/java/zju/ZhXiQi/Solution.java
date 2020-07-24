package zju.ZhXiQi;


import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/3/7 10:24
 */
public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int[] nums = new int[len];
        System.arraycopy(nums1, 0, nums, 0, nums1.length);
        System.arraycopy(nums2, 0, nums, nums1.length, nums2.length);
        int[] ints = IntStream.of(nums).sorted().toArray();
        double res = 0;
        int mid = len/2;
        if (len%2==0){
            res += ints[mid];
            res += ints[mid-1];
            res = res/2;
        }else {
            res = ints[mid];
        }
        return res;
    }

    public static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
 }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        String num1 = "";
        String num2 = "";
        while(l1 != null){
            num1 = l1.val+num1;
            l1 = l1.next;
        }
        while(l2 != null){
            num2 = l2.val+num2;
            l2 = l2.next;
        }
//        num1 = "1000000000000000000000000000001";
        BigDecimal num1Big = new BigDecimal(num1);
        BigDecimal num2Big = new BigDecimal(num2);
        BigDecimal bigDecimal = num1Big.add(num2Big);
        ListNode node = new ListNode(0);
        ListNode head = node;
        if (bigDecimal.compareTo(BigDecimal.ZERO)==0){
            return head;
        }

        while (bigDecimal.divide(BigDecimal.TEN).compareTo(BigDecimal.ZERO) > 0){
            BigDecimal[] bigDecimals = bigDecimal.divideAndRemainder(BigDecimal.TEN);
            bigDecimal = bigDecimals[0];
            BigDecimal bigDecimal1 = bigDecimals[1];
            ListNode tmpNode = new ListNode(bigDecimal1.intValue());
            node.next = tmpNode;
            node = node.next;

        }

        return head.next;
    }
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resList = new ListNode(0);
        ListNode head = resList;
        int yu = 0;
        while (l1 != null || l2 != null){
            int sum = 0;
            if (l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            sum = sum + yu;
            ListNode tmpNode = new ListNode(sum % 10);
            resList.next = tmpNode;
            resList = tmpNode;
            yu = sum/10;
        }
        if (yu != 0){
            resList.next = new ListNode(yu);
        }
        return head.next;

    }

    public boolean isValid(String s) {
        if (s.equals("")){
            return true;
        }
        char[] arr = s.toCharArray();

        Stack<Character> stack = new Stack<>();
        int len = arr.length;
        Map<Character,Character> map = new HashMap<>(8);
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');
        Set<Character> set = map.keySet();
//        Collection<Character> cc =  map.values();
        Set<Character> cc = new HashSet<Character>(){
            {
                add(')');
                add('}');
                add(']');
            }
        };
        if (cc.contains(arr[0])){
            return false;
        }
        for(int i=0;i<len;++i){
            if(set.contains(arr[i])){
                stack.add(arr[i]);
            }
            if(cc.contains(arr[i])){
                if (stack.isEmpty()){
                    return false;
                }
                Character c = stack.pop();
                if(map.get(c) != arr[i]){
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }

    public int removeDuplicates(int[] nums) {
        int i=0;
        int j=1;
        int length = nums.length;
        while (j<length){
            if (nums[j] == nums[i]){
                ++j;
            }else {
                ++i;
                nums[i] = nums[j];
                ++j;
            }
        }
        return i+1;
    }

    public int removeElement(int[] nums, int val) {
        int len = nums.length;
        int j = 0;
        for (int i=0;i<len;++i){
            if (nums[i]!=val){
                nums[j++]=nums[i];
            }
        }
        return j;
    }

    public int strStr(String haystack, String needle) {
        int i = haystack.indexOf(needle);
        return i;
    }

    public int searchInsert(int[] nums, int target) {
        int len = nums.length;
        for (int i=0;i<len;++i){
            if (nums[i]==target){
                return i;
            }
            if (nums[i] > target){
                return i;
            }
        }
        return len;
    }

    /**
     * 最大连续子数组
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums.length==0){
            return 0;
        }
        int max = nums[0];
        int res = nums[0];
        int len = nums.length;
        for (int i=1;i<len;++i){
            max = Math.max(max + nums[i], nums[i]);
            if (res < max){
                res = max;
            }
        }
        return res;
    }

    public int[] plusOne(int[] digits) {
        int len = digits.length;
        int yu = 1;
        for (int i=len-1;i>=0;--i){
            int tmp = digits[i]+yu;
            yu = tmp/10;
            digits[i] = tmp%10;
        }
        if (yu>0){

        }
        return digits;
    }

    public String addBinary(String a, String b) {
        int l1 = a.length()-1;
        int l2 = b.length()-1;
        int yu=0;
        StringBuilder sb = new StringBuilder();
        while (l1>=0 || l2>=0){
            int n1 = l1>=0?a.charAt(l1--) - '0':0;
            int n2 = l2>=0?b.charAt(l2--) - '0':0;
            int tmp = n1 + n2 + yu;
            sb.append(tmp%2);
            yu = tmp/2;
        }
        if (yu > 0){
            sb.append(yu);
        }
        return sb.reverse().toString();

    }

    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public TreeNode invertTree(TreeNode root) {
        TreeNode tmp;
        tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        if (root.left != null) invertTree(root.left);
        if (root.right != null) invertTree(root.right);
        return root;
    }


    public int maximumProduct(int[] nums) {
        int len = nums.length;
//        Arrays.sort(nums);
        Arrays.parallelSort(nums);
//        List<int[]> collect = Stream.of(nums).parallel().sorted().collect(Collectors.toList());
//        System.out.println(collect);
//        int[] ints = collect.get(0);
        int min1 = nums[0];
        int min2 = nums[1];

        int max1 = nums[len-1];
        int max2 = nums[len-2];
        int max3 = nums[len-3];

        return Math.max(min1*min2*max1,max1*max2*max3);
    }

    public static void quickSort(int[] arr,int left, int right){
        if (left < right){
        int i = left;
        int j = right;
        int pivot = arr[left];
        while (i<j){
            while (i<j && arr[j]>=pivot){ --j; }
                if (i<j) { arr[i++]=arr[j]; }
            while (i<j && arr[i]<=pivot){ ++i; }
                if (i<j){ arr[j--]=arr[i]; }
        }
        arr[i] = pivot;
        quickSort(arr,left,i-1);
        quickSort(arr,i+1,right);
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        return head;
    }


    public int maxProduct(int[] nums) {
        if(null == nums) return 0;
        int len = nums.length;
        int[][] dp = new int[2][len+1];
        dp[0][0] = nums[0];
        dp[1][0] = nums[0];
        int max = nums[0];
        for (int i=1;i<len;++i){
            dp[0][i] = Math.max(Math.max(dp[0][i-1]*nums[i],dp[1][i-1]*nums[i]),nums[i]);
            dp[1][i] = Math.min(Math.min(dp[0][i-1]*nums[i],dp[1][i-1]*nums[i]),nums[i]);
            int tmp = dp[0][i]>dp[1][i]?dp[0][i]:dp[1][i];
            if (max < tmp){
                max = tmp;
            }
        }

        return max;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        LinkedList<List<Integer>> result = new LinkedList<>();

        while (!queue.isEmpty()){
            levelOrder(result,queue.size(),queue);
        }
//        Collections.reverse(result);
        return result;
    }
    public void levelOrder(LinkedList<List<Integer>> result,
                           int num,Queue<TreeNode> queue) {
        List<Integer> tmp = new ArrayList<>();
        for(int i=0;i<num;++i){
            TreeNode node = queue.poll();
            tmp.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        result.addFirst(tmp);
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        if(null==nums || nums.length==0) return null;
        return sortedArrayToBST(nums, 0, nums.length-1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if(start > end) return null;
        int mid = (start+end)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums, start, mid-1);
        root.right = sortedArrayToBST(nums, mid+1, end);
        return root;
    }

    public boolean isBalanced(TreeNode root) {
        if(null == root) return true;
        return check(root) != -1;
    }
    public int check(TreeNode root){
        if(null == root) return 0;
        int left = check(root.left);
        int right = check(root.right);
        if(left == -1 || right == -1) return -1;
        if(Math.abs(left - right) > 1) return -1;
        return Math.max(left, right)+1;
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i=0;i<numRows;++i){
            switch (i){
                case 0:
                    result.add(new ArrayList<Integer>(){{add(1);}});
                    break;
                case 1:
                    result.add(new ArrayList<Integer>(){{add(1);add(1);}});
                    break;
                default:
                    List<Integer> list = new ArrayList<>();
                    list.add(1);
                    for(int j=1;j<i;++j){
                        List<Integer> pre = result.get(i-1);
                        list.add(pre.get(j-1)+pre.get(j));
                    }
                    list.add(1);
                    result.add(list);
                    break;
            }
        }

        return result;
    }

    public String intToRoman(int num) {
        if (num == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (num>0){
            if (num >= 1000){
                sb.append("M");
                num = num - 1000;
            }else if (num >= 900){
                sb.append("CM");
                num = num - 900;
            }else if (num >= 500){
                sb.append("D");
                num = num - 500;
            }else if (num >= 400){
                sb.append("CD");
                num = num - 400;
            }else if (num >= 100){
                sb.append("C");
                num = num - 100;
            }else if (num >= 90){
                sb.append("XC");
                num = num - 90;
            }else if (num >= 50){
                sb.append("L");
                num = num - 50;
            }else if (num >= 40){
                sb.append("XL");
                num = num - 40;
            }else if (num >= 10){
                sb.append("X");
                num = num - 10;
            }else if (num >= 9){
                sb.append("IX");
                num = num - 9;
            }else if (num >= 5){
                sb.append("V");
                num = num - 5;
            }else if (num >= 4){
                sb.append("IV");
                num = num-4;
            }else if (num >= 1){
                sb.append("I");
                num = num - 1;
            }
        }
        return sb.toString();
    }

    public boolean isPalindrome(String s) {
        String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int len = str.length();
        for(int i=0;i<len;++i){
            int tmpChart = str.charAt(i);
            int t = str.charAt(len-1-i);
            if(tmpChart != t) return false;
        }
        return true;
    }

    public int singleNumber(int[] nums) {
        //可以使用异或
        int len = nums.length;
        int result = nums[0];
        for(int i=1;i<len;++i){
            result = result^nums[i];
        }
        return result;
    }

    public static class MinStack {

        private final ArrayDeque deque;
        private int length;
        /** initialize your data structure here. */
        public MinStack() {
            this.deque = new ArrayDeque<>();
            this.length = 0;
        }

        public void push(int x) {
            this.deque.addLast(x);
            this.length += 1;
        }

        public void pop() {
            if(length==0){
                return;
            }
            deque.removeFirst();
            this.length = this.length - 1;
        }

        public int top() {
            if(length==0){
                return 0;
            }
            int top = (int) deque.getFirst();
            return top;
        }

        public int getMin() {
            int min=Integer.MAX_VALUE;
            for(Object m:deque){
                if((int)m<min){
                    min = (int)m;
                }
            }
            return min;
        }
    }

    public int[] twoSum2(int[] numbers, int target) {
        Map<Integer,Integer> map = new HashMap<>();

        int len = numbers.length;
        for (int i = 0; i < len; ++i) {
            map.put(target - numbers[i], i+1);
        }
        for (int i = 0; i < len; ++i) {
            if(map.get(numbers[i])!=null){
                return new int[]{i+1,map.get(numbers[i])};
            }
        }
        return new int[]{};
    }

    public String convertToTitle(int n) {
        //26个英文字母,26进制转换
        StringBuilder sb = new StringBuilder();
        while(n>0){
            --n;
            sb.insert(0, (char)('A' + n%26));
            n = n/26;
        }
        return sb.toString();
    }

    public int titleToNumber(String s) {
        //26进制转10进账
        int len = s.length();
        int result = 0;
        int exp = 0;
        for(int i=len-1;i>=0;--i){
            int tmp = s.charAt(i) - 'A' + 1;
            result += tmp * Math.pow(26, exp);
            ++exp;
        }
        return result;
    }

    public boolean findTarget(TreeNode root, int k) {
        if(root == null) return false;
        HashSet<Integer> set = new HashSet<>();
        find(root, set);
        for (Integer v : set) {
            int tmp = k-v;
            //排除相减后的值刚好等于减数的情况
            if(tmp != v && set.contains(tmp)) return true;
        }
        return false;
    }

    /**
     * 转换成普通的两数之和解法，变成求一堆数字中，有没有两个数字之和为给出的数字
     * @param root
     * @param set
     */
    public void find(TreeNode root,HashSet<Integer> set){
        if(root == null) return;
        set.add(root.val);
        find(root.left,set);
        find(root.right,set);
    }

    /**
     * 反转数组指定范围数据
     * @param nums
     * @param start
     * @param end
     */
    public void reverse(int[] nums, int start, int end){
        while (start < end){
            int tmp = nums[end];
            nums[end] = nums[start];
            nums[start] = tmp;
            ++start;
            --end;
        }
    }
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        if(len < 2 || k == 0 || len == k){
            return;
        }
        if (k>len) k = len;
        //1.先将整个数组反转
        reverse(nums, 0, len-1);
        //2.然后反转 0到k-1 位
        reverse(nums, 0, k-1);
        //3.最后反转 k到最后位，恢复原来的数组顺序
        reverse(nums, k, len-1);
    }

    public int lengthOfLongestSubstring(String s) {
        if(null == s) return 0;
        int len = s.length();
        if(len < 2) return len;
        int maxLen=0,start=0,end=0;
        Map<Character,Integer> map = new HashMap<>();
        for(int i=0;i<len;++i){
            char currency = s.charAt(i);
            if(map.getOrDefault(currency, -1) >= start){
                //有重复字母
                if((end-start+1) > maxLen){
                    maxLen = end-start+1;
                }
                //从重复字符位置后一位开始作为起始点
                start = map.get(currency) + 1;
            }
            map.put(currency, i);
            end=i;
        }
        if(end-start+1 > maxLen){
            return end-start+1;
        }else{
            return maxLen;
        }
    }

    /**
     * 三数只和
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int len = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        if(len == 0 || len < 3) return result;
        //排序
        Arrays.sort(nums);
        for(int i=0;i<len;++i){
            //最小的都大于0，则不可能存在三个数之和大于0
            if(nums[i] > 0) break;
            int L=i+1,R=len-1;
            while(L<R){
                int sum = nums[i] + nums[L] + nums[R];
                //去重
                if(i>0 && nums[i]==nums[i-1]) continue;
                if(sum==0){
                    result.add(Arrays.asList(nums[i],nums[L],nums[R]));
                    //判断左边再往前是不是和当前值相同
                    while(L<R && nums[L]==nums[L+1]) ++L;
                    //判断右边再往前是不是和当前值相同
                    while(L<R && nums[R]==nums[R-1]) --R;
                    ++L;
                    --R;
                }else if(sum>0){
                    --R;
                }else if(sum<0){
                    ++L;
                }
            }
        }
        return result;
    }

    public int myAtoi(String str) {
        if (null==str) return 0;
        String trim = str.trim();
        long result = 0;
        int len = trim.length();
        if(len==0) return 0;
        boolean positive = true;

        char cAt = trim.charAt(0);

        switch (cAt){
            case '-':
                positive=false;
                break;
            case '+':
                positive=true;
                break;
            default:
                int tmp = cAt-'0';
                if (tmp>=0 && tmp<=9) result = result + tmp;
                else return 0;
                break;
        }
        for (int i=1;i<len;++i){
            char c = trim.charAt(i);
            int tmp = c-'0';
            if (tmp>=0 && tmp<=9) result = result*10 + tmp;
            else return positive?(int) result:-(int) result;
            if (positive){
                if (result>Integer.MAX_VALUE) return Integer.MAX_VALUE;
            }else {
                if (-result<Integer.MIN_VALUE) return Integer.MIN_VALUE;
            }
        }
        return positive?(int) result:-(int) result;
    }

    /**
     * 打家劫舍
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if(null == nums) return 0;
        int len = nums.length;
        if(len==0) return 0;
        if(len==1) return nums[0];
        if(len < 3) return Math.max(nums[0], nums[1]);
        boolean[] dp = new boolean[len];
        boolean[] rdp = new boolean[len];
        dp[0] = true;
        dp[1] = false;
        rdp[len-1] = true;
        rdp[len-2] = false;
        int result = nums[0];
        int rResult = nums[len-1];
        for(int i=2;i<len;++i){
            int tmp = nums[i];
            int tmpPre = nums[i-1];
            if(result+tmp <= tmpPre && !dp[i-1]){
                //如果2者之和还没有中间的大,且前一个没有被选
                result = tmpPre;
                dp[i-2] = false;
                dp[i-1] = true;
                dp[i] = false;
            }else if(!dp[i-1]){
                //前一个没被选
                result = result + tmp;
                dp[i] = true;
            }
        }
        for(int i=len-3;i>=0;--i){
            int rtmp = nums[i];
            int rtmpPre = nums[i+1];
            if(rResult+rtmp <= rtmpPre && !rdp[i+1]){
                rResult = rtmpPre;
                rdp[i+2] = false;
                rdp[i+1] = true;
                rdp[i] = false;
            }else if(!rdp[i+1]){
                rResult = rResult + rtmp;
                rdp[i] = true;
            }
        }
        return Math.max(result, rResult);
    }


    /**
     * 求素数
     * @param n
     * @return
     */
    public int countPrimes(int n) {

        Integer.valueOf(1);


        int result = 0;
        for(int i=2;i<=n;++i){
            int v = (int)Math.sqrt(i);
            boolean flag = true;
            for(int j=2;j<=v;++j){
                if(i%j==0){
                    //除了1和本身还有可以整除的,不是素数
                    flag = false;
                    break;
                }
            }
            if(flag) ++result;
        }
        return result;
    }

    public boolean isIsomorphic(String s, String t) {
        if(s==null && t==null) return true;
        if(s==null || t==null) return false;
        int len = s.length();
        if(len!=t.length()) return false;

        Map<Character,Character> map1 = new HashMap<>();
        Map<Character,Character> map2 = new HashMap<>();
        String strA = "";
        String strB = "";
        char cA = 'a';
        char cB = 'a';
        for(int i=0;i<len;++i){
            char charA = s.charAt(i);
            if(map1.get(charA)==null){
                strA = strA + cA;
                map1.put(charA, cA++);
            }else{
                strA += map1.get(charA);
            }

            char charB = t.charAt(i);
            if(map2.get(charB)==null){
                strB = strB + cB;
                map2.put(charB, cB++);
            }else{
                strB += map2.get(charB);
            }
        }
        return strA.equals(strB);
    }

    /**
     * 翻转链表
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
//        if(head==null) return head;
        ListNode tail = null;
        while(head!=null){
            ListNode tmp = head.next;
            head.next = tail;
            tail = head;
            head = tmp;
        }
        return tail;
    }


    public boolean containsDuplicate(int[] nums) {
        if(null==nums) return false;
        int len = nums.length;
        if(len == 0 || len == 1) return false;
        // Set<Integer> duplicate = new HashSet<>();
        int duplicate = nums[0];
        for(int i=1;i<len;++i){
            duplicate = (~duplicate^nums[i]);
        }
        return duplicate == 0;
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if(nums == null) return false;
        int len = nums.length;
        if(len==2){
            if(nums[0]==nums[1] && k>=1) return true;
        }
        if(k==0) return false;
        if(len < k) return false;
        for(int i=0;i<len;++i){
            for(int j=1;j<k;++j){
                if(i+j>=len) break;
                if(nums[i] == nums[i+j]) return true;
            }
        }
        return false;
    }

    /**
     * 数组中重复的数字
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {

        int len = nums.length;
        int[] res = new int[len];
        for (int i=0;i<len;++i){
            if (res[nums[i]]!=0) return nums[i];
            else res[nums[i]] = 1;
        }
        return 0;
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix.length==0) return false;
        if (matrix[0].length==0) return false;
        int rows = matrix.length;
        int cols = matrix[0].length;
        int row = 0,col = cols-1;
        while (row<rows && col>=0){
            int num = matrix[row][col];
            if (num==target) return true;
            else if (num > target) --col;
            else if (num < target) ++row;
        }
        return false;
        /*if (matrix[0][0]>target) return false;
        for (int i=0;i<row;++i){
            for (int j=0;j<col;++j){
                if (matrix[i][j]==target) return true;
                else if (matrix[i][j]>target) break;
            }
            if (matrix[i][0]>target) break;
        }

        return false;*/

    }

    class MedianFinder {

        private final Queue<Integer> data;
//        private Queue<Integer> min,max;
        /** initialize your data structure here. */
        public MedianFinder() {
            //小顶堆，保存较大的一部分
//            min = new PriorityQueue<>();
            //大顶堆，保存较小的一部分
//            max = new PriorityQueue<>((x,y) -> (y-x));
            data = new PriorityQueue<>();
        }

        public void addNum(int num) {
            data.add(num);
        }

        public void poll(){
            data.poll();
        }

        public double findMedian() {
            int size = data.size();
            Object[] objects = data.toArray();
            double result = 0;
            if (size%2==0){
                result = ((int)objects[size/2-1] + (int)objects[size/2])/2.0;
            }else {
                result = (Integer) objects[size/2] / 1.0;
            }
            return result;
        }
    }

    public int[] reversePrint(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head!=null){
            list.add(head.val);
            head = head.next;
        }
        int size = list.size();
        int[] ints = new int[size];
        int num = 0;
        for (int i=size-1;i>=0;--i){

            ints[num++] = list.get(i);
        }
        return ints;
    }

    public int[] spiralOrder(int[][] matrix) {
        if (matrix==null || matrix.length==0 || matrix[0].length==0) return new int[0];
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result = new int[rows*cols];
        int index = 0;
        //上下
        int top=0,buttom=rows-1;
        //左右
        int left=0,right=cols-1;

        while (top< buttom && left<right){
            for (int i=left;i<=right;++i){
                result[index++] = matrix[top][i];
            }
            top++;
            for (int j=top;j<=buttom;++j){
                result[index++] = matrix[j][right];
            }
            right--;

            if (top <= buttom && left <= right){
                for (int i=right;i>=left;--i){
                    result[index++] = matrix[buttom][i];
                }
                buttom--;
                for (int j=buttom;j>=top;--j){
                    result[index++] = matrix[j][left];
                }
                left++;
            }
        }
        return result;
    }

    class CQueue {

        private final Stack<Integer> tail;

        private final Stack<Integer> head;

        public CQueue() {
            head = new Stack<>();
            tail = new Stack<>();
        }

        public void appendTail(int value) {
            tail.push(value);

        }

        public int deleteHead() {
            boolean headEmpty = head.isEmpty();
            boolean tailEmpty = tail.isEmpty();
            if (headEmpty && tailEmpty) return -1;
            if (!headEmpty) return head.pop();
            if (headEmpty && !tailEmpty){
                while (!tail.isEmpty()){
                    head.push(tail.pop());
                }
            }
            return head.pop();
        }
    }

    /**
     * 斐波那契数
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n==0) return 0;
        if (n==1) return 1;
        double[] dp = new double[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i=2;i<=n;++i){
            dp[i] = (dp[i-1] + dp[i-2])%1000000007;
        }
//        dp[n] = dp[n-1] + dp[n-2];
        return (int) (dp[n]);
        /* 此方会超时
        if (n==0) return 0;
        if (n==1) return 1;
        return fib(n-1) + fib(n-2);
        */
    }

    /**
     * 青蛙跳台问题
     * @param n
     * @return
     */
    public int numWays(int n) {
        if (n==0) return 1;
        double[] dp = new double[n+2];
        dp[1] = 1;
        dp[2] = 2;
        for(int i=3;i<=n;++i){
            dp[i] = (dp[i-1] + dp[i-2])%1000000007;
        }
        return (int)dp[n];
    }

    public int minArray(int[] numbers) {
        if (numbers==null || numbers.length==0) return 0;
        Arrays.sort(numbers);
        return numbers[0];
    }

    /**
     * 矩阵中的路径
     * 知识点 —— DFS和BFS
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if (word==null || word.length()==0) return false;
        char[] words = word.toCharArray();
        for (int i=0;i<board.length;++i){
            for (int j=0;j<board[i].length;++j){
                if (dfs4Exist(board,words,i,j,0)) return true;
            }
        }
        return false;
    }

    public boolean dfs4Exist(char[][] board, char[] word, int i, int j, int k){
        //剪支
        if (i>=board.length || i<0 || j>=board[i].length || j<0 || board[i][j]!=word[k])
            return false;
        if (k==word.length-1) return true;
        //还没匹配完
        char tmp = board[i][j];
        //防止重复匹配
        board[i][j] = '/';
        boolean result = dfs4Exist(board, word, i - 1, j, k + 1) || dfs4Exist(board, word, i + 1, j, k + 1)
                || dfs4Exist(board, word, i, j - 1, k + 1) || dfs4Exist(board, word, i, j + 1, k + 1);
        board[i][j] = tmp;
        return result;
    }

    /**
     * 重建二叉树
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder==null || preorder.length==0) return null;
        if (inorder==null || inorder.length==0) return null;
        if (preorder.length != inorder.length) return null;
        int len = inorder.length;
        //根据中序遍历的结果，判断左子树和右子树个数
        Map<Integer,Integer> indexMap = new HashMap<>();
        for (int i=0;i<len;++i){
            indexMap.put(inorder[i],i);
        }
        TreeNode root = buildTree(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1, indexMap);
        return root;
    }

    /**
     * 重建二叉树
     * @param preorder
     * @param preorderStartIndex
     * @param preorderEndIndex
     * @param inorder
     * @param inorderStartIndex
     * @param inorderEndIndex
     * @param indexMap
     * @return
     */
    private TreeNode buildTree(int[] preorder, int preorderStartIndex, int preorderEndIndex,
                               int[] inorder, int inorderStartIndex, int inorderEndIndex,
                               Map<Integer,Integer> indexMap){
        if (preorderStartIndex > preorderEndIndex) return null;
        int rootVal = preorder[preorderStartIndex];
        TreeNode root = new TreeNode(rootVal);
        if (preorderStartIndex == preorderEndIndex) return root;
        else {
            Integer rootIndex = indexMap.get(rootVal);
            //左子树个数
            int leftNodes = rootIndex - inorderStartIndex;
            //右子树个数
            int rightNodes = inorderEndIndex - rootIndex;
            //重点
            TreeNode leftSubTree = buildTree(preorder,preorderStartIndex+1,preorderEndIndex-rightNodes,
                    inorder,inorderStartIndex,rootIndex-1,indexMap);
            TreeNode rightSubTree = buildTree(preorder,preorderEndIndex-rightNodes+1,preorderEndIndex,
                    inorder,rootIndex+1,inorderEndIndex,indexMap);
            root.left = leftSubTree;
            root.right = rightSubTree;
            return root;
        }
    }

    /**
     * 矩阵问题
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        if (m==0 || n==0) return 0;
        if(k==0) return 1;
        boolean[][] isVistied = new boolean[m][n];
        return dfs4MovingCount(0,0,isVistied,m,n,k);
    }
    public int dfs4MovingCount(int x, int y, boolean[][] isVisited, int m, int n,int k){
        if (x>m-1 || y>n-1 || isVisited[x][y] || (getSum(x)+getSum(y))>k) return 0;
        isVisited[x][y] = true;
        return 1+dfs4MovingCount(x+1,y,isVisited,m,n,k) + dfs4MovingCount(x,y+1,isVisited,m,n,k);
    }
    public int bfs4MovingCount(boolean[][] isVisited, int m, int n,int k){
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0});
        int res = 0;
        while (!queue.isEmpty()){
            int[] node = queue.poll();
            int x = node[0];
            int y = node[1];
            if (x>m-1 || y>n-1 || isVisited[x][y] || (getSum(x)+getSum(y))>k) continue;
            isVisited[x][y] = true;
            res++;
            queue.add(new int[]{x+1,y});
            queue.add(new int[]{x,y+1});
        }
        return res;
    }
    public int getSum(int x){
        int tmp = x;
        int sum = 0;
        do {
            sum += tmp%10;
        }while ((tmp=tmp/10)!=0);
        return sum;
    }

    /**
     * 切绳子
     * 数学推导
     * 1.均值不等式 =》 将绳子以相等的长度分为多段，得到的乘积最大
     * 2.尽可能将绳子以长度3等分为多段时，乘积最大
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        if (n==2) return 1;
        if (n==3) return 2;
        int multi = 1;
        while (n-3 >= 0){
            multi *= 3;
            n=n-3;
        }
        if (n==1) multi = (multi/3)*4;
        if (n==2) multi = multi*2;
        return multi;

    }

    /**
     * 合并两链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1==null && l2==null) return null;
        if (l1==null && l2!=null) return l2;
        if (l1!=null && l2==null) return l1;

        ListNode root = new ListNode(0);
        ListNode head = root;
        while (l1!=null && l2!=null){
            int l1Val = l1.val;
            int l2Val = l2.val;
            if (l1Val>=l2Val){
                head.next = new ListNode(l2Val);
                head = head.next;
                l2 = l2.next;
            }else {
                head.next = new ListNode(l1Val);
                head = head.next;
                l1 = l1.next;
            }
        }
        if (l1!=null) head.next = l1;
        if (l2!=null) head.next = l2;
        return root.next;
    }

    /**
     * 是否是子树
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A==null || B==null) return false;
        //将树进行先序遍历，看是否有包含关系
        /*StringBuffer preA = new StringBuffer();
        String sA = preOrder(A, preA);
        String preOrderA = levelOrder(A);
        StringBuffer preB = new StringBuffer();
        String sB = preOrder(B, preB);
        String preOrderB = levelOrder(B);

        return preOrderA.contains(preOrderB) || sA.contains(sB);*/
        /**
         * 当B是以A的根节点为子树起始节点时 ---> preOrder(A,B)
         * 当B是以A的左子树为子树起始节点时 ---> isSubStructure(A.left,B)
         * 当B是以A的右子树为子树起始节点时 ---> isSubStructure(A.right,B)
         */
        return preOrder(A,B) || isSubStructure(A.left,B) || isSubStructure(A.right,B);
    }

    private boolean preOrder(TreeNode A,TreeNode B){
        if (B==null) return true;
        if (A==null || A.val != B.val) return false;
        return preOrder(A.left,B.left) && preOrder(A.right,B.right);
    }

    /**
     * 先序遍历
     * @param root
     * @param res
     * @return
     */
    private String preOrder(TreeNode root,StringBuffer res){
        if (root == null) return null;
        res.append(root.val);
        if (root.left!=null) preOrder(root.left,res);
        if (root.right!=null) preOrder(root.right,res);
        return res.toString();
    }

    /**
     * 层序遍历
     * @param root
     * @return
     */
    private String levelOrder(TreeNode root){
        Queue<TreeNode> queue = new ArrayDeque<>();
        StringBuffer sb = new StringBuffer();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode poll = queue.poll();
            if (poll.left!=null) queue.add(poll.left);
            if (poll.right!=null) queue.add(poll.right);
            sb.append(poll.val);
        }
        return sb.toString();
    }

    /**
     * 二叉树的镜像
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {
        if (root==null) return root;

        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        mirrorTree(root.left);
        mirrorTree(root.right);
        return root;
    }

    /**
     * 是否对称二叉树
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root==null) return true;
        return isSymmetric(root.left,root.right);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left==null && right==null) return true;
        /*if (left==null && right!=null) return false;
        if (right==null && left!=null) return false;
        if (left.val != right.val) return false;*/
        if (left == null || right == null || left.val!=right.val) return false;

        return isSymmetric(left.left,right.right) && isSymmetric(left.right,right.left);
    }

    /**
     * 是否是有效数字 —— 有限状态机
     * @param s
     * @return
     */
    public boolean isNumber1(String s) {
        Map[] states = {
                new HashMap<Character,Integer>() {{ put(' ', 0); put('s', 1); put('d', 2); put('.', 4); }}, // 0.
                new HashMap<Character,Integer>() {{ put('d', 2); put('.', 4); }},                           // 1.
                new HashMap<Character,Integer>() {{ put('d', 2); put('.', 3); put('e', 5); put(' ', 8); }}, // 2.
                new HashMap<Character,Integer>() {{ put('d', 3); put('e', 5); put(' ', 8); }},              // 3.
                new HashMap<Character,Integer>() {{ put('d', 3); }},                                        // 4.
                new HashMap<Character,Integer>() {{ put('s', 6); put('d', 7); }},                           // 5.
                new HashMap<Character,Integer>() {{ put('d', 7); }},                                        // 6.
                new HashMap<Character,Integer>() {{ put('d', 7); put(' ', 8); }},                           // 7.
                new HashMap<Character,Integer>() {{ put(' ', 8); }}                                         // 8.
        };
        int p = 0;
        char t;
        for(char c : s.toCharArray()) {
            if(c >= '0' && c <= '9') t = 'd';
            else if(c == '+' || c == '-') t = 's';
            else t = c;
            if(!states[p].containsKey(t)) return false;
            p = (int)states[p].get(t);
        }
        return p == 2 || p == 3 || p == 7 || p == 8;
    }

    /**
     * 是否是有效数字
     * @param s
     * @return
     */
    public boolean isNumber(String s){
        if (s==null) return false;
        s = s.trim();
        if (s.length() == 0) return false;
        boolean numSeen = false;
        boolean dotSeen = false;
        boolean eSeen = false;
        char[] chars = s.toCharArray();
        int length = chars.length;
        for (int i=0;i<length;++i){
            if (chars[i]>='0' && chars[i]<='9'){
                numSeen = true;
            }else if (chars[i]=='.'){
                //.之前不能出现e或.
                if (eSeen || dotSeen) return false;
                //.也不能作为最后一个或第一个
//                if (i==0 || i==length-1) return false;
                dotSeen = true;
            }else if (chars[i]=='e' || chars[i]=='E'){
                //e之前不能出现e，必须出现数字
                if (eSeen || !numSeen) return false;
                eSeen = true;
                //重置numSeen，排除 123e或者123e+的情况，确保e之后也出现数字
                numSeen = false;
            }else if (chars[i]=='-' || chars[i]=='+'){
                //-+出现在0位置或者e/E的后面第一个位置才是合法的
                if (i!=0 && chars[i-1]!='e' && chars[i-1]!='E') return false;
            }else {
                //其他不合法字符
                return false;
            }
        }
        return numSeen;
    }

    /**
     * 调整数组顺序，使奇数在偶数前
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        if (nums==null || nums.length==0) return new int[0];
        int len = nums.length;
        int i = 0,j=len-1;
        while (i<j){
            while (i<j && nums[i]%2!=0) ++i;
            if (i>=j) break;
            int tmp = nums[i];
            while (i<j && nums[j]%2==0) --j;
            nums[i] = nums[j];
            nums[j] = tmp;
        }
        return nums;
    }

    /**
     * 二进制中，1 的个数
     * @param n
     * @return
     */
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        //方法一：
        /*int res = 0;
        while (n!=0){
            res += n & 1;
            n >>>= 1; //无符号右移
        }
        return res;*/
        //方法二
        int res = 0;
        while (n!=0){
            n &= n-1;
            res++;
        }
        return res;
    }

    /**
     * 倒数K个元素 —— 快慢指针做法，直接遍历两次的话无法解决 链表有环问题
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head==null) return head;
        ListNode faster = head;
        ListNode slower = head;
        int t = 0;
        while (faster!=null){
            if (t++ < k) {
                faster = faster.next;
                continue;
            }
            slower = slower.next;
            faster = faster.next;
        }
        return slower;
    }

    /**
     * 旋转字符串
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords(String s, int n) {
        if(s==null) return s;
        int len = s.length();
        if(len <= n) return s;

        String pre = s.substring(0, n);
        String aft = s.substring(n, len);
        return MessageFormat.format("{0}{1}",aft,pre);
    }

    /**
     * 打印数字
     * @param n
     * @return
     */
    public int[] printNumbers(int n) {
        int size = 0;
        for (int i=1;i<=n;++i){
            size = size*10+9;
        }
        int[] res = new int[size];
        for(int i=1;i<=size;++i){
            res[i-1] = i;
        }
        return res;
    }

    /**
     * 二叉树的深度 DFS
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root==null) return 0;

        return 1+Math.max(maxDepth(root.left),maxDepth(root.right));
    }

    class Class4kthLargest{
        int result,k;
        /**
         * 二叉树中第K大节点
         * 问题变为 求 “二叉搜索树第 k 大的节点” 可转化为求 “此树的中序遍历倒序的第 k 个节点”
         * @param root
         * @param k
         * @return
         */
        public int kthLargest(TreeNode root, int k) {
            if (root==null) return 0;
            this.k = k;
            result = 0;
            this.reverseInOrder(root);
            return result;

        }
        /**
         * 中序遍历的逆序
         */
        private void reverseInOrder(TreeNode root){
            if (root==null) return;
            this.reverseInOrder(root.right);
            if (k == 0) return;
            if (--k == 0) result = root.val;
            this.reverseInOrder(root.left);
        }
    }
    /**
     * 二叉树中第K大节点
     * 问题变为 求 “二叉搜索树第 k 大的节点” 可转化为求 “此树的中序遍历倒序的第 k 个节点”
     * @param root
     * @param k
     * @return
     */
    public int kthLargest(TreeNode root, int k) {
        /*
        if (root==null) return 0;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()){
            TreeNode poll = queue.poll();
            if (poll.left!=null) queue.add(poll.left);
            if (poll.right!=null) queue.add(poll.right);
            res.add(poll.val);
        }
        res.sort((o1,o2)->(o2-o1));
        return res.get(k-1);
        */

        if (root==null) return 0;
        List<Integer> result = new ArrayList<>();
        reverseInOrder(root,result,k);
        int size = result.size();
        if (size==0) return 0;
        return result.get(0);

    }

    /**
     * 中序遍历
     */
    private void inOrder(TreeNode root, List<Integer> result){
        if (root==null) return;
        inOrder(root.left,result);
        result.add(root.val);
        inOrder(root.right,result);
    }
    /**
     * 中序遍历的逆序
     */
    private void reverseInOrder(TreeNode root, List<Integer> result,int k){
        if (root==null) return;
        reverseInOrder(root.right,result,k);
        result.add(root.val);
        if (result.size()==k) return;
        reverseInOrder(root.left,result,k);
    }


    /**
     * 合为 target 的连续正整数 —————— 滑动窗口/双指针
     * @param target
     * @return
     */
    public int[][] findContinuousSequence(int target) {
        List<int[]> result = new ArrayList<>();
        //滑动窗口的左右边界和窗口内的数字和
        int i = 1,j = 1, sum = 0;
        //做边界如果比target一半还大，那左边界+右边界一定大于 target
        while (i <= target/2){
            if (sum > target){
                //和比 target 大，缩小移动窗口, 左边界向右移动
                sum -= i;
                ++i;
            }else if (sum < target){
                //和比 target 小，扩大移动窗口，右边界向右移动
                sum += j;
                ++j;
            }else {
                //记录结果
                int[] r = new int[j-i];
                for (int tmp=i;tmp<j;++tmp){
                    r[tmp-i] = tmp;
                }
                result.add(r);
                //左边界向右移动
                sum -= i;
                ++i;
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    /**
     * 二叉树的层序遍历,按层分数组(当前队列的个数 size() 即为本层元素个数)
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        if (null==root) return null;
        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()){
            //当前层数个数
            int size = queue.size();
            List<Integer> r = new ArrayList<>();
            for (int i=0;i<size;++i){
                TreeNode poll = queue.poll();
                r.add(poll.val);
                if (poll.left!=null) queue.add(poll.left);
                if (poll.right!=null) queue.add(poll.right);
            }
            result.add(r);
        }
        return result;
    }

    /**
     * 二叉树的最近公共节点
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        //给定的两个节点都不在此树中
        if (left == null && right == null) return null;
        //p，q都不在左子树中，直接返回先遇到的
        if (left == null) return right;
        //p，q都不在右子树中，直接返回先遇到的
        if (right == null) return left;
        //p，q分别属于不同的子树
        return root;
    }

    /**
     * 数组中出现次数超过一半的数字
     * 此类题常见解法如下：
     * 1.哈希表统计法：遍历数组，用HashMap统计各数字的数量，最终超过数组长度一半的数字则为众数。此方法时间和空间复杂的均为O(n)
     * 2.数组排序法：将数组nums排序，由于众数的数量超过数组长度的一半，因此数组中点的元素一定为众数。此方法时间复杂度O(Nlog2N)
     * 3.摩尔投票法：核心理念为"正负抵消"；时间和空间复杂度分别为O(N)和O(1)
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        /*
        // 哈希表统计法
        if (nums==null || nums.length==0) return 0;
        int length = nums.length;
        int m = (length-1)/2;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i=0;i<length;++i){
            int key = nums[i];
            Integer integer = map.get(key);
            if (integer == null) map.put(key,1);
            else{
                map.put(key,integer+1);
                if (integer+1 > m) return key;
            }
        }
        return 0;
        */

        //摩尔投票法
        if (nums==null || nums.length==0) return 0;
        int vote = 0,result = nums[0];
        int len = nums.length;
        for (int i=0;i<len;++i){
            if (vote == 0) result = nums[i];
            if (result == nums[i]) vote++;
            else vote--;
        }
        //扩展：如何众数不一定在数组中存在
        int count = 0;
        for (int i=0;i<len;++i){
            if (nums[i]==result) ++count;
        }
        //无众数时，返回0
        if (count<=len/2) return 0;
        return result;
    }

    /**
     * 两数之和  —— 双指针
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {

        /*
        int len = nums.length;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<len;i++){
            int tmp = nums[i];
            int t = target - tmp;
            if(map.get(tmp)==null){
                // map.put(tmp, t);
                map.put(t, tmp);
            }else{
                return new int[]{map.get(nums[i]),i};
            }
        }
        return null;
        * */

        if (nums==null || nums.length==0) return new int[0];
        int len = nums.length;
        int left = 0,right = len-1;
        int sum = 0;
        int[] result = new int[2];
        while (left<right){
            sum = nums[left] + nums[right];
            if (sum>target){
                --right;
            }else if (sum<target){
                ++left;
            }else {
                result[0] = nums[left];
                result[1] = nums[right];
                return result;
            }
        }
        return result;
    }


    /**
     * 两个链表的第一个公共节点
     * 双指针，A和B分别向前走，如果A走到头了，那么A从headB的头开始继续；如果B走到头了，那么B从headA的头开始继续
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA==null || headB==null) return null;
        ListNode a = headA,b = headB;
        while (a!=b){
            a = a!=null?a.next:headB;
            b = b!=null?b.next:headA;
        }
        return a;
    }

    /**
     * 回文链表
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if(head == null) return true;

        ListNode midNode = findMidNode(head);
        ListNode secondHalfHead = reverseLinked(midNode.next);
        ListNode curr1 = head;
        ListNode curr2 = secondHalfHead;

        boolean palindrome = true;
        while(palindrome && curr2 != null){
            if(curr1.val != curr2.val) palindrome = false;
            curr1 = curr1.next;
            curr2 = curr2.next;
        }

        return palindrome;
    }

    /* 反转链表 */
    private ListNode reverseLinked(ListNode head){
        ListNode cur = head;
        ListNode prev = null;
        while(cur != null){
            ListNode nextTemp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nextTemp;
        }
        return prev;
    }

    /* 快慢指针寻找中间节点 */
    private ListNode findMidNode(ListNode head){
        ListNode fast = head;
        ListNode low = head;
        while(fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            low = low.next;
        }
        return low;
    }

    /**
     * 回文链表
     * @param head
     * @return
     */
    public boolean isPalindrome2(ListNode head) {
        if(head==null) return true;
        ListNode faster = head;
        ListNode slower = head;
        ListNode tmp;
        while(faster.next != null && faster.next.next!=null){
            faster = faster.next.next;
            slower = slower.next;
        }
        //中间节点
        tmp = slower.next;
        //翻转后续链表
        ListNode tail = null;
        while(tmp!=null){
            //保存当前节点的下一个节点作为临时节点
            ListNode nextTmp = tmp.next;
            //交换节点指向
            tmp.next = tail;
            tail = tmp;
            tmp = nextTmp;
        }
        //判断回文链表
        while(tail!=null){
            if(head.val != tail.val) return false;
            head = head.next;
            tail = tail.next;
        }
        return true;
    }

    /**
     * 圆圈中最后剩下的数字 ———— 约瑟夫环
     * 1.模拟做法
     * 2.数学方法：f(N,M) = (f(N-1,M)+M)%N
     * 其中：
     *      1.f(N,M)表示，N个数，每报到M时去掉那个数，最终胜利者的编号
     *      2.f(N-1,M)表示，N-1个数，每报到M时去掉那个数，最终胜利者的编号
     * @param n
     * @param m
     * @return
     */
    public int lastRemaining(int n, int m) {
        /*
        //模拟做法
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<n;++i){
            list.add(i);
        }
        int count = 0;
        while (n>1){
            //关键地方——计算移除的下标位置，因为下标是从0开始的，所以要 -1
            count = (count+m-1)%n;
            list.remove(count);
            --n;
        }
        return list.get(0);
        */
        //数学方法 —————— 约瑟夫环
        int ans = 0;
        for (int i=2;i<=n;++i){
            ans = (ans+m) % i;
        }
        return ans;
    }

    /**
     * 第一个只出现一次的字符
     *
     * 方法二：
     * 由于给定字符串中只包含小写字母，而小写字母只有26个，
     * 因此可以定义一个大小为26的整数数组来保存字符串中出现的各个小写字母的个数，
     * 最后再遍历一遍给定字符串找到第一个出现一次的字符并返回。
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        if (s==null || s.trim()==" ") return ' ';
        Map<Character,Boolean> map = new HashMap<>();
        char[] chars = s.toCharArray();
        int len =chars.length;
        for (int i=0;i<len;++i){
            char aChar = chars[i];
            map.put(aChar,map.containsKey(aChar));
        }
        for (char c:chars){
            if (!map.get(c)) return c;
        }
        return ' ';
    }

    /**
     * 0～n中缺失的数字
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int length = nums.length;
        for (int i=0;i<length;++i){
            if (nums[i]!=i) return i;
        }
        return length;
    }

    public static void main(String[] args) {

        Solution solution = new Solution();
        solution.firstUniqChar("leetcode");

        solution.printNumbers(1);

        String format = MessageFormat.format("{0}:{1}", 1, 2);
        System.out.println(format);

        int[] exchange = solution.exchange(new int[]{1, 3, 5});

        solution.isNumber("44e016912630333");
        TreeNode isSubStructure = new TreeNode(1);
        isSubStructure.left = new TreeNode(2);
        isSubStructure.right = new TreeNode(3);
        isSubStructure.left.left = new TreeNode(4);
        isSubStructure.left.right = new TreeNode(5);
        isSubStructure.right.left = new TreeNode(6);

        TreeNode isSubStructureB = new TreeNode(1);
        isSubStructureB.left = new TreeNode(2);
        isSubStructureB.right = new TreeNode(3);
        isSubStructureB.left.left = new TreeNode(4);

        boolean subStructure = solution.isSubStructure(isSubStructure, isSubStructureB);
        System.out.println(subStructure);

        int cuttingRope = solution.cuttingRope(6);
        System.out.println(cuttingRope);
        int i6 = solution.movingCount(38, 15, 9);
        char[][] board = {
                {
                    'a','a'
                }
        };
        boolean exist = solution.exist(board, "aaa");
        System.out.println(exist);

        int i5 = solution.numWays(1);
        System.out.println(i5);
        int fib = solution.fib(1);
        System.out.println(fib);
        CQueue cQueue = solution.new CQueue();
        cQueue.appendTail(1);
        cQueue.appendTail(2);
        int i4 = cQueue.deleteHead();
        System.out.println(i4);

        int repeatNumber = solution.findRepeatNumber(new int[]{2, 3, 1, 0, 2, 5, 3});
        System.out.println(repeatNumber);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = now.withMonth(1).withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        System.out.println(localDateTime.toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(System.currentTimeMillis());
        long l = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println(l);
        System.out.println(now.toEpochSecond(ZoneOffset.of("+8")));

        int day = 1;
//        int dayNum = switch (day){
//            case 1 -> 10;
//            default -> throw new IllegalStateException("Unexpected value: " + day);
//        };
//        System.out.println(dayNum);

        int atoi1 = solution.myAtoi("            ");


        solution.containsNearbyDuplicate(new int[]{0,1,2,3,2,5},3);

        boolean b = solution.containsDuplicate(new int[]{1, 2, 3, 1});
        System.out.println(b);

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        solution.reverseList(head);

        solution.isIsomorphic("foo","bar");

        int i3 = solution.countPrimes(10);
        System.out.println(i3);

        solution.rob(new int[]{2,1,1,2});

        LocalDateTime dateTime = LocalDateTime.now().withHour(20).withMinute(0);

        String s2 = Integer.MIN_VALUE - 1 + "";
        int atoi = solution.myAtoi((s2));

        Long integer = Long.valueOf("-12691264978078789");

//        ExecutorService exec = Executors.newSingleThreadExecutor();
//        exec.shutdownNow();

        String s1 = Integer.toBinaryString(1);
        int abcabcbb = solution.lengthOfLongestSubstring("aab");
        System.out.println(abcabcbb);

        int[] numsArr = new int[]{1,2};
        solution.rotate(numsArr,3);

        System.out.println(Arrays.toString(numsArr));
        int zy = solution.titleToNumber("A");
        System.out.println(zy);
        solution.convertToTitle(701);
        int[] ints = solution.twoSum2(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(ints));

        MinStack minStack = new MinStack();
        minStack.push(2);
        minStack.push(0);
        minStack.push(3);
        minStack.push(0);
        int min = minStack.getMin();
        System.out.println(min);
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        int min1 = minStack.getMin();
        System.out.println(min1);


        int i2 = solution.singleNumber(new int[]{1, 1, 2, 2, 3});
        System.out.println(i2);

        boolean palindrome = solution.isPalindrome("0P");
        System.out.println(palindrome);

        boolean isSpec = solution.isSpecialChar("A man, a plan, a canal: Panama", "[^a-zA-Z]");
        System.out.println(isSpec);


        String s = solution.intToRoman(4);
        System.out.println(s);

        List<List<Integer>> generate = solution.generate(5);
        System.out.println(generate);
        TreeNode node1 = solution.sortedArrayToBST(new int[]{-10, -3, 0, 5, 9});


        solution.maxProduct(new int[]{2,-5,-2,-4,3});

        TreeNode node = new TreeNode(1);
        solution.maxDepth(node);


        ListNode deleteDuplicates = new ListNode(1);
        deleteDuplicates.next = new ListNode(1);
        deleteDuplicates.next.next = new ListNode(1);
        deleteDuplicates.next.next.next = new ListNode(2);
        deleteDuplicates.next.next.next.next = new ListNode(2);
        deleteDuplicates.next.next.next.next.next = new ListNode(3);
        deleteDuplicates.next.next.next.next.next.next = new ListNode(3);
        deleteDuplicates.next.next.next.next.next.next.next = new ListNode(4);
        solution.deleteDuplicates(deleteDuplicates);


        String accountJson = "{\"address\":\"0c4b5b7941e5aab3b0e148600989eb2c59324590\",\"encrypted\":\"0ab0f25c610907e44e8f2845055366c4a39eca12c032e64bfde19c5ec8fbd6c2c6f431ea0e0e22ac\",\"version\":\"1.0\",\"algo\":\"0x02\"}";
        System.out.println(accountJson);


        int[] nums1 = new int[]{1,3};
        int[] nums2 = new int[]{2,3};

        double medianSortedArrays = solution.findMedianSortedArrays(nums1, nums2);
        System.out.println(medianSortedArrays);
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(2);
        listNode1.next.next = new ListNode(3);

        ListNode listNode2 = new ListNode(1);
        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);
//        ListNode listNode = solution.addTwoNumbers(listNode1, listNode2);
//        System.out.println(listNode);


        ListNode listNode = solution.mergeTwoLists(listNode1, listNode2);
        System.out.println(listNode);

        boolean valid = solution.isValid("[])");
        System.out.println(valid);

//        int[] nums = new int[]{0,0,1,1,1,2,3,4,5,5,6};
//        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};
//        int[] nums = new int[]{3,2,2,3};
        int[] nums = new int[]{9,9,9,9};
//        solution.removeDuplicates(nums);
//        solution.removeElement(nums,3);

        int i = solution.strStr("hello", "ll");
        System.out.println(i);

        int i1 = solution.maxSubArray(nums);
        System.out.println(i1);

        System.out.println(solution.plusOne(nums));

        solution.addBinary("1010","1011");

        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);
        TreeNode treeNode = solution.invertTree(root);
        System.out.println(treeNode);

        System.out.println(solution.twoSum(new int[]{2,7,11,15},9));

        solution.maximumProduct(new int[]{9,1,5,6,7,2});


        boolean specialChar = solution.isSpecialChar("12104578", "\\d{8}");
        System.out.println(specialChar);

    }

    private boolean isSpecialChar(String str,String... reg){
        String regEx="[_`~!@#$%^&*()+=|{}:;，。、\\[\\]<>《》/?￥……（）——「」【】]";
        if (null != reg && reg.length > 0){
            regEx = reg[0];
        }
        Pattern pattern=Pattern.compile(regEx);
        Matcher matcher=pattern.matcher(str);
        return matcher.find();
    }
}
