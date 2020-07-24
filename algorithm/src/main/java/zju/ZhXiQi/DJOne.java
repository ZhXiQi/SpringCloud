package zju.ZhXiQi;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-08-06 19:26
 */
public class DJOne {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        int N;
        int X;



        for (int i=0;i<T;++i){
            int timeSum = 0;
            int sum = 0;
            Map<Integer,Integer> map = new HashMap<>();

            List<Integer> list = new ArrayList<>();
            N = in.nextInt();
            X = in.nextInt();
            for (int j=0;j<N;++j){
                int temN = in.nextInt();
                int temX = in.nextInt();
                list.add(temN);
                map.put(temN,temX);
            }
            list.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2-o1;
                }
            });


            for(int k=0;k<N;++k){
                Integer integer = list.get(k);
                Integer timeValue = map.get(integer);
                if (timeSum + timeValue <= X){
                    sum += integer;
                    timeSum += timeValue;
                }
            }
            System.out.println(sum);

        }
    }
}
