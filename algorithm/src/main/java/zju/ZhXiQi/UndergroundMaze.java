package zju.ZhXiQi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 最短路径题型
 */
public class UndergroundMaze {
    static int n;
    static int m;
    static int P;
    static int maxRemainEnergy = 0;
    static int[][] map;
    static String path = "";
    static boolean flag = false;
    static LinkedList<String> linkedList = new LinkedList<>();
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        P = in.nextInt();
        map = new int[n][m];
        for (int i=0;i<n;i++){
            for (int j=0;j<m;j++){
                map[i][j] = in.nextInt();
            }
        }
        //处理
        runMap(0,0,P);

        //输出
        if (!flag) System.out.println("Can not escape!");
        else System.out.println(path);
    }

    private static void runMap(int x, int y, int energy) {
        if (energy < 0 || x<0 || y<0 || x>=n || y>=m || map[x][y]!=1)return;
        else {
            linkedList.offer("[" + x + "," + y + "]");
            map[x][y] = 0;
            if (x == 0 && y == m-1){
                if (energy >= maxRemainEnergy){
                    maxRemainEnergy = energy;
                    updatePath();
                }
                map[x][y] = 1;
                linkedList.removeLast();
                flag = true;
                return;
            }
            runMap(x,y+1,energy-1);
            runMap(x+1,y,energy);
            runMap(x-1,y,energy-3);
            runMap(x,y-1,energy-1);
            map[x][y] = 1;
            linkedList.removeLast();
        }
    }

    private static void updatePath() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = linkedList.iterator();
        while (iterator.hasNext()){
            sb.append(iterator.next()+",");
        }
        if (sb.length()>0)sb.deleteCharAt(sb.length() -1);
        path = sb.toString();
    }
}
