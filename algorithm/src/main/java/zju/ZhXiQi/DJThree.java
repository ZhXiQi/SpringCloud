package zju.ZhXiQi;

import java.util.Scanner;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-08-06 20:25
 */
public class DJThree {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int V = in.nextInt();
        int N = in.nextInt();

        int value[] = new int[N];
        for (int i=0;i<N;++i){
            value[i] = in.nextInt();
        }

        int M = in.nextInt();
        int favo[] = new int[M];
        for (int j=0;j<M;++j){
            favo[j] = in.nextInt();
        }

        int result = 0;
        cutProfit(V, value);
    }

    public static int cutProfit(int len,int[] price) {
        int sum=0;
//        int[] price= {0,1,5,8,9,10,17,17,20,24,30};//长度价格表
        if(len==0)
            return 0;
        for(int i=1;i<=len && i<price.length;i++)
        {
            int tmpsum=price[i]+cutProfit(len-i,price);//将len长度的钢条切割成两段，递归求解第二段
            if(tmpsum>sum)
                sum=tmpsum;
        }
        return sum;
    }
}


