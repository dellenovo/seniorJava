import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo14ConditionDemo {
    public static void main(String[] args) {
        ShareData shareDate = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareDate.wash();
            }
        }, "tony-雄雄").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareDate.cut();
            }
        }, "tony-超超").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareDate.blow();
            }
        }, "tony-麦麦").start();
    }
}

class ShareData {
    private volatile int number = 1;

    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void wash() {
        lock.lock();
        try {
            if (number != 1) {
                c1.await();
            }

            System.out.println(Thread.currentThread().getName() + "-洗头");
            number = 2;
            c2.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void cut() {
        lock.lock();
        try {
            if (number != 2) {
                c2.await();
            }

            System.out.println(Thread.currentThread().getName() + "-理发");
            number = 3;
            c3.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void blow() {
        lock.lock();
        try {
            if (number != 3) {
                c3.await();
            }

            System.out.println(Thread.currentThread().getName() + "-吹干");
            number = 1;
            c1.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
