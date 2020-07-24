package zju.ZhXiQi;

import java.util.Scanner;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/2/27 15:06
 */
public class HWTwo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int day = in.nextInt();
        int[] temperatures = new int[day];
        int[] res = new int[day];
        for (int i=0; i<day; ++i){
            temperatures[i] = in.nextInt();
        }
        for (int i=0; i<day; ++i){
            for (int j=i+1;j<day;++j){
                if (temperatures[j] > temperatures[i]){
                    res[i] = j-i;
                    break;
                }
            }
        }
        for (int i=0;i<res.length;++i){
            System.out.println(res[i]);
        }




    }
}
