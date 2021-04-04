import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2021/3/31 11:20
 */
public class Didi {

    //用户进线咨询客户问题
    //48个客户
    //同一个用户每次为他服务的都是同一个客户
    //每一个客服只能服务三个客户，超过其他需排队

    private final static String T1 = "1";
    private final static String T2 = "2";
    private final static String T3 = "3";

    static Map<Integer, Integer> customer2Service = new HashMap<>();
    static Map<Integer, Deque<Integer>> service2Task = new HashMap<>();

    /**
     * 接线
     * @param customerId
     */
    public static void holdOn(Integer customerId, Integer serviceId) {
        //当前客服
        Integer cus = customer2Service.get(customerId);
        if(cus==null) {
            //说明第一次进来咨询
            customer2Service.put(customerId,serviceId);
            Deque<Integer> task = service2Task.get(serviceId);
            if(task==null || task.isEmpty()) {
                //第一次接线
                task = new ArrayDeque<>();
            }
            task.addLast(customerId);
            service2Task.put(serviceId,task);
        } else {
            //指定客服
            Deque<Integer> task = service2Task.get(cus);
            task.addLast(customerId);
        }
    }

    /**
     * 服务
     * @param serviceId
     */
    public static void service(Integer serviceId) throws InterruptedException {
        Deque<Integer> task = service2Task.get(serviceId);
        if (task==null || task.isEmpty()) return;
        for(int i=0;i<3;++i) {
            //模拟服务
            if (!task.isEmpty()) System.out.println("当前客户ID："+serviceId+"接待客户："+task.pop());
        }
        System.out.println();
        TimeUnit.SECONDS.sleep(1);
    }

    static class TaskClass implements Runnable {
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            Integer serviceId = Integer.valueOf(name);
            Deque<Integer> integers = service2Task.get(serviceId);
            while (integers!=null && !integers.isEmpty()) {
                for(int i=0;i<3;++i) {
                    if (!integers.isEmpty()) {
                        try {
                            service(serviceId);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Didi didi = new Didi();
        Random r = new Random();
        for (int i=0;i<48;++i) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    didi.holdOn((int) Thread.currentThread().getId(),r.nextInt(3)+1);
                }
            }).start();
        }

        TaskClass taskClass = new TaskClass();
        new Thread(taskClass,T1).start();
        new Thread(taskClass,T2).start();
        new Thread(taskClass,T3).start();

    }
}
