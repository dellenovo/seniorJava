import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Demo11CountDownLatch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println(Thread.currentThread().getName() + "\t上完班，离开公司");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName() + "\t卷王最后关灯走人");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "7").start();
    }
}
