package crackingTheCoding;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

/**
 * @author ZhXiQi
 * @Title: 程序员面试金典
 * @date 2021/1/10 11:15
 */
public class Solution {

    public static void main(String[] args) {
        int num = 1;
        num>>=1;
        System.out.println(num);
        Solution solution = new Solution();
        solution.subsets(new int[]{1,2,3});
        solution.findClosedNumbers(2147483647);
        solution.convertInteger(826966453,-729934991);
        int i = solution.waysToStep(5);
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        /**
         * [
         *  [1,2,3],
         *  [4,5,6],
         *  [7,8,9]
         * ]
         * 0,1 -> 1,2
         * [
         *  [9,6,3],
         *  [8,5,2],
         *  [7,4,1]
         * ]
         */
        solution.rotate(matrix);
        List<Integer> re = new ArrayList<>();
        StringBuffer sb = new StringBuffer();

        System.out.println(i);
    }

    /**
     * 面试题 17.01 不用加号的加法
     *
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b) {
        if (a==0) return b;
        if (b==0) return a;
        //a^b 不带进位的加法；a&b 进位数；(a&b) << 1 进位数乘10
        return add(a^b,(a&b)<<1);
    }

    /**
     * todo:不会
     * 面试题 04.09. 二叉搜索树序列
     * - 使用一个 queue 存储下个所有可能的节点
     * - 然后选择其中一个作为 path 的下一个元素
     * - 递归直到 queue 元素为空
     * - 将对应的 path 加入结果中
     * - 由于二叉搜索树没有重复元素，而且每次递归的使用元素的顺序都不一样，所以自动做到了去重
     * @param root
     * @return
     */
    public List<List<Integer>> BSTSequences(TreeNode root) {
        /*
        //bfs 只能找到其中一个数组
        List<List<Integer>> result = new ArrayList<>();
        if (root==null) {
            result.add(new ArrayList<>());
            return result;
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);
        List<Integer> list = new ArrayList<>();
        while (!deque.isEmpty()) {
            int size = deque.size();
            while (size > 0) {
                TreeNode treeNode = deque.pollFirst();
                list.add(treeNode.val);
                if (treeNode.left!=null) deque.addLast(treeNode.left);
                if (treeNode.right!=null) deque.addLast(treeNode.right);
                --size;
            }
        }
        result.add(list);
        return result;
        */

        List<List<Integer>> result = new ArrayList<>();
        if (root==null) {
            result.add(new ArrayList<>());
            return result;
        }
        LinkedList<Integer> path = new LinkedList<>();
        path.addLast(root.val);
        BSTSequences(root,new LinkedList<>(),path,result);
        return result;

    }
    public void BSTSequences(TreeNode root, LinkedList<TreeNode> queue, LinkedList<Integer> path,
                             List<List<Integer>> result) {
        if (root == null) return;
        if (root.left!=null) queue.addLast(root.left);
        if (root.right!=null) queue.addLast(root.right);

        if (queue.isEmpty()) {
            result.add(new LinkedList<>(path));
            return;
        }
        int lens = queue.size();
        for (int i=0;i<lens;++i) {
            TreeNode cur = queue.get(i);
            queue.remove(i);
            path.add(cur.val);

            BSTSequences(root,new LinkedList<>(queue),path,result);

            queue.add(i,cur);
            path.removeLast();
        }
    }

