package com.lifei.lifeibatisframe.openapi;

import java.util.List;

/**
 * expose API SqlSession
 * Typical JDBC operations are defined here: CRUD
 */
public interface SqlSession {
    /**
     * query all users
     * @param sql
     * @return
     * @param <T>
     * @throws Exception
     */
    <T> List<T> selectList(String sql) throws Exception;
}
