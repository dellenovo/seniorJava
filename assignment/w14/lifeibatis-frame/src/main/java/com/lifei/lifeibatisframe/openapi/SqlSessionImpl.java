package com.lifei.lifeibatisframe.openapi;

import com.lifei.lifeibatisframe.core.Executor;
import com.lifei.lifeibatisframe.core.entity.Configuration;

import java.util.List;

public class SqlSessionImpl implements SqlSession{
    private Configuration configuration;

    public SqlSessionImpl(Configuration configuration) {
        this.configuration = configuration;
    }
    @Override
    public <T> List<T> selectList(String sql) throws Exception {
        Executor executor = new Executor(configuration);
        return (List<T>)executor.executeQuery(sql);
    }
}
