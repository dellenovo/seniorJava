public class Demo05Synchronized {
    public synchronized void increase() {
//        System.out.println("synchronized 方法");
    }

    public void syncBlock() {
        synchronized (this) {
//            System.out.println("synchronized 块");
        }
    }
}
