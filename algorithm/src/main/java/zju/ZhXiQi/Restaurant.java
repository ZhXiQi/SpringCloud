package zju.ZhXiQi;

/*
链接：https://www.nowcoder.com/practice/d2cced737eb54a3aa550f53bb3cc19d0?tpId=85&tqId=29859&tPage=2&rp=2&ru=%2Fta%2F2017test&qru=%2Fta%2F2017test%2Fquestion-ranking
        来源：牛客网
思路：优先选消费额度大的客人安排就餐
对客人按照消费额度排序（大->小）
对桌子按照容量排序（大->小）
选取当前消费额度最大客人：
1.如果没有桌子可用，结束；
2.如果人数过多无法安排，跳过；
3.如果可安排，则找到最合适的桌位，可就餐的桌位中容量最小的；
  3.1向这批客人收费；
  3.2将桌子从可用资源中删除；
直到没有桌子可用或所有客人全部安排
*/

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Restaurant {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNext()){
            int n = in.nextInt();   //n张桌子
            int m = in.nextInt();   //m批客人
            int[] desk = new int[n];    //桌子数组
            for (int i=0;i<n;i++){
                desk[i] = in.nextInt(); //每个桌子可容纳的最大人数a
            }
            Arrays.sort(desk);  //桌子容纳量从小到大排序
            PriorityQueue<Customer> queue = new PriorityQueue<Customer>();  //将客人按消费额降序加入优先级队列
            for (int i=0;i<m;i++){
                int b = in.nextInt();   //b 人数
                int c = in.nextInt();   //c 预计消费金额
                if (b <= desk[n -1])queue.add(new Customer(b,c));   //如果人数小于桌子最大容纳量,加入队列
            }
            boolean[] visited = new boolean[n]; //记录桌子是否被占用
            long sum = 0;   //记录总盈利
            int count = 0;  //记录已使用的桌子数
            while (!queue.isEmpty()){
                Customer customer = queue.poll();
                for (int i=0;i<n;i++){  //为客人分配桌子
                    if (customer.peopleCount <= desk[i] && !visited[i]){
                        sum += customer.moneyCount;
                        visited[i] = true;
                        count++;
                        break;
                    }
                }
                if (count == n)break;
            }
            System.out.println(sum);
        }
    }
    static class Customer implements Comparable<Customer>{
        private int peopleCount;
        private int moneyCount;

        public Customer(int peopleCount,int moneyCount){
            this.peopleCount = peopleCount;
            this.moneyCount = moneyCount;
        }
        //按消费金额排序
        @Override
        public int compareTo(Customer o) {
            if (o.moneyCount > this.moneyCount) return 1;
            else if (o.moneyCount < this.moneyCount) return -1;
            return 0;
        }
    }
}
