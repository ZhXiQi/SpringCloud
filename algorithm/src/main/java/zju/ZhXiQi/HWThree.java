package zju.ZhXiQi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhXiQi
 * @Title: 飞机绕地球一圈，飞机装满邮箱只能绕半圈，有多架飞机，起飞随时可以起飞，可以互相加油，必须有一辆达到终点，其他的安全返回，一共需要多少飞机？
 * @date 2020/2/28 19:27
 */
public class HWThree {
    public static void main(String[] args) {
        //油箱需要 2 个才能绕一圈
        //烧完一半油只飞完了 1/4；烧完1/4只飞完了 1/8
        int oriFly = 2; //A、B两辆
        //A烧完1/4油的时候，B给他加回1/4，此时A满油，飞完了
        double v = 0.3;
        double v1 = 0.2;
        System.out.println(v1-v);
        List<Integer> widgets = new ArrayList<>();
        int sum = widgets.stream().filter(w -> w.intValue()==1).mapToInt(w -> w.intValue()).sum();

    }
}
