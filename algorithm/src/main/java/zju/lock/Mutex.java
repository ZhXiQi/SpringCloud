package zju.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhXiQi
 * @Title:
 * @date 2020/4/23 11:12
 */
public class Mutex implements Lock {

    private Sync sync;

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        sync.tryAcquire(1);
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        AtomicLong atomicLong = new AtomicLong(123);

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            int state = this.getState();
            if (state==0){
                this.compareAndSetState(0,arg);
                this.setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            return super.tryRelease(arg);
        }
    }
}
