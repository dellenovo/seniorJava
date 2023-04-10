package com.lifei.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DLLifeiLock implements Lock{
    //the node chain of DLLifeiLock
    private static final String ZK_PATH = "/DLLifei/lock";

    // lock prefix
    private static final String LOCK_PREFIX = ZK_PATH + "/a";

    // lock enqueue timeout
    private static final long WAIT_TIME = 60000;

    // the current node
    private String locked_path = null;

    private String prior_path = null;

    private String locked_short_path = null;

    final AtomicInteger lockCount = new AtomicInteger(0);

    // zk client
    private CuratorFramework client = null;

    // local thread
    private Thread thread;

    public DLLifeiLock() {
        ZKClient.instance.init();
        if (!ZKClient.instance.isNodeExist(ZK_PATH)) {
            ZKClient.instance.createNode(ZK_PATH, null);
        }
        client = ZKClient.instance.getClient();
    }
    @Override
    public boolean lock() throws InterruptedException {
        synchronized (this) {
            if (lockCount.get() == 0) {
                // the first time to get the lock
                thread = Thread.currentThread();
                lockCount.incrementAndGet();
                try {
                    boolean locked = false;

                    // 1. try to acquire lock
                    locked = tryLock();

                    if (locked) {
                        return true;
                    }

                    // 2. if the thread fails to acquire the lock, wait.
                    while (!locked) {
                        await();
                        // get the list of waiting node
                        List<String> waiters = getWaiters();
                        // determine whether succeed in locking
                        if (checkLocked(waiters)) {
                            locked = true;
                        }
                    }

                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // release the lock and decrease the count
                    unlock();
                }
            } else {
                // the second time to get the lock
                if (thread != Thread.currentThread()) {
//                    return false;
                    try {
                        boolean locked = false;

                        // 1. try to acquire lock
                        locked = tryLock();

                        if (locked) {
                            return true;
                        }

                        // 2. if the thread fails to acquire the lock, wait.
                        while (!locked) {
                            await();
                            // get the list of waiting node
                            List<String> waiters = getWaiters();
                            // determine whether succeed in locking
                            if (checkLocked(waiters)) {
                                locked = true;
                            }
                        }

                        return true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // release the lock and decrease the count
                        unlock();
                    }
                }
                lockCount.incrementAndGet();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean unlock() {
        // only the locking thread can unlock
        if (!thread.equals(Thread.currentThread())) {
            return false;
        }

        // decrease the count
        int newLockCount = lockCount.decrementAndGet();

        // count must not be lower than 0
        if (newLockCount < 0) {
            throw new RuntimeException("Reentrant lifei lock count must be greater than 0.");
        }

        if (newLockCount > 0) {
            return true;
        }

        // only when lock count has decreased to zero, then we can really unlock and delete the ZK node
        try {
            if (ZKClient.instance.isNodeExist(locked_path)) {
                client.delete().forPath(locked_path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void await() throws Exception {
        if (prior_path == null) {
            throw new Exception("prior path error");
        }

        final CountDownLatch latch = new CountDownLatch(1);

        // add a watcher for the prior node.
        Watcher watcher = watchedEvent -> {
            System.out.println("watched event = " + watchedEvent);
            System.out.println("watched event deleted");
            latch.countDown();
        };

        client.getData().usingWatcher(watcher).forPath(prior_path);
        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }

    private boolean tryLock() throws Exception {
        // todo: 由于这里locked_path是线程不安全的，所以需要每个线程创建一个新的DLLifeiLock实例，需要想办法优化
        locked_path = ZKClient.instance.createEphemeralSeqNode(LOCK_PREFIX);

        List<String> waiters = getWaiters();

        if (locked_path == null) {
            throw new Exception("zk error");
        }

        locked_short_path = getShortPath(locked_path);

        if (checkLocked(waiters)) {
            return true;
        }

        int index = Collections.binarySearch(waiters, locked_short_path);

        if (index < 0) {
            throw new RuntimeException("Node not found!");
        }

        prior_path = ZK_PATH + "/" + waiters.get(index - 1);

        return false;
    }

    private String getShortPath(String locked_path) {
        int index = locked_path.lastIndexOf(ZK_PATH + "/");

        if (index >= 0 ) {
            index += ZK_PATH.length() + 1;
            return index <= locked_path.length() ? locked_path.substring(index) : "";
        }

        return null;
    }

    private boolean checkLocked(List<String> waiters) {
        Collections.sort(waiters);
        if (locked_short_path.equals(waiters.get(0))) {
            System.out.println("successfully got lock, the node is " + locked_short_path);
            return true;
        }

        return false;
    }

    protected List<String> getWaiters() {
        List<String> children = null;
        try {
            children = client.getChildren().forPath(ZK_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return children;
    }
}
