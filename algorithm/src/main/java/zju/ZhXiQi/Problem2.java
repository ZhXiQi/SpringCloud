package zju.ZhXiQi;

import java.util.Scanner;

public class Problem2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while(in.hasNext()){
            String str = in.nextLine();
            String[] split = str.split(" ");
            int len = split.length;


            for (int i=len-1;i>=0;i--){
                System.out.print(split[i]);
                if (i>0) System.out.print(" ");
            }
        }
    }
}
