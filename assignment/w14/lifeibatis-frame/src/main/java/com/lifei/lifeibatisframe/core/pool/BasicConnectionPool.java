package com.lifei.lifeibatisframe.core.pool;

import com.lifei.lifeibatisframe.core.entity.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class BasicConnectionPool implements ConnectionPool {
    private int corePoolSize = 10;
    private int maxPoolSize = 12;

    private volatile int availableConnSize = corePoolSize;
    private Configuration configuration;

    private List<Connection> pool = new ArrayList<>();

    private List<AtomicBoolean> locks = new ArrayList<>();

    private static BasicConnectionPool instance;

    public static BasicConnectionPool getInstance(Configuration configuration) {
        if (instance == null) {
            synchronized (BasicConnectionPool.class) {
                if (instance == null) {
                    instance = new BasicConnectionPool(configuration);
                }
            }
        }

        return instance;
    }
    public BasicConnectionPool(Configuration configuration) {
        this.configuration = configuration;
        createPool();
    }

    public BasicConnectionPool(Configuration configuration, int corePoolSize, int maxPoolSize) {
        this.configuration = configuration;
        if (corePoolSize > maxPoolSize) throw new RuntimeException("Core pool size must be lower than or equal to max " +
                String.format("pool size, but now with core pool size: %d, max pool size: %d", corePoolSize, maxPoolSize));
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        createPool();
    }

    private void createPool() {
        for (int i = 0; i < corePoolSize; i++) {
            pool.add(createConnection());
            locks.add(new AtomicBoolean());
        }
    }

    @Override
    public Connection getConnection() {
        if (availableConnSize == 0) {
            // add a monitor to avoid adding more connections than maxPoolSize
            synchronized (this) {
                if (pool.size() >= maxPoolSize ) {
                    throw new RuntimeException(String.format("The connections are unavailable for the moment. core pool size: %d, " +
                            "max pool size: %d, available connection size: %d", corePoolSize, maxPoolSize, availableConnSize));
                } else {
                    Connection conn = createConnection();
                    pool.add(conn);
                    locks.add(new AtomicBoolean());
                    return conn;
                }
            }
        } else {
            // find the available connections
            int randomstart = (int) (Math.random() * locks.size());
            while (availableConnSize > 0) {
                if (locks.get(randomstart).compareAndSet(false, true)) {
                    availableConnSize--;
                    return pool.get(randomstart);
                }
                randomstart = (randomstart + 1) % locks.size();
            }

            throw new RuntimeException(String.format("The connections are unavailable for the moment. core pool size: %d, " +
                    "max pool size: %d", corePoolSize, maxPoolSize));
        }
    }

    public Connection createConnection() {
        String driver = configuration.getDriver();
        String url = configuration.getUrl();
        String username = configuration.getUsername();
        String password = configuration.getPassword();

        Connection conn;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return conn;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        for (int i = 0; i < pool.size(); i++) {
            if (pool.get(i) == connection) {
                if (locks.get(i).compareAndSet(true, false)){
                    availableConnSize++;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getUrl() {
        return configuration.getUrl();
    }

    @Override
    public String getUserName() {
        return configuration.getUsername();
    }

    @Override
    public String getPassword() {
        return configuration.getPassword();
    }
}
