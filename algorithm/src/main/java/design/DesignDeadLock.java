package design;

import java.util.concurrent.TimeUnit;

/**
 * @author ZhXiQi
 * @Title: 设计死锁程序
 * @date 2021/4/30 14:50
 */
public class DesignDeadLock {

    public static void main(String[] args) {

        DeadLockObj deadLockObj1 = new DeadLockObj();
        DeadLockObj deadLockObj2 = new DeadLockObj();

        new Thread(new Runnable() {
            @Override
            public void run() {
                deadLockObj1.setValue(1);
                try {
                    deadLockObj1.method();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                deadLockObj2.setValue(2);
                try {
                    deadLockObj2.method();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

    }
}

class DeadLockObj {
    private int value;

    private static Object o1 = new Object();
    private static Object o2 = new Object();

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public synchronized void method() throws InterruptedException {
        switch (value) {
            case 1:
                synchronized (o1) {
                    String name = Thread.currentThread().getName();
                    System.out.println("线程"+name+"获取到o1");
                    TimeUnit.SECONDS.sleep(5);
                    synchronized (o2) {
                        System.out.println("线程"+name+"尝试获取o2");
                    }
                }
                break;
            default:
                synchronized (o2) {
                    String name = Thread.currentThread().getName();
                    System.out.println("线程"+name+"获取到o2");
                    TimeUnit.SECONDS.sleep(1);
                    synchronized (o1) {
                        System.out.println("线程"+name+"尝试获取o1");
                    }
                }
                break;
        }
    }
}
