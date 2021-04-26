package solution;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/7/4 00:18
 */
public class Solution {

    /**
     * 非递归前序遍历
     */
    public void preOrderN(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode p = root;
        while (p!=null || !q.isEmpty()) {
            while (p!=null) {
                System.out.println(p.val);
                q.push(p);
                p = p.left;
            }
            if (!q.isEmpty()) {
                p = q.pop();
                p = p.right;
            }
        }
    }

    /**
     * 非递归中序遍历
     */
    public void inOrderN(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode p = root;
        while (p!=null || !q.isEmpty()) {
            while (p!=null) {
                q.push(p);
                p = p.left;
            }
            if (!q.isEmpty()) {
                p = q.pop();
                System.out.println(p.val);
                p = p.right;
            }
        }
    }

    /**
     * 非递归后序遍历
     */
    public void postOrderN(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode p = root;
        TreeNode last = null;
        while (p != null || !q.isEmpty()) {
            while (p != null) {
                q.push(p);
                p = p.left;
            }
            p = q.peek();
            if (p.right == null || p.right == last) {
                //访问
                System.out.println(p.val);
                //记录上一个访问的节点，用于判断"访问根节点之前，右子树是否已经访问过"
                q.pop();
                last = p;
                //表示不需要转向，继续弹栈
                p = null;
            } else {
                p = p.right;
            }
        }
    }

    public static class ListNode {
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
    private ListNode reverseLinked(ListNode head) {
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
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            low = low.next;
        }
        return low;
    }

    /**
     * 回文链表
     * 快慢指针
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
     * 239.滑动窗口的最大值
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        /*if (nums==null || nums.length==0) return new int[0];
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
        return ints;*/