    /**
     * todo: 不会
     * 面试题 17.07. 婴儿名字
     * @param names
     * @param synonyms
     * @return
     */
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        return null;
    }

    /**
     * 面试题 17.16. 按摩师
     * 打家劫舍题
     * @param nums
     * @return
     */
    public int massage(int[] nums) {
        if (nums==null || nums.length==0) return 0;
        int len = nums.length;
        if (len==1) return nums[0];
        int[] dp = new int[len];
        dp[0] = nums[0];
        //选了前一个，当前就不能选，所以需要选择 前一个 和 当前 的最大者
        dp[1] = Math.max(dp[0],nums[1]);
        for (int i=2;i<len;++i) {
            //选前一个(选了前一个，当前这个就不能选) 和 前两个(前一个没选，所以可以选当前) + 当前 两种之中更大的那个
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
        }
        return dp[len-1];
    }

    /**
     * 面试题 08.01. 三步问题
     * 青蛙跳台题
     * @param n
     * @return
     */
    public int waysToStep(int n) {
        switch (n) {
            case 0:return 1;
            case 1:return 1;
            case 2:return 2;
            case 3:return 4;
            default:break;
        }
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for (int i=4;i<=n;++i) {
            //i个台阶 = 差 1 个台阶时的可能 + 差 2 个台阶时的可能 + 差 3 个台阶时的可能
            //两个数据相加的时候就要考虑 溢出问题
            dp[i] = ((dp[i-1] + dp[i-2])%1000000007 + dp[i-3])%1000000007;
        }
        return dp[n];
    }

    /**
     * 面试题 02.03. 删除中间节点
     * 只给出要删除的节点,将要删除的节点变成他下一个节点的值，然后删除它下一个节点
     * @param node
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 面试题 04.02. 最小高度树
     * 根据中序遍历建立二叉树
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        int len = nums.length;
        if (len==0) return null;
        int rootIdx = len / 2;
        TreeNode root = new TreeNode(nums[rootIdx]);
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums,0,rootIdx));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums,rootIdx+1,len));
        return root;
//        return sortedArrayToBST(nums,0,len-1);
    }
    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if (left==right) return null;
        int rootIdx = (left + (right-left))/2;
        TreeNode root = new TreeNode(nums[rootIdx]);
        root.left = sortedArrayToBST(nums,left,rootIdx-1);
        root.right = sortedArrayToBST(nums,rootIdx+1,right);
        return root;
    }

    /**
     * 面试题 02.02. 返回倒数第 k 个节点
     * 双指针
     * @param head
     * @param k
     * @return
     */
    public int kthToLast(ListNode head, int k) {
        ListNode cur = head;
        ListNode pre = head;
        while (cur != null) {
            cur = cur.next;
            --k;
            if (k==0) break;
        }
        if (k>0) return pre.val;
        while (cur != null) {
            pre = pre.next;
            cur = cur.next;
        }
        return pre.val;
    }

    /**
     * 面试题 16.07. 最大数值
     * 首先
     *      int k = (a ^ b) >>> 31;
     *      获得 a 与 b 是否同号
     *      同号 k == 0
     *      异号 无符号 右移 k == 1
     *
     * 然后
     *      如何区分 同号 与 异号
     *      异号时, 直接 使用 k
     *      同号时, k == 0, 将 (k-1) 无符号 右移 获得 符号位
     *      k == 1 时, (k-1) >>> 31 == 0
     *      k == 0 时, (k-1) >>> 31 == 1
     *
     * 最后
     *      1.异号时 只需要输出 符号位 为0的，即正数即可
     *          > ((a>>31)+1) 符号位右移时，负数右移得 -1，正数右移得 0
     *          > a * ((a>>31)+1) + b * ((b>>31)+1) 可以得到正数的值
     *      2.同号时 需要输出 大的值
     *          ？？？？
     * @param a
     * @param b
     * @return
     */
    public int maximum(int a, int b) {
        int k = (a ^ b) >>> 31;
        return k * (a * ((a >> 31) + 1) + b * ((b >> 31) + 1)) +
                ((k-1)>>>31) * (a * ((a - b) >>> 31 ^ 1) + b * ((((a - b) >>> 31) + 1) & 1 ^ 1));
    }

    /**
     * 面试题 01.01. 判定字符是否唯一
     * @param astr
     * @return
     */
    public boolean isUnique(String astr) {
        int len = astr.length();
        for (int i=0;i<len;++i) {
            int i1 = astr.lastIndexOf(astr.charAt(i));
            if (i1 != i) return false;
        }
        return true;
    }

    /**
     * 面试题 02.01. 移除重复节点
     * @param head
     * @return
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        if (head==null) return head;
        int[] map = new int[20001];
        ListNode cur = head;
        map[cur.val] = 1;
        while (cur.next != null) {
            if (cur.val == cur.next.val || map[cur.next.val] == 1) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
                map[cur.val] = 1;
            }
        }
        return head;
    }

    /**
     * 面试题 01.02. 判定是否互为字符重排
     * @param s1
     * @param s2
     * @return
     */
    public boolean CheckPermutation(String s1, String s2) {
        if (s1==null && s2==null) return true;
        if (s1==null || s2==null) return false;
        int lenS1 = s1.length();
        int lenS2 = s2.length();
        if (lenS1!=lenS2) return false;
        /*Map<Character,Integer> map = new HashMap<>();
        for (int i=0;i<lenS1;++i) {
            char c = s1.charAt(i);
            map.put(c,map.getOrDefault(c,0)+1);
        }
        for (int i=0;i<lenS2;++i) {
            char c = s2.charAt(i);
            map.put(c,map.getOrDefault(c,0)-1);
        }
        Set<Character> characters = map.keySet();
        for (Character c:characters) {
            if (map.get(c)!=0) return false;
        }
        return true;*/
        //ASCII码表 0～127
        int[] ascii = new int[128];
        for (int i=0;i<lenS1;++i) {
            ascii[s1.charAt(i)]++;
            ascii[s2.charAt(i)]--;
        }
        for (int i=0;i<128;++i) {
            if (ascii[i]!=0) return false;
        }
        return true;

    }

    /**
     * 面试题 01.03. URL化
     * @param S
     * @param length
     * @return
     */
    public String replaceSpaces(String S, int length) {
        String _blank = "%20";
        if (S==null) return S;
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<length;++i) {
            char c = S.charAt(i);
            if (c==' ') sb.append(_blank);
            else sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 面试题 01.05. 一次编辑
     * @param first
     * @param second
     * @return
     */
    public boolean oneEditAway(String first, String second) {
        if(first==null && second==null) return true;
        int lenF = first.length();
        int lenS = second.length();
        int len = lenF-lenS;
        if(len > 1 || len < -1) return false;
        int canEditCount = 1;
        for (int i=0,j=0;i<lenF&&j<lenS;++i,++j) {
            if(first.charAt(i)!=second.charAt(j)) {
                if(len==1) { //说明 first 长于 second
                    --j; //此处的 j减一和for循环里的加一抵消，相当于 first往前走，second保持原地不变
                } else if (len == -1) { //说明 second 长于 first
                    --i; //此处的i减一和for循环里的加一抵消，相当于 second往前走，first保持原地不变
                }
                --canEditCount;
            }
        }
        if(canEditCount<0) return false;
        return true;
    }

    /**
     * 面试题 01.07. 旋转矩阵
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        /**
         * 先沿对角线交换
         * [
         *  [1,2,3],
         *  [4,5,6],
         *  [7,8,9]
         * ]
         * 0,0 -> 2,2   0,1 -> 1,2
         * [
         *  [9,6,3],
         *  [8,5,2],
         *  [7,4,1]
         * ]
         */
        for(int i=0;i<rows-1;++i) {
            for (int j=0;j<rows-i;++j) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[cols - j - 1][rows - i - 1];
                matrix[cols-j-1][rows-i-1] = tmp;
            }
        }
        /**
         * 然后上下翻转
         * [
         *  [9,6,3],
         *  [8,5,2],
         *  [7,4,1]
         * ]
         * 0,0 -> 2,0
         * [
         *  [7,4,1],
         *  [8,5,2],
         *  [9,6,3]
         * ]
         */
        for (int i=0;i<rows/2;++i) {
            for (int j=0;j<cols;++j) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[rows-i-1][j];
                matrix[rows-i-1][j] = tmp;
            }
        }
    }

    /**
     * 面试题 01.08. 零矩阵
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] row = new int[rows];
        int[] col = new int[cols];
        for(int i=0;i<rows;++i) {
            for (int j=0;j<cols;++j) {
                if (matrix[i][j]==0) {
                    row[i] = 1;
                    col[j] = 1;
                }
            }
        }
        for (int i=0;i<rows;++i) {
            for (int j=0;j<cols;++j) {
                if (row[i]==1 || col[j]==1) matrix[i][j] = 0;
            }
        }
    }

    /**
     * 面试题 01.09. 字符串轮转
     * @param s1
     * @param s2
     * @return
     */
    public boolean isFlipedString(String s1, String s2) {
        if(s1==null && s2==null) return true;
        if(s1==null || s2==null) return false;
        if(s1.length() != s2.length()) return false;
        String s3 = s2+s2;
        return s3.contains(s1)?true:false;
    }

    /**
     * 面试题 05.01. 插入
     * @param N
     * @param M
     * @param i
     * @param j
     * @return
     */
    public int insertBits(int N, int M, int i, int j) {
        //先将N的 i～j 位清0
        N &= ~((((long)1 << (j-i+1)) - 1) << i);

        N |= (M<<i);
        return N;
    }

    /**
     * 面试题 05.02. 二进制数转字符串
     * @param num
     * @return
     */
    public String printBin(double num) {
        String err = "ERROR";
        if (num < 0 || num > 1) return err;
        StringBuffer sb = new StringBuffer();
        sb.append("0.");
        while (num != 0) {
            num *= 2;
            sb.append(num-1>=0?1:0);
            if(num>=1) num--;
            if(sb.length()>32) return err;
        }
        return sb.toString();
    }

    /**
     * 面试题 05.03. 翻转数位
     * @param num
     * @return
     */
    public int reverseBits(int num) {
        int l=0,r=0,max=0;
        for(int i=0;i<32;++i) {
            if((num&1)==1) ++r;
            else {
                l = r+1;
                r=0;
            }
            max = Math.max(l+r,max);
            num>>=1;
        }
        return max;
    }

    /**
     * 面试题 05.04. 下一个数
     * @param num
     * @return
     */
    public int[] findClosedNumbers(int num) {
        int bitCount = Integer.bitCount(num);
        int up = num + 1;
        int down = num - 1;
        int l=-1,r=-1;
        while (up <= (num*2<0?Integer.MAX_VALUE:num*2)) {
            if (Integer.bitCount(up)==bitCount) {
                l=up;
                break;
            }
            if (up<0) break;
            ++up;
        }
        while (down >= num/2) {
            if (Integer.bitCount(down)==bitCount) {
                r=down;
                break;
            }
            --down;
        }
        return new int[]{l,r};
    }

    /**
     * 面试题 05.06. 整数转换
     * @param A
     * @param B
     * @return
     */
    public int convertInteger(int A, int B) {
        int binC = A ^ B;
        /*int result = 0;
        //统计位中 1 的个数
        while (binC!=0) {
            binC&=(binC-1);
            ++result;
        }
        return result;*/
        return Integer.bitCount(binC);
    }


    List<List<Integer>> result = new LinkedList<>();
    public List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid==null ||  obstacleGrid.length==0 || obstacleGrid[0].length==0) return new ArrayList<>();
        pathWithObstacles(obstacleGrid,0,0,obstacleGrid.length-1,obstacleGrid[0].length-1);

        return result;
    }
    /**
     * 找到一个就可以，使用这种
     */
    public boolean pathWithObstacles(int[][] obstacleGrid, int i, int j, int row, int col) {
        if (i>row || j>col || obstacleGrid[i][j]==1) return false;
        LinkedList l = new LinkedList<>();
        l.add(i);
        l.add(j);
        result.add(l);
        /**
         * 找到一个就可以，使用这种
         */
        if ((i==row && j==col) || pathWithObstacles(obstacleGrid,i,j+1,row,col) || pathWithObstacles(obstacleGrid,i+1,j,row,col)) {
            return true;
        }
        result.remove(result.size()-1);
        //剪枝
        obstacleGrid[i][j] = 1;

        return false;
    }

    List<List<Integer>> subsetsRes = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        subsets(nums,0, new ArrayList<>());
        return subsetsRes;
    }
    public void subsets(int[] nums, int idx, List<Integer> sets) {
        subsetsRes.add(new ArrayList<>(sets));
        for (int i=idx;i<nums.length;++i) {
            sets.add(nums[i]);
            subsets(nums, i+1, sets);
            sets.remove(sets.size()-1);
        }
    }

    /**
     * 堆箱子
     * 动态规划之最长升上子序列变体
     * dp，将数组按照某一维排序，最大高度是排序后的某一子序列。用dp[i]标记以第n个箱子为结尾的最大高度和
     * @param box
     * @return
     */
    public int pileBox(int[][] box) {
        int len = box.length;
        if(len==0) return 0;
        Arrays.sort(box,(o1,o2)->o1[0]-o2[0]);
        int[] dp = new int[len];
        int result = 0;
        //以 i 为底
        for (int i=0;i<len;++i) {
            dp[i] = box[i][2];
            //因为已经从小到大排过序，所以 以i为上一层的情况下，j大于i肯定不满足长、宽、高 底层都大于上层的条件
            for(int j=0;j<i;++j) {
                //box[i] 为新的一层，box[j]为底下旧的一层
                if(box[i][0] > box[j][0] && box[i][1] > box[j][1] && box[i][2] > box[j][2]) {
                    dp[i] = Math.max(dp[i],dp[j]+box[i][2]);
                }
            }
            result = Math.max(result,dp[i]);
        }
        return result;
    }


}

