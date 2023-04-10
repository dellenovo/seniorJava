package com.lifei.distributedlock;

public interface Lock {
    boolean lock() throws Exception;

    boolean unlock();
}
