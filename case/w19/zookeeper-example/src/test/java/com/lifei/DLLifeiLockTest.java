package com.lifei;

import com.lifei.distributedlock.DLLifeiLock;
import com.lifei.distributedlock.FutureTaskScheduler;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

public class DLLifeiLockTest {
    int count = 0;

    @Test
    public void testLock() throws InterruptedException, IOException {

//        for (int i = 0; i < 100; i++) {
//            FutureTaskScheduler.add(() -> {
//
//                lock.lock();
//
//                for (int j = 0; j < 10; j++) {
//                    count++;
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println("count ========= " + count);
//
//                lock.unlock();
//            });
//        }

        DLLifeiLock lock = new DLLifeiLock();

        Thread[] ts = new Thread[2];
        for (int i = 0; i < ts.length; i++) {
            Runnable r = () -> {
                // create the lock
//                DLLifeiLock lock = new DLLifeiLock();
                try {
                    lock.lock();
                    Thread.sleep((long)(Math.random() * 10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                lock.unlock();
            };
            ts[i] = new Thread(r, "lifei" + i);
            ts[i].start();
        }

        for (int i = 0; i < ts.length; i++) {
            ts[i].join();
        }

        System.out.println("count ========= " + count);
//        System.in.read();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
