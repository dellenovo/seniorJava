package com.lifei.lifeibatisframe.core.factory;

import com.lifei.lifeibatisframe.core.entity.Configuration;
import com.lifei.lifeibatisframe.openapi.SqlSession;
import com.lifei.lifeibatisframe.openapi.SqlSessionImpl;

public class SqlSessionFactory {
    private Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * create sql session
     * @return
     */
    public SqlSession openSession() {
        return new SqlSessionImpl(configuration);
    }
}
