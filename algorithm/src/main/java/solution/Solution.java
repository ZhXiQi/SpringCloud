package solution;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingDeque;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/7/4 00:18
 */
public class Solution {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
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
     * 两个数组的交集
     * 双指针
     * hash表法：先遍历大的key为元素，value为出现次数，小表匹配的时候匹配到这个key，value就减一
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2==null) return new int[0];
        if (nums1.length==0 || nums2.length==0) return new int[0];
        int lenNum1 = nums1.length;
        int lenNum2 = nums2.length;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i= 0,j=0;
        List<Integer> result = new ArrayList<>();
        while (i<lenNum1 &&j<lenNum2){
            if (nums1[i]==nums2[j]) {
                result.add(nums1[i]);
                ++i;
                ++j;
            }
            else if (nums1[i]<nums2[j]) ++i;
            else ++j;

        }
        int size = result.size();
        int[] ints = new int[size];
        for (int k=0;k<size;++k){
            ints[k] = result.get(k);
        }
        return ints;
    }

    /**
     * 连续子数组的最大和
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums==null || nums.length==0) return 0;
        int length = nums.length;
        int[] dp = new int[length];
        dp[0] = nums[0];
        int result = dp[0];
        for (int i=1;i<length;++i){
            dp[i] = Math.max(dp[i-1]+nums[i],nums[i]);
            if (dp[i]>result) result = dp[i];
        }
        return result;
    }

    /**
     * 删除链表的节点
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head==null) return head;
        ListNode tmp = head;
        ListNode tmpNext = tmp.next;
        while (tmpNext!=null){
            if (tmpNext.val==val){
                tmp.next = tmpNext.next;
            }
            if (tmp.val == val){
                head = tmp.next;
            }
            tmp = tmp.next;
            tmpNext = tmpNext.next;
        }
        if (tmp!=null && tmp.val==val) head = tmp.next;
        return head;
    }

    /**
     * 构建乘积数组
     * 不能用除法 —— 分为 上三角 和 下三角  两部分
     * @param a
     * @return
     */
    public int[] constructArr(int[] a) {
        if (a==null || a.length==0) return new int[0];
        int length = a.length;
        int[] b = new int[length];
        Arrays.fill(b,1);
        int left = 1,right = 1;
        for (int i=0;i<length;++i){
            //左边
            b[i] = b[i]*left;
            left = left*a[i];
            //右边
            b[length-1-i] = b[length-1-i]*right;
            right = right*a[length-1-i];
        }
        return b;
    }

    /**
     * 平衡二叉树
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return Math.abs(dfs(root.left)-dfs(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    public int dfs(TreeNode root) {
        if (root==null) return 0;
        return 1+Math.max(dfs(root.left),dfs(root.right));
    }

    /**
     * 在排序数组中查找数字
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int len = nums.length;
        int count = 0;
        for (int i=0;i<len;++i){
            if (target==nums[i]) count++;
        }
        return count;
    }

    /**
     * 扑克牌的顺子
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {

        int zeroCnt = 0;
        int diffCnt = 0;
        Arrays.sort(nums);
        int length = nums.length-1;
        for (int i=0;i<length;++i){
            if (nums[i]==0) ++zeroCnt;
            else {
                if (nums[i]==nums[i+1]) return false;
                if (nums[i]+1!=nums[i+1]) diffCnt += nums[i+1]-nums[i]-1;
            }
        }
        return zeroCnt>=diffCnt;
    }

    /**
     * 滑动窗口的最大值
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums==null || nums.length==0) return new int[0];
        List<Integer> result = new ArrayList<>();
        int length = nums.length;
        int left=0,right=0;
        while (left<=(length-k)){
            right = left;
            int tmpLen = left + k;
            int maxNum = Integer.MIN_VALUE;
            for (;right<tmpLen;++right){
                if (maxNum<nums[right]) maxNum=nums[right];
            }
            result.add(maxNum);
            ++left;
        }
        int size = result.size();
        int[] ints = new int[size];
        for (int i=0;i<size;++i){
            ints[i] = result.get(i);
        }
        return ints;
    }

    /**
     * 翻转单词顺序
     * 说明：
     * · 无空格字符构成一个单词。
     * · 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
     * · 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if (s==null) return s;
        String trim = s.trim();
        String[] split = trim.split(" ");
        StringBuffer sb = new StringBuffer();
        int length = split.length;
        for (int i=length-1;i>=0;--i){
            if (!split[i].equals("")) {
                sb.append(split[i]);
                if (i!=0) sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 位运算模拟加减法
     * ^ 亦或 ----相当于 无进位的求和， 想象10进制下的模拟情况：（如:19+1=20；无进位求和就是10，而非20；因为它不管进位情况）
     * & 与  ----相当于求每位的进位数， 先看定义：1&1=1；1&0=0；0&0=0；即都为1的时候才为1，正好可以模拟进位数的情况,还是想象10进制下模拟情况：
     *      （9+1=10，如果是用&的思路来处理，则9+1得到的进位数为1，而不是10，所以要用<<1向左再移动一位，这样就变为10了）；
     *
     * 这样公式就是：（a^b) + ((a&b)<<1) 即：每次无进位求和 + 每次得到的进位数--------我们需要不断重复这个过程，直到进位数为0为止；
     *
     * << 左移，不分正负数
     * >> 右移，分正负数
     * >>> 无符号右移
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b) {
        //(a ^ b) 求和、(a & b) << 1 求进位
        return b == 0 ? a : add((a ^ b),(a & b) << 1);
    }

    /**
     * 求 1+2+...+n
     * 位运算的运用
     * @param n
     * @return
     */
    public int sumNums(int n) {
        return n==0?0:n+sumNums(n-1);
    }

    /**
     * 数组中数字出现的次数 II
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        if (nums==null || nums.length==0) return 0;
        Map<Integer,Boolean> map = new HashMap<>();
        int len = nums.length;
        for (int i=0;i<len;++i){
            map.put(nums[i],map.containsKey(nums[i]));
        }
        Set<Integer> integers = map.keySet();
        for (Integer i:integers){
            if (!map.get(i)) return i;
        }
        return 0;
    }

    /**
     * 数组中数字出现的次数(2个数字落单) —— 单个数字不同，直接异或即可
     * 分组异或
     * 两个数字只出现一次，其他都出现两次
     * 如果我们可以把所有数字分成两组，使得：
     *
     * 1.两个只出现一次的数字在不同的组中；
     *
     * 2.相同的数字会被分到相同的组中。
     *
     * 那么对两个组分别进行异或操作，即可得到答案的两个数字。这是解决这个问题的关键。
     *
     * 记这两个只出现了一次的数字为 a 和 b，那么所有数字异或的结果就等于 a 和 b 异或的结果
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
        if (nums==null || nums.length==0) return new int[0];
        int sum = 0;
        for (int num:nums){
            sum ^= num;
        }
        //获取分组的条件,数字按位与其负数，得到某一位为1，其余位为0的数字
        int groupBit = sum & (-sum);
        int[] result = new int[2];
        for (int num:nums){
            //分组
            if ((num & groupBit) == 0) {
                result[0] ^= num;
            }else {
                result[1] ^= num;
            }
        }
        return result;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 深拷贝链表
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head==null) return head;
        return null;
    }

    /**
     * 礼物的最大价值
     * 动态转移方程：
     * f(i, j) = max{f(i - 1, j), f(i, j - 1)} + grid[i][j]
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        if (grid==null) return 0;
        /*
        int rowLen = grid.length;
        int colLen = grid[0].length;
        int[][] dp = new int[rowLen][colLen];
        for (int i=0;i<rowLen;++i){
            for (int j=0;j<colLen;++j){
                dp[i][j] = Math.max(dp[i-1<0?0:i-1][j]+grid[i][j],dp[i][j-1<0?0:j-1]+grid[i][j]);
            }
        }
        return dp[rowLen-1][colLen-1];
        */

        //取列那就先循环行，再循环列,如果取了行长，那就是按照每行数据循环
        //将二维降级为一维处理
        int colLen = grid[0].length;
        int[] dp = new int[colLen];
        dp[0] = grid[0][0];

        int length = dp.length;
        for (int i=1;i<length;++i){
            dp[i] = dp[i-1] + grid[0][i];
        }
        int rowLen = grid.length;
        for (int i=1;i<rowLen;++i){
            dp[0] += grid[i][0];
            for (int j=1;j<length;++j){
                dp[j] = grid[i][j] + Math.max(dp[j-1],dp[j]);
            }
        }
        return dp[length-1];
    }

    /**
     * 层序遍历二叉树
     * @param root
     * @return
     */
    public int[] levelOrder(TreeNode root) {
        if (root==null) return new int[0];
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()){
            TreeNode poll = queue.poll();
            list.add(poll.val);
            if (poll.left!=null) queue.add(poll.left);
            if (poll.right!=null) queue.add(poll.right);
        }
        int length = list.size();
        int[] result = new int[length];
        for (int i=0;i<length;++i){
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 丑数
     * 只包含质因子 2、3 和 5 的数称作丑数
     * 丑数的递推性质： 丑数只包含因子 2, 3, 5 ，因此有 “丑数 == 某较小丑数 × 某因子” （例如：10=5×2）。
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        //确定第几个丑数
        int numA = 0,numB = 0,numC = 0;
        for (int i=1;i<n;++i){
            //a == 2因子对应的较小丑数；b == 3因子对应的较小丑数；c == 5因子对应的较小丑数
            int a = dp[numA] * 2,b = dp[numB] * 3,c = dp[numC] * 5;
            //找各个因子对应的丑数的最小值
            dp[i] = Math.min(Math.min(a,b),c);
            //对应最小值丑数数量加一
            if (dp[i] == a) ++numA;
            if (dp[i] == b) ++numB;
            if (dp[i] == c) ++numC;
        }
        return dp[n-1];
    }

    /**
     * 股票的最大利润
     * 动态规划：前i日最大利润 = max(前(i-1)日最大利润,第i日价格 - 前i日最低价格)
     *         dp[i] = max(dp[i-1],prices[i]-min(prices[0:i]))
     * 初始状态：dp[0] = 0,即首日利润为0
     * 返回值：dp[len-1]
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices==null || prices.length==0) return 0;
        /*
        int length = prices.length;
        int left = 0,right = 1;
        //股票的最大利润
        int maxProfit = 0;
        //最小的买入值
        int minLeft = prices[0];
        while (right<length) {
            int priceLeft = prices[left];
            if (priceLeft<minLeft) {
                minLeft = priceLeft;
            }
            ++left;
            int profit = prices[right] - minLeft;
            if (profit>maxProfit) maxProfit = profit;
            ++right;
        }
        return maxProfit;
        */
        int length = prices.length;
        int[] dp = new int[length];
        dp[0] = 0;
        int min = prices[0];
        for (int i=1;i<length;++i) {
            int price = prices[i];
            if (price<min) min = price;
            dp[i] = Math.max(dp[i-1],prices[i]-min);
        }
        return dp[length-1];
    }

    class Node2 {
        public int val;
        public Node2 left;
        public Node2 right;

        public Node2() {}

        public Node2(int _val) {
            val = _val;
        }

        public Node2(int _val,Node2 _left,Node2 _right) {
            val = _val;
            left = _left;
            right = _right;
        }

        private List<Node2> list = new ArrayList<>();
        Node2 pre,head;
        /**
         * 二叉搜索树与双向链表
         * @param root
         * @return
         */
        public Node2 treeToDoublyList(Node2 root) {
            if (root==null) return null;
            inOrder(root);
            /*int size = list.size();
            Node2 first1 = list.get(0);
            Node2 first = first1;
            for (int i=1;i<size;++i){
                Node2 node2 = list.get(i);
                first.right = node2;
                node2.left = first;
                first = node2;
            }
            Node2 tail = list.get(size - 1);
            first1.left = tail;
            tail.right = first1;
            return first1;*/
            head.left = pre;
            pre.right = head;
            return head;
        }

        public void inOrder(Node2 root) {
            if (root==null) return;
            inOrder(root.left);
//            list.add(root);
            if (pre!=null) pre.right = root;
            //说明是头节点
            else head = root;
            root.left = pre;
            pre = root;
            inOrder(root.right);
        }
    }



    public static void main(String[] args) {
        Solution solution = new Solution();
        int i = solution.lastRemaining(5, 3);
        System.out.println(i);

        List<Integer> testSt = new ArrayList<Integer>(){{add(1);}};
        Integer[] strings = testSt.toArray(new Integer[10]);

//        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
//        int maxSubArray = solution.maxSubArray(nums);
//        System.out.println(maxSubArray);
//        solution.isStraight(new int[]{0,11,3,13,7});
//        solution.maxSlidingWindow(new int[]{1,-1},1);
//        solution.reverseWords("the sky      is blue");
        solution.singleNumbers(new int[]{1,2,2,5});
        System.out.println(-15>>>1);
        System.out.println(Integer.toBinaryString((-15>>>1)));
        solution.maxProfit(new int[]{4,2,1,7});
    }
}
