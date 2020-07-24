package zju.ZhXiQi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Problem3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Character> list = new ArrayList<>();
        List<Character> list1 = new ArrayList<>();
        while (in.hasNext()){
            String str1 = in.nextLine();
            String str2 = in.nextLine();

            char[] chars = str1.trim().toCharArray();
            char[] chars1 = str2.trim().toCharArray();

            for (int i=0;i<chars.length;i++){
                list.add(chars[i]);
            }
            for (int i=0;i<chars1.length;i++){
                list1.add(chars1[i]);
            }
            for (Character c:list){
                if (!list1.contains(c)){
                    System.out.print(c);
                }
            }
        }
    }
}
