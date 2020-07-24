package zju.ZhXiQi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class leetCode {
    /*public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()){
            String str = in.nextLine();
            char[] arr = str.toCharArray();
            int len = arr.length;
            int start = 0;
            int count = 0;
            int indexStart = 0;
            int MaxLen = 0;
            for (int i=0;i<len;i++){
                if ('0' <= arr[i] && '9' >= arr[i]){
                    if (start==0)start=i;
                    count++;
                }else {
                    if (MaxLen < count){
                        MaxLen=count;
                        indexStart = start;
                    }
                    start=0;
                    count=0;
                }
            }
            if (MaxLen < count){
                MaxLen=count;
                indexStart = start;
            }

            System.out.println(str.substring(indexStart,indexStart+MaxLen));
        }
    }*/
    public static void main(String[] args) {
        System.out.println(new Date().toString());
        Scanner in = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();

        while (in.hasNext()){
            String str = in.nextLine();
            String[] numChar = str.split(" ");
            for (int i=0;i<numChar.length;i++){
                list.add(Integer.parseInt(numChar[i]));
            }
            int k = list.remove(list.size()-1);


            list.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
//                    return o1 >= o2?o2:o1;
                    if(o1 > o2) {
                        return 1;
                    } else if(o1 < o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
            if (k>list.size()){
                int num = list.size();
                for (int i=0;i<num;i++){
                    System.out.print(list.get(i));
                    if (i<num-1) System.out.print(" ");
                }
            }else {
                for (int i=0;i<k;i++){
                    System.out.print(list.get(i));
                    if (i<k-1) System.out.print(" ");
                }
            }
        }
    }
}
