package zju.ZhXiQi;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2019-08-06 20:12
 */
public class DJTwo {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        while (in.hasNext()){
            String s = in.nextLine();
            String[] s1 = s.split(" ");
            int N = Integer.parseInt(s1[0]);
            int M = Integer.parseInt(s1[1]);

            Map<String,String> map = new HashMap<>();
            for (int i=0;i<N;++i){
                String str = in.nextLine();
                String[] split = str.split(" ");
                map.put(split[0],split[1]);
            }
            for (int j=0;j<M;++j){
                System.out.println(map.get(in.nextLine()));
            }
        }
    }
}
