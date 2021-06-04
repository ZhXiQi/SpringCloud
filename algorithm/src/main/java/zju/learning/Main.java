package zju.learning;

import java.lang.reflect.Field;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/3/4 16:08
 */
public class Main {

    public Holder holder;

    public void initialize(int n){
        holder = new Holder(n);
    }

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        Main main = new Main();
        main.testA();

        main.holder = new Holder(1);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                    main.initialize((int) Thread.currentThread().getId());
//                    main.holder.assertSanity();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
                main.initialize((int) Thread.currentThread().getId());
                main.holder.assertSanity();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId());
//                main.initialize((int) Thread.currentThread().getId());
                main.holder.assertSanity();
            }
        });
        t1.start();
//        TimeUnit.SECONDS.sleep(1);
        t2.start();
        t3.start();

    }

    public void testA() throws NoSuchFieldException, IllegalAccessException {
        Class<?> declaredClass = Integer.class.getDeclaredClasses()[0];
        Field cache = declaredClass.getDeclaredField("cache");
        cache.setAccessible(true);
        Integer[] integers = (Integer[]) cache.get(cache);
        integers[130] = integers[129];
        integers[131] = integers[129];
        Integer a = 1;
        if (a == (Integer) 1 && a == (Integer)2 && a == (Integer) 3) {
            System.out.println("success");
        }
    }
}
