package com.lifei.lifeibatisframe.core;

import com.lifei.lifeibatisframe.core.entity.Configuration;
import com.lifei.lifeibatisframe.core.entity.SqlSource;
import com.lifei.lifeibatisframe.core.pool.BasicConnectionPool;
import com.lifei.lifeibatisframe.core.pool.ConnectionPool;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executor {
    private Configuration configuration;

    private ConnectionPool cp;

    public Executor(Configuration configuration) {
        this.configuration = configuration;
        cp = BasicConnectionPool.getInstance(configuration);
    }

    public List executeQuery(String statement) throws Exception {
        // avoid hardcode for db connection

        // 1. get configuration & sql source
        Map<String, SqlSource> map = configuration.getSqlSourceMap();
        //SQL mapper
        SqlSource mapper = map.get(statement);
        String sqlStr = mapper.getSql(); // get query statement
        String resultType = mapper.getResultType();// get the qualified name of the return type

        // 2. get connection from connection pool
        Connection conn = cp.getConnection();

        // 3. create object statement and fill SQL statement
        PreparedStatement ps = conn.prepareStatement(sqlStr);

        // 4. execute query sql and return result set
        ResultSet rs = ps.executeQuery();

        // 5. parse result set and get the list of users
        // get the metadata of result set
        ResultSetMetaData metaData = rs.getMetaData();

        // get total column count
        int columnCount = metaData.getColumnCount();

        // get the list collections of all columns
        List<String> columnNames = new ArrayList<String>();

        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        List list = new ArrayList();

        // parse result set in a loop
        while (rs.next()) {
            Class<?> clazz = Class.forName(resultType);

            // create object by reflection
            Object user = clazz.newInstance();

            // get all methods of the current class by reflection
            Method[] methods = clazz.getMethods();

            // traverse all column names
            for (String columnName : columnNames) {
                for (Method method : methods) {
                    String methodName = method.getName();
                    if (("set" + columnName).equalsIgnoreCase(methodName)) {
                        method.invoke(user, rs.getObject(columnName));
                    }
                }
            }

            // add the user to the list
            list.add(user);
        }

        // close connection to release resource
        rs.close();
        ps.close();
        cp.releaseConnection(conn);
        return list;
    }
}
