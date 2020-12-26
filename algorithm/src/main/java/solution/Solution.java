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
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/7/4 00:18
 */
public class Solution {

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
        Node left;
        Node right;

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
            //记录历史最低价
            if (price<min) min = price;
            //取 前i日最大利润和当前价-历史最低价 的最大值
            dp[i] = Math.max(dp[i-1],prices[i]-min);
        }
        return dp[length-1];
    }

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
        /*
        //双指针（滑动窗口）法
        Set<Character> set = new HashSet<>();
        for(int l=0,r=0;r<len;++r) {
            char c = s.charAt(r);
            while(set.contains(c)) {
                //缩小滑动窗口左边边界
                set.remove(s.charAt(l));
                ++l;
            }
            set.add(c);
            result = Math.max(result,r-l+1);
        }
        return result;
        */
        return 0;
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

    public void reorderList(ListNode head) {
        if(head==null) return;
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

    public static void main(String[] args) {
        Solution solution = new Solution();
        double v = solution.stringToDouble("3.14");

//        solution.matrixScore(new int[][]{{0,0,1,1},{1,0,1,0},{1,1,0,0}});
//        solution.matrixScore(new int[][]{{1,1},{1,1},{0,1}});
        solution.matrixScore(new int[][]{{0,1},{0,1},{0,1},{0,0}});

        solution.searchRange(new int[]{1,5},5);
        List<test> testArrayList = new ArrayList<>();
        testArrayList.add(new test(1,"3"));
        testArrayList.add(new test(2,"2"));
        testArrayList.add(new test(3,"1"));

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
