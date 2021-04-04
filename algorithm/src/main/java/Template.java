
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ZhXiQi
 * @Title: 代码模板，要求熟能生巧
 * @date 2021/3/30 19:05
 */
public class Template {
    /**
     * N皇后问题
     * 典型的回溯问题
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        char[][] grid = new char[n][n];
        for (int i=0;i<n;++i) {
            for (int j=0;j<n;++j) {
                grid[i][j] = '.';
            }
        }
        //每一列是否有皇后
        boolean[] cols = new boolean[n];
        //对角线是否有皇后，斜线为从左上到右下方向，同一条斜线上的每个位置满足行下标与列下标之差相等 即 (0,0) 到 (n,n) ，也就是最长为 (n-0)+(n-0) = n+n
        boolean[] diagonal = new boolean[n+n];
        //反对角线是否有皇后,反斜线为从右上到左下方向，即 (0,n) 到 (n,0) 也就是 abs(0-n) + (n-0) = n+n
        boolean[] undiagonal = new boolean[n+n];
        List<List<String>> result = new ArrayList<>();
        solveNQueens(0,n,grid,cols,diagonal,undiagonal,result);
        return result;
    }

    public void solveNQueens(int row, int n,char[][] grid, boolean[] cols, boolean[] diagonal, boolean[] undiagonal, List<List<String>> result) {
        if (row == n) {
            List<String> list = new ArrayList<>();
            for (int i=0;i<grid.length;++i) {
                list.add(new String(grid[i]));
            }
            result.add(list);
            return;
        }
        for (int col=0;col<n;++col) {
            if (!cols[col] && !diagonal[n-row+col] && !undiagonal[row+col]) {
                grid[row][col] = 'Q';
                cols[col] = true;
                diagonal[n-row+col] = true;
                undiagonal[row+col] = true;
                solveNQueens(row+1,n,grid,cols,diagonal,undiagonal,result);
                grid[row][col] = '.';
                cols[col] = false;
                diagonal[n-row+col] = false;
                undiagonal[row+col] = false;
            }
        }
    }

    /**
     * 零钱置换问题 和 青蛙跳台问题类似，只是青蛙跳台问题固定只有 1和2两种情况，降维为两个点
     * 如果青蛙跳台问题，泛化成任意给定台阶数，如1，2，5，则只需要修改我们的DP方程：
     *      DP[i] = DP[i-1] + DP[i-2] + DP[i-5], 也就是DP[i] = DP[i] + DP[i-j] ,j =1,2,5

     * 组合问题：我们不关心硬币使用的顺序，而是硬币有没有被用到。是否使用第k个硬币受到之前情况的影响。
     * 状态转移方程：
     if 金额数大于硬币
     DP[k][i] = DP[k-1][i] + DP[k][i-coins[k]]
     else
     DP[k][i] = DP[k-1][i]
     可进行降维： DP[i] = DP[i] + DP[i-coins[k]]，但是降维处理后，双循环，内外循环不能交换，交换了就变成排列问题了
     for (int coin : coins){ //枚举硬币
     for (int i = 1; i <= amount; i++){ //枚举金额
     if (i < coin) continue; // coin不能大于amount
     dp[i] += dp[i-coin];
     }
     }
     *
     * 排列问题：会把 1，2 和 2，1 当成两种情况
     *
     *
     * @param amount 总额
     * @param coins 可选硬币
     * @return
     */
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for (int coin:coins) {
            for (int i=coin;i<=amount;++i) {
                if (i<coin) continue;   // coin不能大于amount
                dp[i] = dp[i] + dp[i-coin];
            }
        }
        return dp[amount];
    }

    /**
     * 括号
     * 回溯
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        generateParenthesis(0, 0, n, "", result);
        return result;
    }
    public void generateParenthesis(int numOfChar, int numOfLeftBracket, int n, String s, List<String> result) {
        //括号是一对才有效，所以 n 个组合，字符长度为 2*n
        if (numOfChar == 2*n) {
            result.add(s);
            return;
        }
        //左括号数量少于 n，则优先添加左括号
        if (numOfLeftBracket < n) generateParenthesis(numOfChar+1, numOfLeftBracket+1, n, s+'(', result);
        //有括号数量“numOfChar-numOfLeftBracket" 少于 左括号数量，不成对，需要添加右括号
        if (numOfChar-numOfLeftBracket < numOfLeftBracket) generateParenthesis(numOfChar+1, numOfLeftBracket, n, s+')', result);
    }
    /**
     * 基本dp
     * 打家劫舍
     * 打家劫舍II 为环，可以看为 选择偷第一个，不偷最后一个 和 不偷第一个，偷最后一个 两个列表打家劫舍的中的最大值
     * 打家劫舍III 房屋为树形
     */
    public int rob(int[] nums) {
        if (nums==null) return 0;
        int len = nums.length;
        if (len==0) return 0;
        if (len==1) return nums[0];
        int[] dp = new int[len];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for (int i=2;i<len;++i) {
            dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);
        }
        return dp[len-1];
    }

    public int robII(int[] nums) {
        int len = nums.length;
        switch(len) {
            case 1:return nums[0];
            case 2:return Math.max(nums[0],nums[1]);
            case 3:return Math.max(Math.max(nums[0],nums[2]),nums[1]);
            default:break;
        }
        // int[] dpOne = new int[len];
        int dpOne = 0;
        int ppreOne = nums[0];
        int preOne = Math.max(nums[0],nums[1]);
        for(int i=2;i<len-1;++i) {
            dpOne = Math.max(ppreOne+nums[i],preOne);
            ppreOne = preOne;
            preOne = dpOne;
        }
        // int[] dpTwo = new int[len];
        int dpTwo = 0;
        int ppreTwo = nums[1];
        int preTwo = Math.max(nums[1],nums[2]);
        for(int i=3;i<len;++i) {
            dpTwo = Math.max(ppreTwo+nums[i],preTwo);
            ppreTwo = preTwo;
            preTwo = dpTwo;
        }
        return Math.max(dpOne,dpTwo);
    }

    /**
     * 打家劫舍III
     * 树和动态规划结合
     * f(o) 表示选o节点，g(o)表示不选o节点，
     *      则  f(o) = g(o.left) + g(o.right)
     *          g(o) = max(f(o.left),g(o.left)) + max(f(o.right),g(o.right))
     * result = max(f(o),g(o))
     *
     * @param root
     * @return
     */
    public int robIII(TreeNode root) {
        if(root==null) return 0;
        int[] result = robIIIDfs(root);
        return Math.max(result[0], result[1]);
    }
    /**
     * 后序遍历
     * @param root
     * @return [0,1] -> 0:表示选当前节点f(o),1:表示不选当前节点g(o)
     */
    public int[] robIIIDfs(TreeNode root) {
        if (root==null) return new int[]{0,0};
        int[] left = robIIIDfs(root.left);
        int[] right = robIIIDfs(root.right);
        int selected = root.val + left[1] + right[1];
        int noSelected = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return new int[]{selected,noSelected};
    }

    //回溯 、剪枝
    public List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid==null ||  obstacleGrid.length==0 || obstacleGrid[0].length==0) return new ArrayList<>();
        List<List<Integer>> resultPathWithObstacles = new LinkedList<>();
        pathWithObstacles(obstacleGrid,0,0,obstacleGrid.length-1,obstacleGrid[0].length-1, resultPathWithObstacles);

        return resultPathWithObstacles;
    }
    /**
     * 找到一个就可以，使用这种
     */
    public boolean pathWithObstacles(int[][] obstacleGrid, int i, int j, int row, int col, List<List<Integer>> resultPathWithObstacles) {
        if (i>row || j>col || obstacleGrid[i][j]==1) return false;
        LinkedList l = new LinkedList<>();
        l.add(i);
        l.add(j);
        resultPathWithObstacles.add(l);
        /**
         * 找到一个就可以，使用这种
         * 是否到达目的地 或者 没到达目的地，从右边走是否可以 或者 没到达目的地，从下边做是否可以
         */
        if ((i==row && j==col) || pathWithObstacles(obstacleGrid,i,j+1,row,col,resultPathWithObstacles)
                || pathWithObstacles(obstacleGrid,i+1,j,row,col,resultPathWithObstacles)) {
            return true;
        }
        resultPathWithObstacles.remove(resultPathWithObstacles.size()-1);
        //剪枝
        obstacleGrid[i][j] = 1;

        return false;
    }

    /**
     *  输入： nums = [1,2,3]
     输出：
     [
     [3],
       [1],
       [2],
       [1,2,3],
       [1,3],
       [2,3],
       [1,2],
       []
     ]
     */
    //非重复子集,幂集
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsetsRes = new ArrayList<>();
        subsets(nums,0, new ArrayList<>(), subsetsRes);
        return subsetsRes;
    }
    public void subsets(int[] nums, int idx, List<Integer> sets,List<List<Integer>> subsetsRes) {
        subsetsRes.add(new ArrayList<>(sets));
        for (int i=idx;i<nums.length;++i) {
            //包含重复元素的话，先对数组排序，然后 if(i>0 && nums[i-1]==nums[i] && !used[i-1]) continue;
            sets.add(nums[i]);
            subsets(nums, i+1, sets,subsetsRes);
            sets.remove(sets.size()-1);
        }
    }

    /**
     * 无重复字符串的排列组合
     * 字符串长度在 9 位以内
     */
    public String[] permutation(String S) {
        List<String> permutation = new ArrayList<>();
        boolean[] used = new boolean[10];
        permutation(S,new StringBuffer(),permutation,used);
        int size = permutation.size();
        return permutation.toArray(new String[size]);
    }
    public void permutation(String S, StringBuffer sb,List<String> permutation,boolean[] used) {
        if (sb.length() >= S.length()) {
            permutation.add(new String(sb.toString()));
            return;
        }

        for (int i=0;i<S.length();++i) {
            if(!used[i]) {
                sb.append(S.charAt(i));
                used[i] = true;
                permutation(S,sb,permutation,used);
                used[i] = false;
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }

    /**
     * 有重复字符串的排列组合
     */
    public String[] permutationWithRepeat(String S) {
        char[] arr = S.toCharArray();
        //这个排序关键
        Arrays.sort(arr);
        List<String> permutationWithRepeat = new ArrayList<>();
        boolean[] used = new boolean[10];
        permutationWithRepeat(S,new StringBuffer(),arr,permutationWithRepeat,used);
        return permutationWithRepeat.toArray(new String[permutationWithRepeat.size()]);
    }

    public void permutationWithRepeat(String S, StringBuffer sb, char[] arr,List<String> permutationWithRepeat,boolean[] used) {
        if (sb.length() >= S.length()) {
            permutationWithRepeat.add(new String(sb.toString()));
            return;
        }
        for (int i=0;i<S.length();++i) {
            if (!used[i]) {
                if (i > 0 && arr[i]==arr[i-1] && !used[i-1]) continue;
                sb.append(arr[i]);
                used[i] = true;
                permutationWithRepeat(S,sb,arr,permutationWithRepeat,used);
                used[i] = false;
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }

    //非递归前序遍历
    public void preOrderN (TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode p = root;
        System.out.print("preOrderN:");
        while (p!=null || !q.isEmpty()) {
            while (p!=null) {
                //访问值
                System.out.print(p.val + " ");
                q.push(p);
                p = p.left;
            }
            if (!q.isEmpty()) {
                p = q.pop();
                p = p.right;
            }
        }
        System.out.println();
    }
    //非递归中序遍历
    public void inOrderN (TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode p = root;
        System.out.print("inOrderN:");
        while (p!=null || !q.isEmpty()) {
            while (p!=null) {
                q.push(p);
                p = p.left;
            }
            if (!q.isEmpty()) {
                p = q.pop();
                //访问节点
                System.out.print(p.val+" ");
                p = p.right;
            }
        }
        System.out.println();
    }
    //非递归后序遍历
    public void postOrderN(TreeNode root) {
        Deque<TreeNode> q = new ArrayDeque<>();
        TreeNode p = root;
        TreeNode last = null;
        System.out.print("postOrderN:");
        while (p!=null || !q.isEmpty()) {
            while (p!=null) {
                q.push(p);
                p = p.left;
            }
            p = q.peek();
            if (p.right==null || p.right==last) {
                //访问节点
                System.out.print(p.val+" ");
                q.pop();
                last = p;
                p = null;
            } else {
                p = p.right;
            }
        }
        System.out.println();
    }

    //求路径和,不一定要根节点开始，叶子节点结束
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        return helper(root,sum) + pathSum(root.left,sum) + pathSum(root.right,sum);
    }
    public int helper(TreeNode root, int sum) {
        if (root==null) return 0;
        sum -= root.val;
        int num = sum==0?1:0;
        num += helper(root.left,sum);
        num += helper(root.right,sum);
        return num;
    }

    /**
     * 二叉树中，和为某一值的路径
     * 求路径和,收集满足路径和的列表
     * DFS，回溯
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum2(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root,sum,new LinkedList<>(),result);
        return result;
    }

    public void dfs(TreeNode root, int sum, LinkedList<Integer> linkedList,List<List<Integer>> result){
        if (root==null) return;
        //把经过的数据放到列表里
        linkedList.add(root.val);
        int target = sum - root.val;
        //当目标值为0，且root为叶子节点，则找到一个符合条件的路径
        if (target==0 && root.left==null && root.right==null) result.add(new LinkedList<>(linkedList));
        dfs(root.left,target,linkedList,result);
        dfs(root.right,target,linkedList,result);
        //回溯时删除当前节点数据
        linkedList.removeLast();
    }

    //检查二叉树平衡性 自上而下
    public boolean isBalance(TreeNode root) {
        if (root == null) return true;
        return Math.abs(depth(root.left) - depth(root.right)) <= 1 && isBalance(root.left) && isBalance(root.right);
    }
    public int depth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(depth(root.left), depth(root.right)) + 1;
    }
    //检查二叉树平衡性 自下而上
    public boolean isBalance2(TreeNode root) {
        if (root == null) return true;
        return depth2(root) >= 0;
    }
    public int depth2(TreeNode root) {
        if (root == null) return 0;
        int left = depth2(root.left);
        int right = depth2(root.right);
        if (left == -1 || right == -1 || Math.abs(left-right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    //判断是否合法二叉搜索树
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MAX_VALUE, Long.MIN_VALUE);
    }
    /**
     * 当前节点值要大于左子树最大值，小于右子树最小值
     * 递归当前节点左子树时，右子树最小值变为当前节点的值
     * 递归当前节点右子树时，左子树最大值变为当前节点的值
     * @param root
     * @param min 右子树最小值
     * @param max 左子树最大值
     * @return
     */
    public boolean isValidBST(TreeNode root, long min, long max) {
        if (root == null) return true;
        //递归 左子树的时候，
        return (root.val > max && root.val < min) && isValidBST(root.left, root.val, max) && isValidBST(root.right, min, root.val);
    }

    /**
     * 合并二叉树
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1==null && root2==null) return null;
        if(root1==null && root2!=null) return root2;
        if(root1!=null && root2==null) return root1;
        root1.left = mergeTrees(root1.left,root2.left);
        root1.right = mergeTrees(root1.right,root2.right);
        root1.val += root2.val;
        return root1;
    }
    //首个公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==null || root==p || root==q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left!=null && right!=null) return root;
        if (left==null && right!=null) return right;
        return left;
    }

    //快排
    public void quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int i=left,j=right,pivot = arr[i];
            while (i<j) {
                while (i<j && pivot < arr[j]) --j;
                if (i<j) arr[i++] = arr[j];
                while (i<j && pivot > arr[i]) ++i;
                if (i<j) arr[j--] = arr[i];
            }
            arr[i] = pivot;
            quickSort(arr, left, i-1);
            quickSort(arr, j+1, right);
        }
    }

    /**
     * 递归方式合并非递减链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        if(l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next,l2);
            return l1;
        } else {
            l2.next = mergeTwoLists(l1,l2.next);
            return l2;
        }
    }
    /**
     * 非递归方式 合并非递减链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode cur = head;
        while(l1!=null && l2!=null) {
            if(l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        cur.next = l1==null?l2:l1;
        return head.next;
    }

    //反转链表
    public ListNode reverse(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    //反转链表 II 反转给定区间的链表
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode cur = new ListNode(0);
        cur.next = head;
        ListNode pre = cur;
        for(int i=1;i<left;++i) {
            pre = pre.next;
        }
        head = pre.next;
        for(int i=left;i<right;++i) {
            ListNode tmp = head.next;
            head.next = tmp.next;
            tmp.next = pre.next;
            pre.next = tmp;
        }
        return cur.next;
    }

    //求链表中间节点(快慢指针)
    public ListNode findMidNode(ListNode head) {
        ListNode faster = head;
        ListNode slower = head;
        while (faster != null && faster.next != null) {
            faster = faster.next.next;
            slower = slower.next;
        }
        return slower;
    }


    //堆排序
    public void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public void fixDown(int[] arr, int len, int idx) {
        while (true) {
            int left=(idx<<1),right=left+1,parent=idx;
            if(left < len && arr[parent] < arr[left]) parent = left;
            if(right < len && arr[parent] < arr[right]) parent = right;
            if(parent==idx) break;
            swap(arr, parent, idx);
            idx = parent;
        }
    }

    public void buildHeap(int[] arr, int len) {
        int heapSize = (int) Math.floor(len/2);
        for (int i=heapSize;i>=0;--i) {
            fixDown(arr, len, i);
        }
    }

    public void heapSort(int[] arr) {
        int len = arr.length;
        buildHeap(arr, len);
        for (int i=len-1;i>=0;--i) {
            swap(arr, i, 0);
            --len;
            fixDown(arr, len, 0);
        }
    }

    //按照斜对角线翻转矩阵 90度
    public void diagonal(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        for (int i=0;i<row;++i) {
            for (int j=0;j<col-i;++j) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[col-j-1][row-i-1];
                matrix[col-j-1][row-i-1] = tmp;
            }
        }
    }
    //按照主对角线翻转矩阵
    public int[][] transpose(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] result = new int[col][row];
        for(int i=0;i<col;++i) {
            for (int j=0;j<row;++j) {
                result[i][j] = matrix[j][i];
            }
        }
        return result;
    }


    //上下翻转矩阵
    public void reverseUp2Down(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i=0;i<row/2;++i) {
            for (int j=0;j<col;++j) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[col-i-1][j];
                matrix[col-i-1][j] = tmp;
            }
        }
    }

    //左右翻转矩阵
    public void reverseLeft2Right(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i=0;i<row;++i) {
            for (int j=0;j<col/2;++j) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][col-j-1];
                matrix[i][col-j-1] = tmp;
            }
        }
    }

    /**
     * 二叉树转链表
     */
    TreeNode biNodeHead = new TreeNode(-1);
    TreeNode biNodePre = null;
    public TreeNode convertBiNode(TreeNode root) {
        if(root==null) return null;
        convertBiNode(root.left);
        //头一个节点
        if(biNodePre==null) biNodeHead.right = root;
        else biNodePre.right = root;
        biNodePre = root;
        root.left = null;
        convertBiNode(root.right);
        return biNodeHead.right;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2,5,1,5,3,8,9,1,2,6,0};
        int[][] matrix = new int[][]{
                {1,2,3},
                {4,5,6},
                {7,8,9}
        };
        Template s = new Template();
        s.quickSort(nums,0,nums.length-1);
        System.out.println(Arrays.toString(nums));
        int[] numsQuickSort = new int[] {2,5,1,5,3,8,9,1,2,6,0};
        s.heapSort(numsQuickSort);
        System.out.println(Arrays.toString(numsQuickSort));
        s.diagonal(matrix);
        System.out.println(Arrays.deepToString(matrix));
        s.reverseUp2Down(matrix);
        System.out.println(Arrays.deepToString(matrix));
        s.reverseLeft2Right(matrix);
        System.out.println(Arrays.deepToString(matrix));

        ListNode head = new ListNode(4);
        head.next = new ListNode(5);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(1);

        ListNode mid = s.findMidNode(head);
        System.out.println(mid.val);

        ListNode last = s.reverse(head);
        while(last!=null) {
            System.out.print(last.val);
            System.out.print(" ");
            last = last.next;
        }

        System.out.println();
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        boolean validBst = s.isValidBST(root);
        System.out.println("isValidBST:"+validBst);

        boolean balance = s.isBalance(root);
        System.out.println("isBalance:"+balance);
        boolean balance2 = s.isBalance2(root);
        System.out.println("isBalance2:"+balance2);

        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(5);
        root2.left.left = new TreeNode(6);
        root2.left.right = new TreeNode(2);
        root2.left.right.left = new TreeNode(7);
        root2.left.right.right = new TreeNode(4);

        root2.right = new TreeNode(1);
        root2.right.left = new TreeNode(0);
        root2.right.right = new TreeNode(8);
        TreeNode p = root2.left;
        TreeNode q = root2.right.right;
        TreeNode r = s.lowestCommonAncestor(root2, p, q);
        System.out.println("lowestCommonAncestor:"+r==null?"null":r.val);

        s.preOrderN(root2);
        s.inOrderN(root2);
        s.postOrderN(root2);

        System.out.println();
        int num = s.pathSum(root2, 11);
        System.out.println("pathSum:"+num);
        List<List<Integer>> list =s.pathSum2(root2, 14);
        System.out.println("pathSum2:"+list.toString());

        String[] permutationWithRepeat = s.permutationWithRepeat("eqq");
        System.out.println("permutationWithRepeat:"+Arrays.toString(permutationWithRepeat));
        String[] permutation = s.permutation("abc");
        System.out.println("permutation:"+Arrays.toString(permutation));

        List<List<String>> solveNQueens = s.solveNQueens(4);
        System.out.println("N皇后问题："+solveNQueens.toString());

        int changeNum = s.change(4, new int[]{1,2,5});
        System.out.println("零钱置换："+changeNum);

        List<String> bracket = s.generateParenthesis(3);
        System.out.println("括号；"+bracket.toString());

        int rob = s.rob(new int[]{2,1,4,5,2,7,2});
        System.out.println("打家劫舍1:"+rob);

        int robIII = s.robIII(root2);
        System.out.println("打家劫舍3:"+robIII);

        List<List<Integer>> subsets = s.subsets(new int[]{1,2,3});
        System.out.println("subsets:"+subsets.toString());

        List<List<Integer>> pathWithObstacles = s.pathWithObstacles(new int[][]{
                {0,0,0},
                {0,1,0},
                {0,0,0}
        });
        System.out.println("pathWithObstacles:"+pathWithObstacles.toString());
    }
}

class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int val) {
        this.val = val;
        this.next = null;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
