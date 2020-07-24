package zju.learning;

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

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();

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
}
