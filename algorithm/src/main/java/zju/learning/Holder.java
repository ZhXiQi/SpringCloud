package zju.learning;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/3/4 16:07
 */
public class Holder {
    private int n;

    public Holder(int n) {
        this.n = n;
        try {
            TimeUnit.MICROSECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void assertSanity(){
        if (n != n){
            throw new AssertionError("This statement is false.");
        }
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+8"));
        LocalDateTime endDate = now.withHour(20).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime startDate = endDate.minusDays(1);
        long l = startDate.toEpochSecond(ZoneOffset.UTC);

        String yyyyMMdd1 = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(yyyyMMdd1);
        LocalDate yyyyMMdd = LocalDate.parse("20190330", DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(yyyyMMdd);
        System.out.println(l);
        int i = startDate.get(ChronoField.NANO_OF_SECOND);
        System.out.println(i);
        System.out.println(startDate.toString());
        System.out.println(endDate.toString());
    }
}