/**
 * 面试题 03.06. 动物收容所
 */
class AnimalShelf {

    private Deque<int[]> dog = new LinkedList<>();
    private Deque<int[]> cat = new LinkedList<>();
    private Deque<int[]> animal = new LinkedList<>();

    private final int[] empty = new int[]{-1,-1};

    public AnimalShelf() {

    }

    public void enqueue(int[] animal) {
        int animalType = animal[1];
        int size = this.animal.size();
        if (size>=20000) return;
        this.animal.addLast(animal);
        switch (animalType) {
            case 0:
                //cat
                cat.addLast(animal);
                break;
            case 1:
                //dog
                dog.addLast(animal);
                break;
            default:break;
        }
    }

    public int[] dequeueAny() {
        int[] any = this.animal.pollFirst();
        if (any==null) return empty;
        int animalType = any[1];
        if (animalType==0) cat.pollFirst();
        else dog.pollFirst();
        return any;
    }

    public int[] dequeueDog() {
        int[] dog = this.dog.pollFirst();
        if (dog==null) return empty;
        this.animal.removeIf(tmp->tmp[0]==dog[0] && tmp[1]==dog[1]);
        return dog;
    }

    public int[] dequeueCat() {
        int[] cat = this.cat.pollFirst();
        if (cat==null) return empty;
        this.animal.removeIf(tmp->tmp[0]==cat[0] && tmp[1]==cat[1]);
        return cat;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}


class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
