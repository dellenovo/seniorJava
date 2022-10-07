public class Demo06Volatile {
    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(demo);
            t.start();
        }

        Thread.sleep(1000);
        System.out.println("count = " + demo.count);
    }

    static class VolatileDemo implements Runnable {
        public volatile int count;

        public void run() {
            addCount();
        }

        public synchronized void addCount() {
            for (int i = 0; i < 10000; i++) {
                count++;
            }
        }
    }
}
