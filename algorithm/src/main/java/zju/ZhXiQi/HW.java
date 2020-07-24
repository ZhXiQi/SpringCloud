package zju.ZhXiQi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/2/23 16:16
 */
public class HW {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.LENIENT);

        LocalDate parse = LocalDate.parse(s, dateTimeFormatter);
        LocalTime localTime = LocalTime.now();
        System.out.println(parse);

//        LocalDateTime.parse(s,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        sdf.setLenient(false);

        Date nowDay = null;
        Date originDate = null;
        try {
            nowDay = sdf.parse(s);
            originDate = sdf.parse("1990-01-01");
        } catch (ParseException e) {
            System.out.println("Invalid input");
            return;
        }
        if (nowDay.before(originDate)){
            System.out.println("Invalid input");
            return;
        }

        long l = (nowDay.getTime() - originDate.getTime()) / (24 * 60 * 60 * 1000);
        long res = l % 5;
        if (res<3){
            System.out.println("He is working");
        }else {
            System.out.println("He is having a rest");
        }

    }
    private boolean isSpecialChar(String str){
        String regEx = "[ _`~!@#$%^&*()+=|{}:;，。、\\[\\]<>《》/?￥……（）——「」【】";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }
}
