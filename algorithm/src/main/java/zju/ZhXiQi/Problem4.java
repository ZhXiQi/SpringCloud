package zju.ZhXiQi;

import java.util.ArrayList;
import java.util.Scanner;

//最大序列和问题(动态规划)
public class Problem4 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> dp = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            int n = in.nextInt();
            for (int i=0;i<n;i++){
                list.add(in.nextInt());
            }
            dp.add(list.get(0));
            for (int i=1;i<list.size();i++){
                //状态方程
                dp.add(Math.max(dp.get(i-1)+list.get(i),list.get(i)));
            }
            int k=0;
            for (int i=0;i<dp.size();i++){
                if (dp.get(k)<dp.get(i))k=i;
            }
            System.out.println(dp.get(k));
        }
    }
}
