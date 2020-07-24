package zju.ZhXiQi;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Problem1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Map<Integer,Integer> map = new HashMap<>();
        while(in.hasNext()){
            String str = in.nextLine();
            String[] split = str.split(" ");
            int n = split.length;

//            System.out.println(n);

            for (int i=0;i<n;i++){
                int num = Integer.parseInt(split[i]);
                if (map.containsKey(num)){
                    map.put(num,map.get(num)+1);
                }else {
                    map.put(num,1);
                }
            }

            for (Map.Entry<Integer,Integer> i:map.entrySet()){
                if (i.getValue() >= n/2){
                    System.out.print(i.getKey());
                }
            }
        }
    }
}