        /*
        //自己的思路 个别数据超时
        if (nums==null || nums.length==0) return new int[0];
        int length = nums.length;
        List<Integer> result = new ArrayList<>();
        int idx = 0;
        while (idx <= length - k) {
            int maxValue = Integer.MIN_VALUE;
            for (int i=idx;i<idx+k;++i) {
                maxValue = Math.max(maxValue,nums[i]);
            }
            ++idx;
            result.add(maxValue);
        }
        int size = result.size();
        int[] ints = new int[size];
        for (int i=0;i<size;++i) {
            ints[i] = result.get(i);
        }
        return ints;*/
        /**
         * 思路： 遍历数组 L R 为滑窗左右边界 只增不减
         *         双向队列保存当前窗口中最大的值的数组下标 双向队列中的数从大到小排序，
         *         新进来的数如果大于等于队列中的数 则将这些数弹出 再添加
         *         当R-L+1=k 时 滑窗大小确定 每次R前进一步L也前进一步 保证此时滑窗中最大值的
         *         数组下标在[L，R]中，并将当前最大值记录
         *   举例： nums[1，3，-1，-3，5，3，6，7] k=3
         *      1：L=0，R=0，队列【0】 R-L+1 < k
         *             队列代表值【1】
         *      2: L=0,R=1, 队列【1】 R-L+1 < k
         *             队列代表值【3】
         *      解释：当前数为3 队列中的数为【1】 要保证队列中的数从大到小 弹出1 加入3
         *           但队列中保存的是值对应的数组下标 所以队列为【1】 窗口长度为2 不添加记录
         *      3: L=0,R=2, 队列【1，2】 R-L+1 = k ,result={3}
         *             队列代表值【3，-1】
         *      解释：当前数为-1 队列中的数为【3】 比队列尾值小 直接加入 队列为【3，-1】
         *           窗口长度为3 添加记录记录为队首元素对应的值 result[0]=3
         *      4: L=1,R=3, 队列【1，2，3】 R-L+1 = k ,result={3，3}
         *             队列代表值【3，-1,-3】
         *      解释：当前数为-3 队列中的数为【3，-1】 比队列尾值小 直接加入 队列为【3，-1，-3】
         *           窗口长度为4 要保证窗口大小为3 L+1=1 此时队首元素下标为1 没有失效
         *           添加记录记录为队首元素对应的值 result[1]=3
         *      5: L=2,R=4, 队列【4】 R-L+1 = k ,result={3，3，5}
         *             队列代表值【5】
         *      解释：当前数为5 队列中的数为【3，-1，-3】 保证从大到小 依次弹出添加 队列为【5】
         *           窗口长度为4 要保证窗口大小为3 L+1=2 此时队首元素下标为4 没有失效
         *           添加记录记录为队首元素对应的值 result[2]=5
         *     依次类推 如果队首元素小于L说明此时值失效 需要弹出
         */
        if(nums==null||nums.length<2) return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数按从大到小排序
        LinkedList<Integer> list = new LinkedList();
        int length = nums.length;
        // 结果数组
        int[] result = new int[length-k+1];
        for(int i=0;i<length;i++){
            // 保证从大到小 如果前面数小 弹出
            while(!list.isEmpty() && nums[list.peekLast()]<=nums[i]) list.pollLast();
            // 添加当前值对应的数组下标
            list.addLast(i);
            // 初始化窗口 等到窗口长度为k时 下次移动在删除过期数值
            if(list.peek()<=i-k) list.poll();
            // 窗口长度为k时 再保存当前窗口中最大值
            if(i-k+1>=0) result[i-k+1] = nums[list.peek()];
        }
        return result;
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
     * 面试题 17.01. 不用加号的加法
     * 位运算模拟加减法
     * ^ 亦或 ----相当于 无进位的求和， 想象10进制下的模拟情况：（如:19+1=20；00010011 ^ 00000001 = 00010010(18)无进位求和就是10，而非20；因为它不管进位情况）
     * & 与  ----相当于求每位的进位数， 先看定义：1&1=1；1&0=0；0&0=0；即都为1的时候才为1，正好可以模拟进位数的情况,还是想象10进制下模拟情况：
     *      （9+1=10，1001 & 0001 = 0001 如果是用&的思路来处理，则9+1得到的进位数为1，而不是10，所以要用<<1向左再移动一位，这样就变为10了）；
     * 18 + 2 = 20 -> 00010010 & 00000010 = 00000010(2)  00010010 ^ 00000010 = 00010000(16)
     * 19 + 1 = 20 -> 00010011 & 00000001 = 00000001(1)  00010011 ^ 00000001 = 00010010(18)
     * 这样公式就是：（a^b) + ((a&b)<<1) 即：每次无进位求和 + 每次得到的进位数--------我们需要不断重复这个过程，直到进位数为0为止；
     *
     * << 左移，不分正负数
     * >> 右移，分正负数
     * >>> 无符号右移
     *
     * 18 + 2 = 00010010 + 00000010 = 00010100
     *
     *
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
        Node left;
        Node right;
        public List<Node> children;

        public Node() {
        }

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }

        public Node(int _val, List<Node> children) {
            this.val = _val;
            this.children = children;
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

    /**
     * 股票的最大价值II
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        /**
         * 贪心算法，只要第二天股价比第一天高就卖出，且只加正值
         */
        if(prices==null) return 0;
        int len = prices.length;
        if(len < 2) return 0;

        /*//贪心法
        int result = 0;
        for(int i=1;i<len;++i) {
            int tmp = prices[i] - prices[i-1];
            if(tmp > 0) result += tmp;
        }
        return result;*/

        /**
         * 动态规划法 ---- https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/tan-xin-suan-fa-by-liweiwei1419-2/
         * 第一步：定义状态
         *      状态 dp[i][j] 定义如下：
         *          dp[i][j] 表示到下标为 i 的这一天，持股状态为 j 时，我们手上拥有的最大现金数
         *          注意：限定持股状态为 j 是为了方便推导状态转移方程，这样的做法满足 无后效性
         *          其中：
         *              第一维 i 表示下标为 i 的那一天（具有前缀性质，即考虑了之前的天数的交易）
         *              第二维 j 表示下标为 i 的那一天是持有股票，还是持有现金。这里 0 表示持有现金，1表示持有股票
         */


        // cash：持有现金
        // hold：持有股票
        // 状态数组
        // 状态转移：cash → hold → cash → hold → cash → hold → cash
        int[] cash = new int[len];
        int[] hold = new int[len];

        cash[0] = 0;
        hold[0] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 这两行调换顺序也是可以的
            cash[i] = Math.max(cash[i - 1], hold[i - 1] + prices[i]);
            hold[i] = Math.max(hold[i - 1], cash[i - 1] - prices[i]);
        }
        return cash[len - 1];
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

    /**
     * 栈的压入、弹出序列
     * 判断合不合法，用个栈试一试:
     *      把压栈的元素按顺序压入，当栈顶元素和出栈的第一个元素相同，则将该元素弹出，出栈列表指针后移并继续判断。
     *      最后判断栈是否已空即可。
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed==null || popped==null || pushed.length==0 || popped.length==0) return true;
        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0;
        int length = popped.length;
        for (int i:pushed){
            stack.push(i);
            while (num<length && !stack.isEmpty() && (stack.peek() == popped[num])) {
                stack.pop();
                ++num;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 从上到下打印二叉树 二
     * 按照之字型打印
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(TreeNode root) {
        Deque<TreeNode> deque = new ArrayDeque<>();
        List<List<Integer>> result = new ArrayList<>();
        if (root != null) deque.add(root);
        while (!deque.isEmpty()) {
            LinkedList<Integer> tmp = new LinkedList<>();
            //直接遍历当前队列里的所有元素，此时的值即为某层的元素个数
            for (int l=deque.size();l>0;l--) {
                TreeNode poll = deque.poll();
                //结果集的外层list的size即为层数，根据层数判断输出方式，偶数层
                if (result.size()%2==0) tmp.addLast(poll.val);
                //奇数层
                else tmp.addFirst(poll.val);
                if (poll.left!=null) deque.add(poll.left);
                if (poll.right!=null) deque.add(poll.right);
            }
            result.add(tmp);
        }
        return result;
    }

    /**
     * 二叉树中，和为某一值的路径
     * DFS，回溯
     * @param root
     * @param sum
     * @return
     */
    private List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        dfs(root,sum,new LinkedList<>());
        return result;
    }

    public void dfs(TreeNode root, int sum, LinkedList<Integer> linkedList){
        if (root==null) return;
        //把经过的数据放到列表里
        linkedList.add(root.val);
        int target = sum - root.val;
        //当目标值为0，且root为叶子节点，则找到一个符合条件的路径
        if (target==0 && root.left==null && root.right==null) result.add(new LinkedList<>(linkedList));
        dfs(root.left,target,linkedList);
        dfs(root.right,target,linkedList);
        //回溯时删除当前节点数据
        linkedList.removeLast();
    }

    /**
     * 把数组排成最小的数
     * @param nums
     * @return
     */
    public String minNumber(int[] nums) {
        if (nums==null || nums.length==0) return "";
        int length = nums.length;
        String[] numsStr = new String[length];
        for (int i=0;i<length;++i) {
            numsStr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(numsStr,(x,y)->(x+y).compareTo(y+x));
        StringBuilder sb = new StringBuilder();
        for (String s:numsStr) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 字符串排列
     * 回溯
     * @param s
     * @return
     */
    List<String> permutation = new LinkedList<>();
    public String[] permutation(String s) {
        if (s==null || s.trim().length()==0) return new String[0];
        char[] chars = s.toCharArray();
        int length = chars.length;
        permutation(chars,0,length);
        return permutation.toArray(new String[permutation.size()]);
    }
    public void permutation(char[] chars, int index, int len) {
        if (index == len-1) {
            //完成回溯
            permutation.add(String.valueOf(chars));
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i=index;i<len;++i) {
            if (set.contains(chars[i])) continue;
            set.add(chars[i]);
            swap(chars,i,index);
            permutation(chars,index+1,len);
            //回溯
            swap(chars,i,index);
        }
    }
    public void swap(char[] chars,int i,int j) {
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }

    /**
     * 把数字翻译成字符串
     * 回溯和dp皆可，类似青蛙跳台，一次一个或两个
     * @param num
     * @return
     */
    int count = 0;
    public int translateNum(int num) {
        /*
        //递归
        if (num<10) return 1;
        int twoValue = num % 100;
        //只能一个一个来
        if (twoValue>25 || twoValue<10) return translateNum(num/10);
        //可以有一个或两个组合的情况
        else return translateNum(num/10) + translateNum(num/100);
        */
        /*
        //非递归
        int result = 1,single = 1;
        while (num!=0) {
            int twoValue = num % 100;
            //保存当前可能的结果数
            int tmp = result;
            //既可以两个，也可以一个，两种可能
            if (twoValue<=25 && twoValue>=10) result += single;
            //只有一种选择时可能的结果数
            single = tmp;
            num = num/10;
        }
        return result;
        */

        //回溯
        String s = String.valueOf(num);
        translateNum(s,0,s.length());
        return count;
    }

    /**
     * 回溯法
     * @param s
     * @param index
     * @param len
     */
    public void translateNum(String s,int index,int len) {
        if (index == len) {
            ++count;
            return;
        }
        if (index+2<=len) {
            Integer twoValue = Integer.valueOf(s.substring(index, index + 2));
            if (twoValue<=25 && twoValue>=10) {
                translateNum(s,index+2,len);
            }
        }
        translateNum(s,index+1,len);
    }

    /**
     * 二叉搜索树的后序遍历序列
     * 二叉搜索树，左子树小于根节点，右子树大于根节点
     * @param postorder
     * @return
     */
    public boolean verifyPostorder(int[] postorder) {
        int length = postorder.length;
        return verifyPostorder(postorder,0,length-1);
    }
    public boolean verifyPostorder(int[] postorder, int i, int j) {
        if (i>=j) return true;
        int pivot = i;
        //左子树小于根节点
        while(postorder[pivot] < postorder[j]) ++pivot;
        //新的根节点
        int m = pivot;
        //右子树大于根节点
        while(postorder[pivot] > postorder[j]) ++pivot;
        //最后计算出来的根节点下标pivot是否等于后序遍历的根节点j，并且左右子树是否也满足这个条件
        return pivot==j && verifyPostorder(postorder,i,m-1) && verifyPostorder(postorder,m,j-1);
    }

    /**
     * 机器人的运动范围
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        if (m==0 || n==0) return 0;
        if(k==0) return 1;
        boolean[][] isVistied = new boolean[m][n];
        // return dfs4MovingCount(0,0,isVistied,m,n,k);
        return bfs4MovingCount(isVistied,m,n,k);
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
     * 最长不含重复字符的子字符串
     * 双指针、滑动窗口、哈希表
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s==null) return 0;
        int len = s.length();
        int result = 0;
        //双指针（滑动窗口）法
        Set<Character> set = new HashSet<>();
        for(int l=0,r=0;r<len;++r) {
            char c = s.charAt(r);
            while(set.contains(c)) {
                //缩小滑动窗口左边边界，因为只要求最大的个数，不要求不重复的字符串是啥，所以只要有重复，窗口里的字符数量扣除一个即可
                set.remove(s.charAt(l));
                ++l;
            }
            set.add(c);
            result = Math.max(result,r-l+1);
        }
        return result;
    }

    /**
     * 字符串转整数
     * @param str
     * @return
     */
    public int strToInt(String str) {
        str = str.trim();
        char[] tchar = str.toCharArray();
        int len = tchar.length;
        long result = 0;
        boolean positive = true;

        switch (tchar[0]) {
            case '+':
                positive = true;
                break;
            case '-':
                positive = false;
                result = -result;
                break;
            default:
                result = tchar[0] - '0';
        }
        for(int i=1;i<len;++i) {
            int c = tchar[i] - '0';
            if(0 <= c && 9 >= c) {
                result = result*10 + c;
                if (positive && result >= Integer.MIN_VALUE) return Integer.MAX_VALUE;
                else if (-result <= Integer.MIN_VALUE) return Integer.MIN_VALUE;
            } else {
                break;
            }
        }
        if (positive) return (int) result;
        else return (int) -result;
    }

    /**
     * 1～n中整数n的1出现的次数
     * 将 n 分为  high、cur、low 三种位，digit = 10^i 位因子
     * cur = 0时，result 由高位决定，即 result = high * digit（此时digit为10）
     * cur = 1时，result 由高位high和低位low决定，即 result = high * digit + low + 1
     * cur > 1时，result 由高位high决定，即 result = (high + 1) * digit
     * @param n
     * @return
     */
    public int countDigitOne(int n) {
        int count = 0;
        int high = n/10,cur = n%10,digit = 1,low = 0;
        while (high!=0 || cur!=0) {
            if (cur==0) count = count + high * digit;
            else if (cur==1) count = count + high * digit + low + 1;
            else count = count + (high+1) * digit;
            low = low + cur * digit;
            digit = digit * 10;
            cur = high%10;
            high = high/10;
        }
        return count;
    }

    //下面我们都用 1234 和 2345 来举例
    private int f(int n){
        // 上一级递归 n = 20、10之类的整十整百之类的情况；以及n=0的情况
        if(n== 0) return 0;
        // n < 10 即为个位，这样子只有一个1
        if(n < 10) return 1;

        String s = String.valueOf(n);
        //长度：按例子来说是4位
        int length = s.length();

        //这个base是解题速度100%的关键，本例中的是999中1的个数：300
        // 99的话就是20 ; 9的话就是1 ；9999就是4000 这里大家应该发现规律了吧。
        int base = (length-1)*(int)Math.pow(10,length-2);

        //high就是最高位的数字
        int high = s.charAt(0) - '0';
        //cur就是当前所数量级，即1000
        int cur = (int)Math.pow(10,length -1);
        if(high == 1){
            //最高位为1，1+n-cur就是1000~1234中由千位数提供的1的个数，剩下的f函数就是求1000~1234中由234产生的1的个数
            return base + 1 + n - cur + f(n - high * cur);
        }else{
            //这个自己思考
            return base * high + cur + f(n- high * cur);
        }
    }

    /**
     * 组合总和
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int len = candidates.length;
        List<Integer> tmp = new ArrayList<>();
        combinationSum(candidates,target,0,len,tmp);
        return result;
    }
    public void combinationSum(int[] candidates, int target, int index, int len, List<Integer> tmp) {
        if (index>=len) {
            return;
        }
        if (0==target) {
            result.add(new ArrayList<Integer>(tmp));
            return;
        }
        combinationSum(candidates,target,index+1,len,tmp);
        if(target - candidates[index] >= 0) {
            tmp.add(candidates[index]);
            combinationSum(candidates,target-candidates[index],index,len,tmp);
            tmp.remove(tmp.size()-1);
        }
    }

    /**
     * 组合总和二
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates,int target) {
        int length = candidates.length;
        if (length==0) return result;
        Arrays.sort(candidates);
        Deque<Integer> path = new ArrayDeque<>(length);
        combinationSum2(candidates,target,0,path,length);
        return result;
    }
    public void combinationSum2(int[] candidates, int target, int index, Deque<Integer> path,int len) {
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i=index;i<len;++i) {
            //大剪枝
            if (candidates[i]>target) break;
            //小剪枝
            if (i>index && candidates[i] == candidates[i-1]) continue;
            path.addLast(candidates[i]);
            combinationSum2(candidates,target-candidates[i],i+1,path,len);
            path.removeLast();
        }
    }

    /**
     * 二叉树的层平均值
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root==null) return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            double v = 0d;
            int size = queue.size();
            for (int i=0;i<size;i++) {
                TreeNode poll = queue.poll();
                if (poll.left!=null) queue.add(poll.left);
                if (poll.right!=null) queue.add(poll.right);
                v = v + poll.val;
            }
            result.add(v/size);
        }
        return result;
    }

    /**
     * 单词搜索
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        if(board==null || word==null) return false;
        int rows = board.length;
        int cols = board[0].length;
        for(int i=0;i<rows;++i) {
            for(int j=0;j<cols;++j) {
                boolean exi = exist(board,i,j,word,0,new boolean[rows][cols],word.length(),cols,rows);
                if(exi) return true;
            }
        }
        return false;
    }

    public boolean exist(char[][] board, int x, int y, String word, int index, boolean[][] visited,int len,int cols, int rows) {
        if(visited[x][y] || board[x][y] != word.charAt(index)) return false;
        if(index == len-1) return true;

        visited[x][y] = true;
        if(x>0) {
            boolean exits = exist(board,x-1,y,word,index+1,visited,len,cols,rows);
            if(exits) return true;
        }
        if(x<rows-1){
            boolean exits = exist(board,x+1,y,word,index+1,visited,len,cols,rows);
            if(exits) return true;
        }
        if(y>0){
            boolean exits = exist(board,x,y-1,word,index+1,visited,len,cols,rows);
            if(exits) return true;
        }
        if(y<cols-1) {
            boolean exits = exist(board,x,y+1,word,index+1,visited,len,cols,rows);
            if(exits) return true;
        }
        //回溯结束需要设置回去
        visited[x][y] = false;
        return false;
    }

    /**
     * 迭代法中序遍历
     * https://leetcode-cn.com/problems/binary-tree-preorder-traversal/solution/leetcodesuan-fa-xiu-lian-dong-hua-yan-shi-xbian-2/
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode leaf = root;
        while(!q.isEmpty() || leaf!=null) {
            while(leaf!=null) {
                q.addLast(leaf);
                leaf = leaf.left;
            }
            leaf = q.pollLast();
            result.add(leaf.val);
            leaf = leaf.right;
        }
        return result;
    }

    /**
     * 迭代法后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode prev = null;
        while (!q.isEmpty() || root != null) {
            while (root!=null) {
                q.addLast(root);
                root = root.left;
            }
            root = q.pollLast();
            if (root.right == null || root.right == prev) {
                result.add(root.val);
                prev = root;
                root = null;
            }else {
                q.addLast(root);
                root = root.right;
            }
        }
        return result;
    }

    /**
     * 二叉搜索树的插入
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if(root==null) return new TreeNode(val);
        TreeNode ppos = root;
        while(ppos != null) {
            if(ppos.val < val) {
                if(ppos.right==null) {
                    ppos.right = new TreeNode(val);
                    return root;
                }else{
                    ppos = ppos.right;
                }
            }else{
                if(ppos.left==null) {
                    ppos.left = new TreeNode(val);
                    return root;
                }else{
                    ppos = ppos.left;
                }
            }
        }
        return root;
    }

    /**
     * 解数独
     * @param board
     */
    public void solveSudoku(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] colUse = new boolean[9][10];
        boolean[][] rowUse = new boolean[9][10];
        boolean[][][] numUsed = new boolean[3][3][10];
        for (int row=0;row<rows;++row) {
            for (int col=0;col<cols;++col) {
                int num = board[row][col] - '0';
                if (num >=0 && num <= 9) {
                    rowUse[row][num] = true;
                    colUse[col][num] = true;
                    numUsed[row/3][col/3][num] = true;
                }
            }
        }
        solveSudoku(board,rowUse,colUse,numUsed,0,0);
    }
    public boolean solveSudoku(char[][] board, boolean[][] rowUsed, boolean[][] colUsed, boolean[][][] numUsed, int row, int col){
        //终止条件,先row不懂，遍历col，col遍历完，row加一，col从头开始
        if (col == board[0].length) {
            col = 0;
            //先遍历列
            ++row;
            if (row == board.length) return true;
        }
        if (board[row][col] == '.') {
            for (int num=1;num<=9;++num) {
                int i = row / 3;
                int j = col / 3;
                //当前数字是否可用
                boolean numCanUse = !(rowUsed[row][num] || colUsed[col][num] || numUsed[i][j][num]);
                if (numCanUse) {
                    //如果该数字可用
                    rowUsed[row][num] = true;
                    colUsed[col][num] = true;
                    numUsed[i][j][num] = true;
                    board[row][col] = (char) (num + '0');
                    if (solveSudoku(board,rowUsed,colUsed,numUsed,row,col+1)) {
                        return true;
                    }
                    //否则回溯
                    rowUsed[row][num] = false;
                    colUsed[col][num] = false;
                    numUsed[i][j][num] = false;
                    board[row][col] = '.';
                }
            }
        } else return solveSudoku(board,rowUsed,colUsed,numUsed,row,col+1);
        return false;
    }

    /**
     * 翻转二叉树
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {
        if (root != null) {
            TreeNode tmp = root.left;
            root.left = root.right;
            root.right = tmp;
            invertTree(root.left);
            invertTree(root.right);
        }
        return root;
    }

    /**
     * 全排列 II
     * 回溯
     * @param args
     */
    private boolean[] flag;
    public List<List<Integer>> permuteUnique(int[] nums) {
        int len = nums.length;
        flag = new boolean[len];
        //排序
        Arrays.sort(nums);
        permuteUnique(nums,0,len,new ArrayList<Integer>());
        return result;
    }
    public void permuteUnique(int[] nums, int index,int len, List<Integer> tmp) {
        if(index==len) {
            result.add(new ArrayList<Integer>(tmp));
            return;
        } else {
            for(int i = 0;i<len;++i) {
                //基于排好序的情况下
                if(flag[i] || (i>0 && nums[i]==nums[i-1] && !flag[i-1])) continue;
                flag[i] = true;
                tmp.add(nums[i]);
                permuteUnique(nums,index+1,len,tmp);
                flag[i] = false;
                tmp.remove(index);
            }
        }
    }

    /**
     * 冗余连接 II
     * 并查集
     * @param edges
     * @return
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {

        return null;
    }

    /**
     * 子集
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        int len = nums.length;
        subsets(nums,0,new ArrayList<Integer>(),len);
        return result;
    }
    public void subsets(int[] nums, int index, List<Integer> tmp, int len) {
        result.add(new ArrayList<>(tmp));
        for(int i=index;i<len;++i) {
            tmp.add(nums[i]);
            subsets(nums,i+1,tmp,len);
            tmp.remove(tmp.size()-1);
        }
    }

    /**
     * 合并二叉树
     * @param t1
     * @param t2
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1!=null && t2==null) return t1;
        if (t1==null && t2!=null) return t2;
        if (t1==null && t2==null) return null;

        t1.left = mergeTrees(t1.left,t2.left);
        t1.right = mergeTrees(t1.right,t2.right);
        t1.val += t2.val;
        return t1;
    }

    /**
     * 二叉搜索树中的众数
     */
    private List<Integer> l = new ArrayList<>();
    private int curTime, maxTime, preVal;
    public int[] findMode(TreeNode root) {
        if (root==null) return null;
        bfs4FindMode(root);
        int size = l.size();
        int[] result = new int[size];
        for (int i=0;i<size;++i) {
            result[i] = l.get(i);
        }
        return result;
    }
    private void bfs4FindMode(TreeNode root) {
        if (root!=null) {
            bfs4FindMode(root.left);
            if (root.val == preVal) {
                ++curTime;
            } else {
                preVal = root.val;
                curTime = 1;
            }
            if (curTime == maxTime) {
                l.add(root.val);
            } else if (curTime > maxTime) {
                l.clear();
                l.add(root.val);
                maxTime = curTime;
            }
            bfs4FindMode(root.right);
        }
    }

    /**
     * 路径总和 II
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSumII(TreeNode root, int sum) {
        pathSumII(root,sum,new ArrayList<Integer>());
        return result;
    }
    public void pathSumII(TreeNode root, int sum, List<Integer> tmp) {
        if (root==null) return;
        if(root.left==null && root.right==null && sum!=root.val) return;
        tmp.add(root.val);
        if (sum==root.val && root.left==null && root.right==null) {
            result.add(new ArrayList<>(tmp));
            //这里不移除，会少移除一个
            tmp.remove(tmp.size()-1);
            return;
        }
        pathSumII(root.left,sum-root.val,tmp);
        pathSumII(root.right,sum-root.val,tmp);
        tmp.remove(tmp.size()-1);
    }

    /**
     * 填充每个节点的下一个右侧节点指针 II
     * BFS
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if(root==null) return root;
        ArrayDeque<Node> q = new ArrayDeque<>();
        q.addLast(root);
        while(!q.isEmpty()) {
            int size = q.size();
            Node tmp = q.pollFirst();
            if(tmp.left!=null)q.addLast(tmp.left);
            if(tmp.right!=null)q.addLast(tmp.right);
            for(int i=1;i<size;++i) {
                Node one = q.pollFirst();
                tmp.next = one;
                tmp = one;
                if(one.left!=null) q.addLast(one.left);
                if(one.right!=null) q.addLast(one.right);
            }
            tmp.next = null;
        }
        return root;
    }

    /**
     * 二叉搜索树的最近公共祖先
     * 利用二叉搜索树的特点
     * @param
     */
    TreeNode lcaResult;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        lca(root,p,q);
        return lcaResult;
    }
    public void lca(TreeNode root, TreeNode p, TreeNode q) {
        //利用二叉搜索树的特点
        if ((root.val - p.val) * (root.val - q.val) <= 0) {
            lcaResult = root;
        }else if (root.val < p.val && root.val < q.val) lca(root.right,p,q);
        else lca(root.left,p,q);
    }

    /**
     * 二叉搜索树的最小绝对值差
     * 暴力法
     * @param root
     * @return
     */
    List<Integer> list = new ArrayList<>();
    public int getMinimumDifference(TreeNode root) {
        if (root==null) return Integer.MAX_VALUE;
        getMinimumDifference_dfs(root);
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        int i=list.get(0),res=Integer.MAX_VALUE;
        int size = list.size();
        for (int k=1;k<size;++k) {
            int tmp = list.get(k) - i;
            if (tmp < res) res = tmp;
            i = list.get(k);
        }
        return res;
    }
    public void getMinimumDifference_dfs(TreeNode root) {
        if (root==null) return;
        list.add(root.val);
        getMinimumDifference_dfs(root.left);
        getMinimumDifference_dfs(root.right);
    }

    /**
     * 二叉搜索树的最小绝对值差
     * 利用二叉搜索树特性
     * @param root
     * @return
     */
    TreeNode preNode = null;
    Integer resultValue = Integer.MAX_VALUE;
    public int getMinimumDifference2(TreeNode root) {
        if (root==null) return Integer.MAX_VALUE;
        getMinimumDifference2_dfs(root);
        return resultValue;
    }
    public void getMinimumDifference2_dfs(TreeNode root) {
        if (root==null) return;
        getMinimumDifference_dfs(root.left);
        if (preNode != null) {
            resultValue = Math.min(Math.abs(root.val - preNode.val),resultValue);
        }
        preNode = root;
        getMinimumDifference_dfs(root.right);
    }

    /**
     * 24. 两两交换链表中的节点
     * 使用递归来解决该题，主要就是递归的三部曲：
     *
     * 找终止条件：本题终止条件很明显，当递归到链表为空或者链表只剩一个元素的时候，没得交换了，自然就终止了。
     * 找返回值：返回给上一层递归的值应该是已经交换完成后的子链表。
     * 单次的过程：因为递归是重复做一样的事情，所以从宏观上考虑，只用考虑某一步是怎么完成的。
     * 我们假设待交换的俩节点分别为head和next，next的应该接受上一级返回的子链表(参考第2步)。
     * 就相当于是一个含三个节点的链表交换前两个节点，就很简单了，想不明白的画画图就ok。
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        if(head==null || head.next==null) return head;
        ListNode nextNode = head.next;
        head.next = swapPairs(nextNode.next);
        nextNode.next = head;
        return nextNode;
    }

    /**
     * 1002. 查找常用字符
     * @param A
     * @return
     */
    public List<String> commonChars(String[] A) {
        int[] minFeq = new int[26];
        Arrays.fill(minFeq,Integer.MAX_VALUE);
        for (int i=0;i<A.length;++i) {
            for (String word : A) {
                int[] feq = new int[26];
                int length = word.length();
                for (int j=0;j<length;++j) {
                    ++feq[word.charAt(j) - 'a'];
                }
                for (int j=0;j<26;++j) {
                    minFeq[j] = Math.min(minFeq[j],feq[j]);
                }
            }
        }
        List<String> result = new ArrayList<>();
        for (int i=0;i<26;++i) {
            for (int j=0;j<minFeq[i];++j) {
                result.add(String.valueOf((char)('a' + i)));
            }
        }
        return result;
    }

    /**
     * 116. 填充每个节点的下一个右侧节点指针
     * @param root
     * @return
     */
    public Node connectOne(Node root) {
        Queue<Node> queue = new ArrayDeque<>();
        if(root!=null)queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node preNode = null;
            for (int i=0;i<size;++i) {
                Node poll = queue.poll();
                if(i==0) {
                    preNode = poll;
                } else {
                    preNode.next = poll;

                    preNode = preNode.next;
                }
                if (poll.left!=null) queue.add(poll.left);
                if (poll.right!=null) queue.add(poll.right);

            }
        }
        return root;
    }

    /**
     * 经典题解，核心思路：
     * 1. 使用常规深度优先一层层搜索
     * 2. 使用三个整形分别标记每一层哪些格子可以放置皇后，这三个整形分别代表
     *      列、左斜下、右斜下（col，ld，rd），二进制位为 1 代表不能放置，0代表可以放置
     * 3. 两个核心运算：
     *      1. x & -x 代表除最后一位 1 保留，其它位全部为 0
     *      2. x & (x - 1) 代表将最后一位 1 变成 0
     * @param n
     * @return
     */
    private int result4totalNQueens;
    public int totalNQueens(int n) {

        totalNQueensDfs(n,0,0,0,0);
        return result4totalNQueens;
    }

    public void totalNQueensDfs(int n, int row, int col, int ld, int rd) {
        if (row >= n) { ++result4totalNQueens; return; }

        // 将所有能放置 Q 的位置由 0 变成 1，以便进行后续的位遍历
        int bits = ~(col | ld | rd) & ((1 << n) - 1);
        while (bits > 0) {
            int pick = bits & -bits; // 注: x & -x
            totalNQueensDfs(n, row + 1, col | pick, (ld | pick) << 1, (rd | pick) >> 1);
            bits &= bits - 1; // 注: x & (x - 1)
        }
    }

    /**
     * 19. 删除链表的倒数第N个节点
     * 双指针法
     * 还可以使用 递归法
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode firstNode = head;
        ListNode secondNode = head;
        int count = 0;
        if(head==null) return head;
        while (secondNode.next!=null) {
            if (count >= n) {
                secondNode = secondNode.next;
                firstNode = firstNode.next;
            } else {
                secondNode = secondNode.next;
            }
            ++count;
        }
        //while退出时表示 secondNode 位于最后一个节点，firstNode位于前n个
        if(count+1==n) return head.next;
        firstNode.next = firstNode.next.next;
        return head;
    }

    /**
     * 844. 比较含退格的字符串
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        int lengthS = S.length();
        int lengthT = T.length();
        StringBuffer sbS = new StringBuffer();
        StringBuffer sbT = new StringBuffer();
        for (int i=0;i<lengthS;++i) {
            if (S.charAt(i) == '#') {
                if (sbS.length()>0) sbS.deleteCharAt(sbS.length()-1);
            } else {
                sbS.append(S.charAt(i));
            }
        }
        for (int i=0;i<lengthT;++i) {
            if (T.charAt(i) == '#') {
                if (sbT.length()>0) sbT.deleteCharAt(sbT.length()-1);
            } else {
                sbT.append(T.charAt(i));
            }
        }
        return sbS.toString().equals(sbT.toString());
    }

    public List<Integer> preorderTraversal(TreeNode root) {

        Deque<TreeNode> deque = new ArrayDeque<>();
        List<Integer> result = new ArrayList<>();
        if (root!=null) deque.add(root);
        while (!deque.isEmpty()) {
            TreeNode treeNode = deque.pollLast();
            result.add(treeNode.val);
            if (treeNode.right!=null) deque.addLast(treeNode.right);
            if (treeNode.left!=null) deque.addLast(treeNode.left);
        }
        return result;
    }


    /**
     * 1207. 独一无二的出现次数
     * @param arr
     * @return
     */
    public boolean uniqueOccurrences(int[] arr) {
        if(arr==null) return true;
        Map<Integer,Integer> map = new HashMap<>();
        int len = arr.length;
        for(int i=0;i<len;++i) {
            int num = map.getOrDefault(arr[i],0);
            map.put(arr[i],num+1);
        }

        return map.size() == new HashSet<Integer>(map.values()).size();
    }

    /**
     * 129. 求根到叶子节点数字之和
     */
    public int sumNumbers;
    public int sumNumbers(TreeNode root) {

        if (root==null) return 0;
        dfs4sumNumbers(root,0);
        return sumNumbers;
    }

    public void dfs4sumNumbers(TreeNode root,int value) {
        if (root.left==null && root.right==null) {
            value = value*10 + root.val;
            sumNumbers += value;
            return;
        }
        value = value*10+root.val;
        if (root.left!=null) dfs4sumNumbers(root.left,value);
        if (root.right!=null) dfs4sumNumbers(root.right,value);
    }

    /**
     * 岛屿的总周长
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        if (grid==null || grid.length==0) return 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int result = 0;
        for (int i=0;i<rows;++i) {
            for (int j=0;j<cols;++j) {
                if (grid[i][j]==1) {
                    //上
                    if (i-1<0 || grid[i-1][j]==0) ++result;
                    //左
                    if (j-1<0 || grid[i][j-1]==0) ++result;
                    //下
                    if (i+1>rows || grid[i+1][j]==0) ++result;
                    //右
                    if (j+1>cols || grid[i][j+1]==0) ++result;
                }
            }
        }
        return result;
    }

    public int[] intersection(int[] nums1, int[] nums2) {

        int len1 = nums1.length;
        int len2 = nums2.length;
        if (len1==0 || len2==0) return new int[0];
        Set<Integer> set = new HashSet<>();

        for (int i=0;i<len1;++i) {
            set.add(nums1[i]);
        }
        Set<Integer> s = new HashSet<>();
        for (int j=0;j<len2;++j) {
            if (set.contains(nums2[j])) s.add(nums2[j]);
        }

        int[] result = new int[s.size()];
        int num = 0;
        for (Integer i:s) {
            result[num++] = i;
        }
        return result;
    }

    /**
     * 有效的山脉数组
     * @param A
     * @return
     */
    public boolean validMountainArray(int[] A) {
        if (A == null || A.length < 3) return false;
        int length = A.length;
        int left = 0, right = length - 1;
        while (left < length - 2 && A[left] < A[left+1]) ++left;
        while (right > 1 && A[right] < A[right-1]) --right;
        return left==right;
    }

    /**
     * 插入区间
     * @param intervals
     * @param newInterval
     * @return
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {

        int[][] res = new int[intervals.length+1][2];
        int idx = 0;
        int i = 0;
        while (i<intervals.length && intervals[i][1] < newInterval[0]) {
            res[idx++] = intervals[i++];
        }

        while (i<intervals.length && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[i][0],newInterval[0]);
            newInterval[1] = Math.max(intervals[i][1],newInterval[1]);
            ++i;
        }
        res[idx++] = newInterval;
        while (i < intervals.length) {
            res[idx++] = intervals[i++];
        }
        return Arrays.copyOf(res,idx);
    }

    /**
     * 单词接龙
     * 需要掌握的知识递进：
     * 1.BFS。
     *  2.双端BFS。
     * 3.临近点查找方式：
     * 首先将所有的字符存到结构为HashSet的dic字典里去，然后将字符串的每一位挨个从a变到z,
     * 在变化的时候实时去字典里查，因为是hashset，所以复杂度是O(1)，非常快。
     * 如果查到了，则就是找到了临近点。
     */
    //递归
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (wordList == null || wordList.size() == 0) return 0;
        //hashset的好处：去重也完成了
        //开始端
        HashSet<String> start = new HashSet<>();
        //结束端
        HashSet<String> end = new HashSet<>();
        //所有字符串的字典
        HashSet<String> dic = new HashSet<>(wordList);
        start.add(beginWord);
        end.add(endWord);
        if (!dic.contains(endWord)) return 0;
        //经历过上面的一系列判定，到这里的时候，若是有路径，则最小是2，所以以2开始
        return bfs(start, end, dic, 2);

    }

    public int bfs(HashSet<String> st, HashSet<String> ed, HashSet<String> dic, int l) {
        //双端查找的时候，若是有任意一段出现了“断裂”，也就是说明不存在能够连上的路径，则直接返回0
        if (st.size() == 0) return 0;
        if (st.size() > ed.size()) {//双端查找，为了优化时间，永远用少的去找多的，比如开始的时候塞进了1000个，而结尾只有3个，则肯定是从少的那一端开始走比较好
            return bfs(ed, st, dic, l);
        }
        //BFS的标记行为，即使用过的不重复使用
        dic.removeAll(st);
        //收集下一层临近点
        HashSet<String> next = new HashSet<>();
        for (String s : st) {
            char[] arr = s.toCharArray();
            for (int i = 0; i < arr.length; i++) {
                char tmp = arr[i];
                //变化
                for (char c = 'a'; c <= 'z'; c++) {
                    if (tmp == c) continue;
                    arr[i] = c;
                    String nstr = new String(arr);
                    if (dic.contains(nstr)) {
                        if (ed.contains(nstr)) return l;
                        else next.add(nstr);
                    }
                }
                //复原
                arr[i] = tmp;
            }
        }
        return bfs(next, ed, dic, l + 1);
    }

    /**
     * 根据数字二进制下 1 的数目排序
     * @param arr
     * @return
     */
    public int[] sortByBits(int[] arr) {
        if (arr == null) return new int[0];
        int length = arr.length;
        Integer[] result = new Integer[length];
        for (int i=0;i<length;++i) {
            result[i] = arr[i];
        }
        Arrays.sort(result,(a,b) -> {
            int bitCountA = Integer.bitCount(a);
            int bitCountB = Integer.bitCount(b);
            return bitCountA==bitCountB?a - b:bitCountA-bitCountB;
        });
        for (int i=0;i<length;++i) arr[i] = result[i];
        return arr;
    }
    
    class SortEntity {
        private int key;
        private int value;

        public SortEntity(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SortEntity)) return false;
            SortEntity that = (SortEntity) o;
            return getKey() == that.getKey() &&
                    getValue() == that.getValue();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getValue());
        }

    }

    /**
     * 区间和的个数
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        int len = nums.length;
        int result = 0;
        for (int i=0;i<len;++i) {
            long tmp = nums[i];
            for (int j=i;j<len;++j) {
                if (j>i) tmp += nums[j];
                System.out.println(MessageFormat.format("tmp:{0}",tmp));
                if (lower <= tmp && tmp <= upper) ++result;
            }
        }
        return result;
    }

    /**
     * 最接近原点的k个点
     * @param points
     * @param K
     * @return
     */
    public int[][] kClosest(int[][] points, int K) {
        if(points==null) return new int[0][0];
        /*
        int length = points.length;
        Queue<int[]> queue = new PriorityQueue<>((a,b) -> a[0]*a[0] - b[0]*b[0] + a[1]*a[1] - b[1]*b[1]);
        for (int i=0;i<length;++i) {
            queue.add(points[i]);
        }
        int size = queue.size();
        int len;
        if (K<size) len = K;
        else len = size;
        int[][] result = new int[len][2];
        for (int i=0;i<len;++i) {
            result[i] = queue.poll();
        }
        return  result;
        */

        return Arrays.stream(points)
                .sorted(Comparator.comparingInt(o -> (o[0] * o[0] + o[1] * o[1])))
                .limit(K)
                .collect(Collectors.toList())
                .toArray(new int[K][2]);
    }

    /**
     * 根据身高重建队列
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        //按照身高降序，K升序排序
        Arrays.sort(people,(o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0]);

        /**
         * K值定义为：排在H前面且身高大于或等于H的人数
         * 因为从身高降序开始插入，此时所有人身高都大于等于H
         * 因此K值即为需要插入的位置
         */
        List<int[]> list = new ArrayList<>();
        for (int[] arr:people) {
            list.add(arr[1],arr);
        }
        return list.toArray(new int[list.size()][]);
    }

    /**
     * 三角形的最大周长
     * @param A
     * @return
     */
    public int largestPerimeter(int[] A) {
        if (A==null || A.length<3) return 0;
        Arrays.sort(A);
        int length = A.length;
        for (int i=length-1;i>=2;--i) {
            if (A[i-2] + A[i-1] > A[i]) return A[i-2] + A[i-1] + A[i];
        }
        return 0;
    }

    private int findFirstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 小于一定不是解
            if (nums[mid] < target) {
                // 下一轮搜索区间是 [mid + 1, right]
                left = mid + 1;
            } else if (nums[mid] == target) {
                // 下一轮搜索区间是 [left, mid]
                right = mid;
            } else {
                // nums[mid] > target，下一轮搜索区间是 [left, mid - 1]
                right = mid - 1;
            }
        }

        if (nums[left] == target) {
            return left;
        }
        return -1;
    }

    private int findLastPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;
            if (nums[mid] > target) {
                // 下一轮搜索区间是 [left, mid - 1]
                right = mid - 1;
            } else if (nums[mid] == target){
                // 下一轮搜索区间是 [mid, right]
                left = mid;
            } else {
                // nums[mid] < target，下一轮搜索区间是 [mid + 1, right]
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 在排序数组中查找元素的第一个和最后一个位置
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1,-1};
        if (nums==null) return result;
        int length = nums.length;
        if (length==0) return result;
        /*int count = 0;
        for (int i=0;i<length;++i) {
            if (nums[i]==target) {
                ++count;
                result[1] = i;
            } else if (count > 0) break;
        }
        if (count>0) result[0] = result[1] - count;
        return result;*/
        int firstPosition = findFirstPosition(nums, target);
        if (firstPosition==-1) return result;
        int lastPosition = findLastPosition(nums, target);
        result[0] = firstPosition;
        result[1] = lastPosition;
        return result;
    }

    /**
     * 翻转矩阵后的得分
     * @param A
     * @return
     */
    public int matrixScore(int[][] A) {
        if(A==null) return 0;
        int rows = A.length;
        int cols = A[0].length;
        //先判断每一行首位是不是1,不是1就翻转
        for (int i=0;i<rows;++i) {
            if (A[i][0]==0) {
                for (int j=0;j<cols;++j) {
                    A[i][j] = 1- A[i][j];
                }
            }
        }
        //然后判断从第二列开始，每列1的个数要大于等于0
        for (int j=1;j<cols;++j) {
            int numOfZero = 0;
            for (int i=0;i<rows;++i) {
                if (A[i][j]==0) ++numOfZero;
            }
            //rows 才代表列的个数
            if (numOfZero > (rows-numOfZero)) {
                //翻转
                for (int i=0;i<rows;++i) {
                    A[i][j] = 1 - A[i][j];
                }
            }
        }
        //计算
        int result = 0;
        for (int i=0;i<rows;++i) {
            int tmp = 0;
            for (int j=0;j<cols;++j) {
                tmp += A[i][j] * Math.pow(2,cols-1-j);
            }
            result += tmp;
        }
        return result;
    }

    class kClosest {
        double dis;
        int[] point;

        public kClosest(double dis, int[] point) {
            this.dis = dis;
            this.point = point;
        }

        public double getDis() {
            return dis;
        }

        public void setDis(double dis) {
            this.dis = dis;
        }

        public int[] getPoint() {
            return point;
        }

        public void setPoint(int[] point) {
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof kClosest)) return false;
            kClosest kClosest = (kClosest) o;
            return Double.compare(kClosest.getDis(), getDis()) == 0 &&
                    Arrays.equals(getPoint(), kClosest.getPoint());
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(getDis());
            result = 31 * result + Arrays.hashCode(getPoint());
            return result;
        }
    }

    static class test {
        private Integer a;
        private String b;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public test() {
        }

        public test(Integer a, String b) {
            this.a = a;
            this.b = b;
        }
    }

    /**
     * 小数字符串 转 数值类型
     * 如："3.14" -> 3.14
     * @param str
     */
    public double stringToDouble(String str) {
        double result = 0;
        if (StringUtils.isEmpty(str)) return result;
        char[] chars = str.toCharArray();
        int length = chars.length;
        if (chars[0]=='.' || chars[length-1]=='.') return result;

        int index = 0;
        boolean positive = true;
        switch (chars[0]) {
            case '+':
                ++index;
                positive = true;
                break;
            case '-':
                ++index;
                positive = false;
                break;
            default:break;
        }

        for (;index<length;++index) {
            if (chars[index]!='.') {
                result = result*10 + (chars[index]-'0');
            } else break;
        }
        index = index+1;
        int dotNum = 1;
        for (;index<length;++index) {
            result = result*10 + (chars[index]-'0');
            dotNum *= 10;
        }
        result = result/dotNum;
        if (positive) return result;
        else return -result;
    }

    /**
     * 842.将数组拆分成斐波那契序列
     * @param S
     * @return
     */
    public List<Integer> splitIntoFibonacci(String S) {

        return null;
    }

    /**
     * 青蛙跳台阶问题
     * 青蛙只能跳一步或两步
     * @param n
     * @return
     */
    public int numWays(int n) {
        switch (n) {
            case 0:
            case 1: return 1;
            case 2: return 2;
            default:break;
        }
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i=3;i<=n;++i) {
            //第i个台阶的走法 = 第 i-1 个台阶的走法(最后只剩一步) + 第 i-2 个台阶的走法(最后只剩两步)之和
            dp[i] = (dp[i-1] + dp[i-2])%1000000007;
        }
        return dp[n];
    }

    /**
     * 1704.判断字符串的两半是否相似
     * 包含元音个数是否相同
     * 思路：直接设置两个index，一个在头，一个在尾，碰到对应的加数量，就是双指针
     * @param s
     * @return
     */
    public boolean halvesAreAlike(String s) {
        if (s==null) return true;
        if (s.trim().equals("")) return true;
        int length = s.length();
        if (length%2!=0) return false;
        int halfLen = length / 2;
        int pre = 0,sub = 0;
        for (int i=0;i<length;++i) {
            char c = s.charAt(i);
            if (i<halfLen) {
                //前半
                pre += halves(c);
            } else {
                //后半
                sub += halves(c);
            }
        }
        return pre==sub;
    }
    public int halves(char c) {
        switch (c) {
            case 'a': case 'e': case 'i': case 'o': case 'u': case 'A': case 'E': case 'I': case 'O': case 'U':
                return 1;
            default:
                return 0;
        }
    }

    /**
     * 1046. 最后一块石头的重量
     * 有一堆石头，每块石头的重量都是正整数。
     *
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     *
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     *
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        if (stones==null || stones.length==0) return 0;
        Arrays.sort(stones);
        int length = stones.length;
        while (length>=2) {
            int i = stones[length - 1] - stones[length - 2];
            if (i==0) {
                length = length - 2;
                stones[length] = i;
            } else {
                stones[length-2] = i;
                length = length - 1;
            }
            Arrays.sort(stones,0,length);
        }

        return stones[0];
    }

    /**
     * 86.分隔链表
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        if (head==null) return head;

        ListNode first = new ListNode(0);
        ListNode second = new ListNode(0);
        ListNode firstTail = first;
        ListNode secondTail = second;
        while (head!=null) {
            if (head.val < x) {
                //如果当前节点的值小于x，则把当前节点挂到第一个链表后面
                firstTail = firstTail.next = head;
            } else {
                //否则挂到第二个链表后面
                secondTail = secondTail.next = head;
            }
            head = head.next;
        }
        //拼接链表
        firstTail.next = second.next;
        secondTail.next = null;
        return first.next;

        /*
        //蠢办法
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        while (head!=null) {
            if (head.val < x) first.add(head.val);
            else second.add(head.val);
            head = head.next;
        }
        ListNode result = new ListNode(0);
        ListNode res = result;
        int firstSize = first.size();
        for (int i=0;i<firstSize;++i) {
            ListNode node = new ListNode(first.get(i));
            result.next = node;
            result = result.next;
        }
        int secondSize = second.size();
        for (int i=0;i<secondSize;++i) {
            ListNode node = new ListNode(second.get(i));
            result.next = node;
            result = result.next;
        }
        return res.next;
        */
    }

    /**
     * 509.斐波那契数
     * @param n
     * @return
     */
    public int fib(int n) {
        switch(n) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return fib(n-1) + fib(n-2);
        }
        /*
        //更高效版本
        if(n==0) return 0;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i=2;i<=n;++i) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
        */
    }

    /**
     * 830. 较大分组的位置
     * 滑动窗口、双指针
     * @param s
     * @return
     */
    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> result = new ArrayList<>();
        if(s==null || s.length()==0) return result;
        int len = s.length();
        /*
        //滑动窗口思想
        Set<Character> set = new HashSet<>();
        int count = 1;
        set.add(s.charAt(0));
        for (int i=1;i<len;++i) {
            char tmp = s.charAt(i);
            if (set.contains(tmp)) {
                ++count;
            } else {
                if (count >= 3) {
                    int left = i - count;
                    int right = i - 1;
                    result.add(new ArrayList<Integer>(){{add(left);add(right);}});
                }
                set.clear();
                set.add(tmp);
                count = 1;
            }
        }
        if (count>=3) {
            int left = len - count;
            int right = len-1;
            result.add(new ArrayList<Integer>(){{add(left);add(right);}});
        }
        */
        //双指针
        int left = 0,right = 1;
        while (right < len) {
            if (s.charAt(left) == s.charAt(right)) {
                ++right;
            } else {
                if (right - left >= 3) {
                    int tmpLeft = left;
                    int tmpRight = right-1;
                    result.add(new ArrayList<Integer>(){{add(tmpLeft);add(tmpRight);}});
                }
                left = right;
                ++right;
            }
        }
        if (right - left >= 3) {
            int tmpLeft = left;
            int tmpRight = right-1;
            result.add(new ArrayList<Integer>(){{add(tmpLeft);add(tmpRight);}});
        }
        return result;
    }

    /**
     * 通配符匹配
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        //match为 * 号匹配的个数
        int i=0,j=0,start = -1,match = 0;
        int lenS = s.length();
        int lenP = p.length();
        while (i<lenS) {
            // 一对一匹配,匹配成功一起移
            if (j < lenP && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
                ++i;
                ++j;
            //记录p的"*"的位置,还有s的位置
            } else if (j < lenP && p.charAt(j) == '*'){
                start = j;
                match = i;
                ++j;
            //j 回到 记录的下一个位置
            //match 更新下一个位置
            //这不代表用*匹配一个字符
            } else if (start != -1) {
                j = start + 1;
                match += 1;
                i = match;
            } else return false;
        }
        //将多余的 * 直接匹配空串
        while (j < lenP) {
            if (p.charAt(j) != '*') return false;
            ++j;
        }
        return true;
    }

    /**
     * 189.旋转数组
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        int l = k % length;
        //法一 双重循环
        for (int i=0;i<l;++i) {
            //每次都取最后一个
            int tmp = nums[length-1];
            //数组整体后移
            for (int j=length-1;j>0;--j) {
                nums[j] = nums[j-1];
            }
            //最后一个放到最前
            nums[0] = tmp;
        }

        //法二，翻转
        reverse(nums,0,length-1);
        reverse(nums,0,l-1);
        reverse(nums,l,length-1);
    }

    /**
     * 翻转数组
     * @param nums
     * @param start
     * @param end
     */
    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int tmp = nums[start];
            nums[start++] = nums[end];
            nums[end--] = tmp;
        }
    }

    /**
     * 559. N叉树的最大深度
     * DFS/BFS皆可
     * @param root
     * @return
     */
    public int maxDepth(Node root) {

        /*
        //DFS
        if (root==null) return 0;
        List<Node> children = root.children;
        int maxDepth = 0;
        if (children==null || children.size()==0) return 1;
        int len = children.size();
        for (int i=0;i<len;++i) {
            maxDepth = Math.max(maxDepth,maxDepth(children.get(i)));
        }
        return maxDepth+1;
        */

        //BFS
        if (root == null) return 0;
        List<Node> children = root.children;
        int maxDepth = 0;
        if (children==null || children.size()==0) return 1;
        Deque<Node> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            ++maxDepth;
            //将这一层的节点都加进去
            int size = queue.size();
            while (size > 0) {
                Node first = queue.pollFirst();
                List<Node> nodes = first.children;
                if (nodes!=null && nodes.size()>0) {
                    for (int i=0;i<nodes.size();++i) queue.addLast(nodes.get(i));
                }
                --size;
            }
        }
        return maxDepth;
    }

    /**
     * 二叉树深度
     * DFS/BFS
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        /*
        //DFS
        if (root==null) return 0;
        int maxDepth = 0;
        maxDepth = Math.max(maxDepth,Math.max(maxDepth(root.left),maxDepth(root.right)));
        return maxDepth + 1;
        */

        //BFS
        if (root==null) return 0;
        int maxDepth = 0;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            ++maxDepth;
            while (size > 0) {
                TreeNode treeNode = deque.pollFirst();
                if (treeNode.left!=null) deque.addLast(treeNode.left);
                if (treeNode.right!=null) deque.addLast(treeNode.right);
                --size;
            }
        }
        return maxDepth;
    }

    /**
     * 64.最小路径合
     * 动态规划
     * 限制：每次只能向下或者向右移动一步
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(i == 0 && j == 0) continue;
                else if(i == 0)  grid[i][j] = grid[i][j - 1] + grid[i][j];
                else if(j == 0)  grid[i][j] = grid[i - 1][j] + grid[i][j];
                else grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];
    }

    /**
     * 部分排序
     * 单调递增的
     * @param array
     * @return
     */
    public int[] subSort(int[] array) {
        int len = array.length;
        int max = Integer.MIN_VALUE,min = Integer.MAX_VALUE;
        int l = -1, r = -1;
        //寻找最靠右的那个数（满足左边存在大于它的数）
        for (int i=0;i<len;++i) {
            if (array[i] < max) r = i;
            else max = array[i];
        }
        //寻找最靠左的那个数（满足右边存在小于它的数）
        for (int i=len-1; i>=0; --i) {
            if (array[i] > min) l = i;
            else min = array[i];
        }
        return new int[]{l,r};
    }

    /**
     * 汇总区间
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<>();
        if (nums==null) return result;
        int len = nums.length;
        if (len==0) return result;
        if (len==1) {
            result.add(String.valueOf(nums[0]));
            return result;
        }
        int idx = nums[0];
        Integer left = null,right = null;
        for (int i=0;i<len;++i) {
            if (left==null) left = nums[i];
            if (idx != nums[i]) {
                right = nums[i-1];
                if (left.intValue() == right.intValue()) {
                    result.add(String.valueOf(left));
                } else {
                    String value = MessageFormat.format("{0}->{1}", left, right);
//                    String value = left + "->" + right;
                    result.add(value);
                }
                left = nums[i];
                idx = nums[i];
            } else ++idx;
        }
        if (left!=null) {
            int num = nums[len - 1];
            if (left.intValue()!=num) result.add(MessageFormat.format("{0}->{1}",left,num));
            else result.add(String.valueOf(left));
        }
        return result;
    }

    /**
     * 628. 三个数的最大乘积
     * 排序后，最大的就两种情况
     *  1.全是正数的情况下，取最大的三个正数
     *  2.有负数的情况下，取最小的两个负数和最大的正数
     * 取两种情况下的较大的一种
     * @param nums
     * @return
     */
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        int first = nums[len-1];
        int second = nums[len-2];
        int third = nums[len-3];
        int resultOne = first*second*third;
        int fourth = nums[0];
        int fifth = nums[1];
        int resultTwo = fourth*fifth*first;

        return Math.max(resultOne,resultTwo);
    }

    /**
     * 面试题 02.05. 链表求和
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int mod = 0;
        ListNode head = new ListNode(-1);
        ListNode tmpHead = head;
        while (l1!=null || l2!=null || mod > 0) {
            int sum = mod;
            if (l1!=null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2!=null) {
                sum += l2.val;
                l2 = l2.next;
            }
            mod = sum/10;
            tmpHead.next = new ListNode(sum%10);
            tmpHead = tmpHead.next;
        }
        return head.next;
    }

    /**
     * 1052. 爱生气的书店老板
     * 滑动窗口
     * 先计算出 原本的值 total，然后根据 X的窗口，求最大增益 maxIncr
     * 结果即 total + maxIncr
     * @param customers
     * @param grumpy
     * @param X
     * @return
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int custLen = customers.length;
        int total = 0;
        for(int i=0;i<custLen;++i) {
            total += customers[i] * (grumpy[i]^1);
        }
        int incr = 0;
        for (int i=0;i<X;++i) {
            incr += customers[i] * grumpy[i];
        }
        int maxIncr = incr;
        for (int i=X;i<custLen;++i) {
            incr = incr - (customers[i-X] * grumpy[i-X]) + (customers[i] * grumpy[i]);
            maxIncr = Math.max(maxIncr,incr);
        }
        return total + maxIncr;
    }

    /**
     * 最长公共子序列
     * 二维dp
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1==null || text2==null) return 0;
        int len1 = text1.length();
        int len2 = text2.length();
        //+1表示默认dp[0][0]都是空串
        int[][] dp = new int[len1+1][len2+1];
        for (int i=0;i<len1;++i) {
            for (int j=0;j<len2;++j) {
                char a = text1.charAt(i);
                char b = text2.charAt(j);
                if (a==b) {
                    //因为dp[0][0]是设置为空串，所以实际上 dp[i+1][j+1]才是正常的定位格，dp[i][j]是退格位置
                    dp[i+1][j+1] = dp[i][j] + 1;
                } else {
                    dp[i+1][j+1] = Math.max(dp[i][j+1],dp[i+1][j]);
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * leetcode 62题
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[] dp = new int[m];
        Arrays.fill(dp,1);
        for(int i=1;i<n;++i) {
            for(int j=1;j<m;++j) {
                dp[j] = dp[j-1] + dp[j];
            }
        }
        return dp[m-1];
    }

    /**
     * 股票的最大利润
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices==null) return 0;
        int len = prices.length;
        if (len==0) return 0;

        //状态 第i天 持有、不持有,不持有有今天卖了和原本就没有两种情况，持有有原本就持有和买了今天的
        int[][] dp = new int[len][2];
        //base case
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        //选择 buy、sell、rest
        for (int i=1;i<len;++i) {
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i]);
        }
        return dp[len-1][0];
    }

    /**
     * 最长递增子序列
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len];
        int res = nums[0];
        for(int i=0;i<len;++i) {
            dp[i] = 1;  //自己独自一个序列即是 1 的长度
            for(int j=0;j<i;++j) {
                if(nums[j] < nums[i]) dp[i] = Math.max(dp[i],dp[j]+1);
                res = Math.max(res,dp[i]);
            }
        }
        return res;
    }

    /**
     * 最长回文串
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int len = s.length();
        if(len<2) return s;
        char[] arr = s.toCharArray();
        int begin = 0;
        int maxLen = 1;
        for(int i=0;i<len-1;++i) {
            for(int j=i+1;j<len;++j) {
                if(j-i+1 > maxLen && isPalindrome(arr,i,j)) {
                    maxLen = j-i+1;
                    begin = i;
                }
            }
        }
        return s.substring(begin,begin+maxLen);
    }

    /**
     * 判断是否回文串
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public boolean isPalindrome(char[] arr, int left, int right) {
        while(left < right) {
            if(arr[left] != arr[right]) return false;
            ++left;
            --right;
        }
        return true;
    }

//    static long left = -1l;
//    static long right = 0l;
    //可用 TimeUnit 替换 Set 的Long 增大 并发度（TimeUnit可以精确到纳秒）
//    static Set<Long> set = new HashSet<>();
    static Map<String,Set<Long>> map = new HashMap<>();
    static long limitTime = 10*60*1000;
    public static boolean canInvoke(String methodName,String callerId){
        long cur = System.currentTimeMillis();
        String key = MessageFormat.format("{0}-{1}",methodName,callerId);
        Set<Long> tmpSet = map.get(key);
        if(tmpSet==null) {
//            if(left==-1) { left = l; }
            tmpSet = new HashSet<>();
            tmpSet.add(cur);
            map.put(key,tmpSet);
        } else {
//            int diff = (int) (dif/limitTime);
//            int count = set.size();
            //right - 10分钟即可，完全没必要记录left
            if(countSet(tmpSet,cur - limitTime,cur) > 1000) return true;
            tmpSet.add(cur);
        }
        return false;
    }
    private static int countSet(Set<Long> set, long left, long right) {
        int result = 0;
        for(long time:set) {
            if(left < time && time < right) ++result;
            else set.remove(time);
        }
        return result;
    }

    /**
     * 最大信封
     * @param envelopes
     * @return
     */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes,(o1,o2)->{
            if(o2[0]!=o1[0]) return o2[0]-o1[0];
            else return o2[1]-o1[1];
        });
        int row = envelopes.length;
        int res = 1;
        int curRow = envelopes[0][0],curCol = envelopes[0][1];
        for(int i=1;i<row;++i){
            if(envelopes[i][0]<curRow && envelopes[i][1]<curCol) {
                ++res;
                curRow = envelopes[i][0];
                curCol = envelopes[i][1];
            }
        }
        return res;
    }

    public static String changeStr(String str) {
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        if(str==null) return str;
        int len = str.length();
        for(int i=0;i<len;++i) {
            char c = str.charAt(i);
            if(c=='*') sb1.append(c);
            else sb2.append(c);
        }
        return (sb1.append(sb2)).toString();
    }

    //arr要有序
    private static int rrre = 0;
    public static void countResult(int[] arr, int idx,boolean[] used,int sum) {
        if (sum==24) {
            ++rrre;
            return;
        }
        if(idx==arr.length) {
            return;
        }

        for(int i=idx;i<arr.length;++i) {
            if(!used[i]) {
                if(i>0 && arr[i]==arr[i-1] && !used[i-1]) continue;
                sum += arr[i];
                used[i] = true;
                countResult(arr,i+1,used,sum);
                used[i] = false;
                sum -= arr[i];
            }
        }
    }


    TreeNode biNodeHead = new TreeNode(-1);
    TreeNode biNodePre = null;
    public TreeNode convertBiNode(TreeNode root) {
        if(root==null) return null;
        convertBiNode(root.left);
        if(biNodePre==null) biNodeHead.right = root;
        else biNodePre.right = root;
        biNodePre = root;
        root.left = null;
        convertBiNode(root.right);
        return biNodeHead.right;
    }

    /**
     * 圆环上有10个点，编号为0~9。从0出发，每次可以逆时针和顺时针走一步，问走n步回到0共有多少种走法。
     * @return
     */
    int res = 0;
    public int fun(int n){
        dfs(0,n);
        return res;
    }
    /**
     * @param i:当前索引
     * @param n：还剩几步
     */
    private void dfs(int i, int n) {
        //走完了
        if (n == 0){
            //是否回到了0点
            if (i == 0){
                //总方法数加一
                res++;
            }
            return;
        }
        //顺时针
        int nextL = (i + 1) % 10;
        dfs(nextL,n-1);
        //逆时针
        int nextR = (i - 1 + 10) % 10;
        dfs(nextR,n-1);
    }

    /**
     * 圆环上有10个点，编号为0~9。从0出发，每次可以逆时针和顺时针走一步，问走n步回到0共有多少种走法。
     * dp做法
     * @return
     */
    public int dp(int n){
        //matrix[i][j]表示在还有i步时，当前位置为j，可以有matrix[i][j]种方法走到0点
        int[][] matrix = new int[n+1][10];
        //只剩0步时，只能有0种方法（这里其实可以不写，写出来是方便理解）
        for (int i = 0;i < 10;i++) {
            matrix[0][i] = 0;
        }
        //初始化，只剩一步时，1,9位置都有一种方法到达0点，其它位置都是0种方法
        matrix[1][1] = 1;
        matrix[1][9] = 1;
        //i表示剩几步，从2步开始算
        for (int i =2;i < n+1;i++) {
            //j表示当前位置
            for (int j = 0;j < 10;j++) {
                if(j>0&&j<9)
                    //到达0点方法数等于前一步的两个位置之和（j-1和j+1代表顺/逆序时针的前一步位置）
                    matrix[i][j] = matrix[i - 1][j + 1] + matrix[i - 1][j - 1];
                if(j==0) //这里也是计算前一步的两个位置之和，但位置减一会小于0，所以直接用9代替逆时针的前一步位置
                    matrix[i][j] = matrix[i - 1][j + 1] + matrix[i - 1][9];
                if (j == 9)
                    matrix[i][j] = matrix[i - 1][0] + matrix[i - 1][j-1];
            }
        }
        return matrix[n][0];
    }

    /**
     * 圆环上有10个点，编号为0~9。从0出发，每次可以逆时针和顺时针走一步，问走n步回到0共有多少种走法。
     * @param n
     * @return
     */
    public int getSteps(int n) {
        int[][] matrix = new int[n+1][10];
        matrix[1][1] = 1;
        matrix[1][9] = 1;
        for(int i=2;i<n+1;++i) {
            for(int j=0;j<10;++j) {
                if(j>0 && j<9) matrix[i][j] = matrix[i-1][j+1] + matrix[i-1][j-1];
                if (j==0) matrix[i][j] = matrix[i-1][j+1] + matrix[i-1][9];
                if (j==9) matrix[i][j] = matrix[i-1][0] + matrix[i-1][j-1];
            }
        }
        return matrix[n][0];
    }

    /**
     * 判断是否奇偶树
     * @param root
     * @return
     */
    public boolean isEvenOddTree(TreeNode root) {
        if(root==null) return true;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int level = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            int tmpNux = -1;
            for(int i=0;i<size;++i) {
                TreeNode tmp = q.pop();
                if(level%2==0) {
                    //偶数层，奇整数，严格递增
                    if(tmp.val % 2 == 0) return false;
                    if(i>0) {
                        if(tmp.val <= tmpNux) return false;
                    }
                } else {
                    //奇数层，偶整数，严格递减
                    if(tmp.val % 2 == 1) return false;
                    if(i>0) {
                        if(tmp.val >= tmpNux) return false;
                    }
                }
                tmpNux = tmp.val;
                if(tmp.left != null) q.addLast(tmp.left);
                if(tmp.right != null) q.addLast(tmp.right);
            }
            ++level;
        }
        return true;
    }

    /**
     * 二叉树转链表
     * 递增顺序查找树
     */
    private TreeNode head = new TreeNode(-1);
    private TreeNode pre = null;
    public TreeNode increasingBST(TreeNode root) {
        if (root==null) return null;
        increasingBST(root.left);
        if (pre==null) head.right = root;
        else pre.right = root;
        pre = root;
        root.left = null;
        increasingBST(root.right);
        return head.right;
    }

    /**
     * 不同的子序列
     * @param s
     * @param t
     * @return
     */
    public int numDistinct(String s, String t) {
        int m = s.length(), n = t.length();
        //dp[0][x] 和 dp[x][0] 表示 s空串 或 t空串，所以设置为 m+1 和 n+1
        int[][] dp = new int[m+1][n+1];
        //base case
        for(int i=0;i<=m;++i) dp[i][0] = 1;
        for(int i=1;i<=m;++i) {
            for(int j=1;j<=n;++j) {
                if(j>i) break;
                //相等时，如果s选了这个字符，则t也要选，所以此时子序列应该是 dp[i-1][j-1]；s不选的话，就要看 剩下的s有没有t，即dp[i-1][j];
                if(s.charAt(i-1) == t.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
                else dp[i][j] = dp[i-1][j];
            }
        }
        return dp[m][n];
    }




    /**
     * 移除重复元素
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if(len==0) return 0;
        int j=0;
        for(int i=1;i<len;++i) {
            //后一个替换前一个
            if(nums[j]!=nums[i]) nums[++j] = nums[i];
        }
        return ++j;
    }

    /**
     * 移除重复元素II  每个元素允许有两个
     * @param nums
     * @return
     */
    public int removeDuplicatesII(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        if(nums.length < 2) return nums.length;
        int result = 2;
        for (int i=2;i<nums.length;i++) {
            if (nums[i] != nums[result-2]) nums[result++] = nums[i];
        }
        return result;
    }

    /**
     * 移除元素
     * @param nums
     * @param val 移除的给定元素
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int idx = 0;
        int len = nums.length;
        for(int i=0;i<len;++i) {
            if(nums[i]!=val) {
                nums[idx++] = nums[i];
            }
        }
        return idx;
    }

    /**
     * 反转数字
     * @param x
     * @return
     */
    public int reverse(int x) {
        int res = 0;
        while(x!=0) {
            int mod = x%10;
            if(res > Integer.MAX_VALUE/10 || (res == Integer.MAX_VALUE && mod > 7)) return 0;
            if(res < Integer.MIN_VALUE/10 || (res == Integer.MIN_VALUE && mod < -8)) return 0;
            res = res*10 + mod;
            x = x/10;
        }
        return res;
    }

    /**
     * 递增子序列
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        if(nums==null) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        findSubsequences(nums,0,Integer.MIN_VALUE, new ArrayList<>(), result);
        return result;
    }

    /**
     *
     * @param nums
     * @param idx 当前索引
     * @param last 前一个选择值
     * @param list
     * @param result
     */
    public void findSubsequences(int[] nums, int idx,int last, List<Integer> list, List<List<Integer>> result) {
        if (idx == nums.length) {
            if (list.size() >= 2) result.add(new ArrayList<>(list));
            return;
        }
        //符合递增要求，选择当前元素，最小值变为 当前值
        //只有当前的元素大于等于上一个选择的元素的时候才能选择这个元素，这样枚举出来的所有元素都是合法的
        if (nums[idx] >= last) {
            list.add(nums[idx]);
            findSubsequences(nums, idx + 1, nums[idx], list, result);
            list.remove(list.size()-1);
        }
        //当前元素不等于当前最小值时（防止重复），不选当前元素，直接找下一个，最小值依旧不变
        /**
         * 只有当当前的元素不等于上一个选择的元素的时候，才考虑不选择当前元素，直接递归后面的元素。因为如果有两个相同的元素，我们会考虑这样四种情况：
         *
         * 前者被选择，后者被选择
         * 前者被选择，后者不被选择
         * 前者不被选择，后者被选择
         * 前者不被选择，后者不被选择
         *
         * 其中第二种情况和第三种情况其实是等价的，我们这样限制之后，舍弃了第二种，保留了第三种，于是达到了去重的目的。
         *
         */
        if (nums[idx] != last) {
            findSubsequences(nums, idx + 1, last, list, result);
        }
    }

    /**
     * 跳跃游戏 II
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        if(nums==null || nums.length<=1) return 0;
        int start = 0;
        int end = 1;
        int result = 0;
        while(end < nums.length) {
            int maxStep = 0;
            //每次找最大的步数
            for(int i=start;i<end;++i) maxStep = Math.max(nums[i]+i,maxStep);
            start = end;
            end = maxStep + 1;
            result++;
        }
        return result;
    }

    /**
     * 三数只和 等于 0
     * 双指针法
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        //先排序，方便去重
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        int len = nums.length;
        for(int i=0;i<len;++i) {
            if(nums[i] > 0) break;
            //去重
            if(i>0 && nums[i] == nums[i-1]) continue;
            int target = -nums[i];
            int  left = i+1,right = len-1;
            while(left <  right) {
                int tmp = nums[left] + nums[right];
                if(tmp == target) {
                    result.add(new ArrayList<>(Arrays.asList(nums[i],nums[left],nums[right])));
                    ++left;
                    --right;
                    //去重
                    while (left < right && nums[left] == nums[left-1]) ++left;
                    while (left < right && nums[right] == nums[right+1]) --right;
                } else if (tmp < target) {
                    ++left;
                }  else {
                    --right;
                }
            }
        }
        return result;
    }

    /**
     * 丑数 就是只包含质因数 2、3 和/或 5 的正整数。
     * @param n
     * @return
     */
    public boolean isUgly(int n) {
        if(n==1) return true;
        if(n==0) return false;
        //递归法
        if(n%5==0) return isUgly(n/5);
        if(n%3==0) return isUgly(n/3);
        if(n%2==0) return isUgly(n/2);
        return false;
        //迭代法
        /*while (n%5==0) n = n/5;
        while (n%3==0) n = n/3;
        while (n%2==0) n = n/2;
        return n==1;*/
    }

    /**
     * 两数之和
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }

    /**
     * 合并链表
     * 合并两个有序的链表
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

        //递归解法
        /*if (l1==null) return l2;
        if (l2==null) return l1;
        if (l1.val <= l2.val) {
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1,l2.next);
            return l2;
        }*/
    }

    /**
     * 最大数
     * 字符串数字组成最大数
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        String[] numsStr = new String[nums.length];
        int len = nums.length;
        for(int i=0;i<len;++i) {
            numsStr[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(numsStr,
                //按照特殊的字符串排序 3，30 较大排序为 330 而不是 303，所以排序方式为 (o2+o1).compareTo((o1+o2)),即 "330".compareTo("303")
                (o1, o2) -> (o2+o1).compareTo(o1+o2));
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<len;++i) sb.append(numsStr[i]);

        //排除特殊情况，最开始都是"0"的情况
        while(sb.length() > 1 && sb.charAt(0) == '0') sb.deleteCharAt(0);
        return sb.toString();
    }

    /**
     * 合并K个升序链表
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if(lists==null || lists.size()==0) return null;
        int len = lists.size();

        ListNode result = new ListNode(Integer.MIN_VALUE);
        for(int i=0;i<len;++i) {
            result = mergeLists(result,lists.get(i));
        }
        return result.next;
    }

    public ListNode mergeLists(ListNode l1, ListNode l2) {
        if (l1==null) return l2;
        if (l2==null) return l1;
        if(l1.val <= l2.val) {
            l1.next = mergeLists(l1.next,l2);
            return l1;
        } else {
            l2.next = mergeLists(l1,l2.next);
            return l2;
        }
    }

    /**
     * 重排链表
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return;
        }
        ListNode mid = middleNode(head);
        ListNode l1 = head;
        ListNode l2 = mid.next;
        mid.next = null;
        l2 = reverseList(l2);
        mergeList(l1, l2);

        /*
        //递归
        if(head == null  || head.next == null || head.next.next == null) return;
        ListNode tmp = head;
        while (tmp.next.next != null) tmp = tmp.next;
        tmp.next.next = head.next;
        head.next = tmp.next;
        tmp.next = null;
        reorderList(head.next.next);


        //队列
        if(head==null || head.next==null || head.next.next==null) return;
        Deque<ListNode> one = new ArrayDeque<>();
        Deque<ListNode> two = new ArrayDeque<>();
        ListNode first = head;
        ListNode second = head;
        while (second.next!=null && second.next.next!=null) {
            one.add(first);
            first = first.next;
            second = second.next.next;
        }
        one.add(first);
        first = first.next;
        while (first!=null) {
            two.add(first);
            first = first.next;
        }

        ListNode result = head;
        if (one.size()==1 && two.size()==0) return;
        while (!one.isEmpty() || !two.isEmpty()) {
            if (!one.isEmpty()) {
                ListNode tmp = one.pollFirst();
                tmp.next = null;
                result.next = tmp;;
                result = result.next;
            }
            if (!two.isEmpty()) {
                ListNode tmp = two.pollLast();
                tmp.next = null;
                result.next = tmp;
                result = result.next;
            }
        }
         */
    }

    /**
     * 快慢指针求中间节点
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 反转链表
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    /**
     * 合并链表
     * @param l1
     * @param l2
     */
    public void mergeList(ListNode l1, ListNode l2) {
        ListNode l1_tmp;
        ListNode l2_tmp;
        while (l1 != null && l2 != null) {
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            l1.next = l2;
            l1 = l1_tmp;

            l2.next = l1;
            l2 = l2_tmp;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int reverse = solution.reverse(Integer.MIN_VALUE);
        System.out.println(reverse);
        int[] ints = {3,2,4};
        solution.twoSum(ints,6);
        solution.removeDuplicates(ints);
        System.out.println(Arrays.toString(ints));
        int steps = solution.getSteps(2);
        System.out.println(steps);
        Solution.countResult(new int[]{1,1,2,22,23},0,new boolean[5],0);
        System.out.println(rrre);
        //[[5,4],[6,4],[6,7],[2,3]][[46,89],[50,53],[52,68],[72,45],[77,81]]
        solution.maxEnvelopes(new int[][]{{46,89},{50,53},{52,68},{72,45},{77,81}});
        Scanner in = new Scanner(System.in);

        solution.isMatch("aa","a*");
        solution.longestPalindrome("babad");
        solution.lengthOfLIS(new int[]{1,3,6,7,9,4,10,5,6});
        solution.uniquePaths(3,2);
        System.out.println(19^1);
        String s1 = null;
        System.out.println((String) s1);

        solution.largeGroupPositions("babaaaabbb");
        ListNode partition = new ListNode(1);
        partition.next = new ListNode(2);
        solution.partition(partition,2);

        solution.lastStoneWeight(new int[]{});
        String tttt = "00219123";
        boolean b = tttt.startsWith("0029");

        int i2 = solution.numWays(7);
        System.out.println(i2);

        double v = solution.stringToDouble("3.14");

//        solution.matrixScore(new int[][]{{0,0,1,1},{1,0,1,0},{1,1,0,0}});
//        solution.matrixScore(new int[][]{{1,1},{1,1},{0,1}});
        solution.matrixScore(new int[][]{{0,1},{0,1},{0,1},{0,0}});

        solution.searchRange(new int[]{1,5},5);
        List<test> testArrayList = new ArrayList<>();
        testArrayList.add(new test(1,"3"));
        testArrayList.add(new test(2,"2"));
        StringBuffer sb = new StringBuffer();
        testArrayList.add(new test(3,"1"));
        System.out.println(testArrayList.toString());

        String format = MessageFormat.format("{0}-{1}[{2}]", 1, 2, 3);
        System.out.println(format);
        testArrayList.parallelStream().filter(o -> o.getA().intValue() == 5).forEach(t->{
            System.out.println("----");
        });

        String s = testArrayList.toString();
        System.out.println(s);
        boolean equals = "7".equals(null);

        List<test> collect1 = testArrayList.parallelStream().sorted((o1, o2) -> (o2.getA() - o1.getA())).collect(Collectors.toList());
        Optional<test> first = testArrayList.parallelStream().sorted((o1, o2) -> (o2.getA() - o1.getA())).filter(o -> {
            return String.valueOf(o.getA()).equals(o.getB());
        }).findFirst();
        if (first.isPresent()) {
            System.out.println("present");
        } else {
            System.out.println("no present");
        }

        solution.kClosest(new int[][]{{1,3},{-2,2}},1);

        int i1 = solution.countRangeSum(new int[]{-2147483647,0,-2147483647,2147483647}, -564, 3864);
        System.out.println(i1);

        List<String> list = Arrays.asList(new String[]{"12", "23"});

        ListNode node = new ListNode(1);
        solution.reorderList(node);

        TreeMap<Integer,Integer> map = new TreeMap<>();
        map.put(1,2);
        map.put(3,4);
        Collection<Integer> values = map.values();
        values.parallelStream().forEach(t->{
            System.out.println("----");
        });
        List<test> collect = values.parallelStream().map(t -> {
            if (t.intValue() == 1) {
                test test = new test();
                test.setA(t);
                test.setB(String.valueOf(t));
                return test;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

//        collect.removeAll(Collections.singleton(null));
        System.out.println(CollectionUtils.isEmpty(collect));

        String itnl = "131TEXT-9已";
        //去除数字，英文，汉字  之外的内容
//        String s = itnl.replaceAll("[^a-zA-Z0-9\\u4E00-\\u9FA5]", "");
        // replaceAll("[\\s*|\t|\r|\n]", "");  // 去除所有空格，制表符
        itnl = itnl.replaceAll("[^0-9]", "");
        System.out.println(itnl);
        char c = itnl.charAt(0);
        System.out.println(c);

        solution.subsets(new int[]{1,2,3});
        solution.combinationSum(new int[]{2,3,6,7},7);
        int i = solution.strToInt("91283472332");
        System.out.println(i);
        Map<Integer,Integer> hashmap = new HashMap<>();

        
//        int abcabcbb = solution.lengthOfLongestSubstring("abcabcbb");
//        System.out.println(abcabcbb);
//
//        solution.verifyPostorder(new int[]{1, 3, 2, 6, 5});
//
//        int translateNum = solution.translateNum(12228);
//        System.out.println(translateNum);
//
//        String[] abcs = solution.permutation("abc");
//        System.out.println(Arrays.toString(abcs));
//
//        int i = solution.lastRemaining(5, 3);
//        System.out.println(i);

        List<Integer> testSt = new ArrayList<Integer>(){{add(1);}};
        Integer[] strings = testSt.toArray(new Integer[10]);

        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int maxSubArray = solution.maxSubArray(nums);
        System.out.println(maxSubArray);
//        solution.isStraight(new int[]{0,11,3,13,7});
//        solution.maxSlidingWindow(new int[]{1,-1},1);
//        solution.reverseWords("the sky      is blue");
        solution.singleNumbers(new int[]{1,2,2,5});
        System.out.println(-15>>>1);
        System.out.println(Integer.toBinaryString((-15>>>1)));
        solution.maxProfit(new int[]{4,2,1,7});
    }
}
