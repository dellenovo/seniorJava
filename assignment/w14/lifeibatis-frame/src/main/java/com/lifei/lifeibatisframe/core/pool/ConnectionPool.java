package com.lifei.lifeibatisframe.core.pool;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUserName();
    String getPassword();
}
