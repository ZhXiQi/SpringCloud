package design;

import java.text.MessageFormat;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhXiQi
 * @Title:  线程1打印0，线程2打印1，线程3打印2，线程1打印3。。。以此类推
 * @date 2021/3/9 14:48
 */
public class Design {

    private static final String FLAG_THREAD_1 = "1";
    private static final String FLAG_THREAD_2 = "2";
    private static final String FLAG_THREAD_3 = "3";
    private static final String FLAG_THREAD_4 = "4";
    private static volatile int initValue = 1;
    private static volatile int maxNum = 30;
    private static volatile boolean terminal = false;

    static class FairRunnable implements Runnable {

        private ReentrantLock lock;
        private Condition condition1;
        private Condition condition2;
        private Condition condition3;
        private Condition condition4;

        public FairRunnable(ReentrantLock lock, Condition condition1, Condition condition2, Condition condition3, Condition condition4) {
            this.lock = lock;
            this.condition1 = condition1;
            this.condition2 = condition2;
            this.condition3 = condition3;
            this.condition4 = condition4;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                while (!terminal) {
                    Thread.sleep(new Random().nextInt(100) + 200);
                    String id = Thread.currentThread().getName();
                    String msg = MessageFormat.format("编号{0}卖出了{1}的票",id,initValue);
                    switch (Thread.currentThread().getName()) {
                        case FLAG_THREAD_1:
                            System.out.println(msg);
                            if(initValue>maxNum) terminal = true;
                            if (terminal) break;
                            //唤起线程2，自身线程挂起阻塞
                            condition2.signal();
                            condition1.await();
                            break;
                        case FLAG_THREAD_2:
                            if(initValue%2 == 0) System.out.println(msg);
                            if(initValue>maxNum) terminal = true;
                            if (terminal) break;
                            condition3.signal();
                            condition2.await();
                            break;
                        case FLAG_THREAD_3:
                            System.out.println(msg);
                            if(initValue>maxNum) terminal = true;
                            if (terminal) break;
                            condition4.signal();
                            condition3.await();
                            break;
                        case FLAG_THREAD_4:
                            System.out.println(msg);
                            if(initValue>maxNum) terminal = true;
                            if (terminal) break;
                            condition1.signal();
                            condition4.await();
                            break;
                        default:break;
                    }
                    ++initValue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } finally {
                lock.unlock();
            }
        }
    }

    static class FairRunnable2 implements Runnable {

        private ReentrantLock lock;
        private Condition condition;

        public FairRunnable2(ReentrantLock lock, Condition condition) {
            this.lock = lock;
            this.condition = condition;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                while (initValue<=maxNum) {
                    Thread.sleep(new Random().nextInt(100) + 200);
                    String id = Thread.currentThread().getName();
                    String msg = MessageFormat.format("编号{0}卖出了编号{1}的票",id,initValue);
                    switch (Thread.currentThread().getName()) {
                        case FLAG_THREAD_1:
                            System.out.println(msg);
                            break;
                        case FLAG_THREAD_2:
                            if(initValue%2 == 0) System.out.println(msg);
                            break;
                        case FLAG_THREAD_3:
                            System.out.println(msg);
                            break;
                        case FLAG_THREAD_4:
                            System.out.println(msg);
                            break;
                        default:break;
                    }
                    ++initValue;
                    condition.signalAll();
                    condition.await();
                }
                terminal = true;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 线程交替打印，不实用锁实现
     */
    static class FairRunnable3 implements Runnable {
        volatile static int flag = 1;
        final Object object = new Object();

        @Override
        public void run() {
            while (!terminal) {
                synchronized (object) {
                    String name = Thread.currentThread().getName();
                    switch (name) {
                        case FLAG_THREAD_1:
                            while (flag != 1) {
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case FLAG_THREAD_2:
                            while (flag != 2) {
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case FLAG_THREAD_3:
                            while (flag != 3) {
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case FLAG_THREAD_4:
                            while (flag != 4) {
                                try {
                                    object.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        default:break;
                    }
                    //线程任务开始执行
                    System.out.println(name + "开始执行;" + "打印：" + initValue++);
                    if (initValue >= maxNum) terminal = true;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    switch (name) {
                        case FLAG_THREAD_1:
                            flag = 2;
                            break;
                        case FLAG_THREAD_2:
                            flag = 3;
                            break;
                        case FLAG_THREAD_3:
                            flag = 4;
                            break;
                        case FLAG_THREAD_4:
                            flag = 1;
                            break;
                        default:break;
                    }
                    //唤醒所有线程
                    object.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(true);
        Condition condition1 = lock.newCondition();
//        Condition condition2 = lock.newCondition();
//        Condition condition3 = lock.newCondition();
//        Condition condition4 = lock.newCondition();
        FairRunnable2 fairRunnable = new FairRunnable2(lock, condition1);
        Thread t1 = new Thread(fairRunnable, FLAG_THREAD_1);
        Thread t2 = new Thread(fairRunnable, FLAG_THREAD_2);
        Thread t3 = new Thread(fairRunnable, FLAG_THREAD_3);
        Thread t4 = new Thread(fairRunnable, FLAG_THREAD_4);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
//
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (initValue<maxNum) {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100) + 200);
                        long id = Thread.currentThread().getId();
                        System.out.println("编号" + id + "卖出了编号" + initValue++ + "的票");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
        Thread threa2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (initValue<maxNum) {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100) + 200);
                        long id = Thread.currentThread().getId();
                        if(initValue%2 == 0) System.out.println("编号" + id + "卖出了编号" + initValue++ + "的票");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

        Thread threa3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (initValue < maxNum) {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100) + 200);
                        long id = Thread.currentThread().getId();
                        System.out.println("编号" + id + "卖出了编号" + initValue++ + "的票");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
        Thread threa4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (initValue<maxNum) {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100) + 200);
                        long id = Thread.currentThread().getId();
                        System.out.println("编号" + id + "卖出了编号" + initValue++ + "的票");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        });

//        thread.start();
//        threa2.start();
//        threa3.start();
//        threa4.start();



        FairRunnable3 fairRunnable3 = new FairRunnable3();
//        new Thread(fairRunnable3,FLAG_THREAD_1).start();
//        new Thread(fairRunnable3,FLAG_THREAD_2).start();
//        new Thread(fairRunnable3,FLAG_THREAD_3).start();
//        new Thread(fairRunnable3,FLAG_THREAD_4).start();

        List<Object> objects = new LinkedList<>();

        while (!terminal) {
            TimeUnit.SECONDS.sleep(1);
        }
        System.exit(0);
    }

    /**
     * 模拟栈
     * 最小栈
     */
    class MinStack {
        Deque<Integer> xStack;
        Deque<Integer> minStack;
        /** initialize your data structure here. */
        public MinStack() {
            xStack = new LinkedList<Integer>();
            minStack = new LinkedList<Integer>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int x) {
            xStack.push(x);
            minStack.push(Math.min(minStack.peek(),x));
        }


        public void pop() {
            xStack.pop();
            minStack.pop();
        }

        public int top() {
            return xStack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /**
     * 乱序输出数组
     * @param arr
     */
    public void shuffle(int[] arr) {
        int len = arr.length;
        for(int i=0;i<len;++i) {
            int random = (int) (Math.random()*(len-1));
            int tmp = arr[len-1-i];
            arr[len-1-i] = arr[random];
            arr[random] = tmp;
        }
    }

}
