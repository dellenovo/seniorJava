package com.lifei;

import com.lifei.lifeibatisframe.core.factory.SqlSessionFactory;
import com.lifei.lifeibatisframe.core.factory.SqlSessionFactoryBuilder;
import com.lifei.lifeibatisframe.openapi.SqlSession;
import com.lifei.pojo.User;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class CustomFrameTest {
    SqlSession sqlSession;

    @Before
    public void init() throws Exception {
        // 1. create SqlSessionFactoryBuilder
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();

        // 2. Builder builds factory
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
         // 3. open SqlSession
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test() throws Exception {
        // 4. execute sql
        List<User> users = sqlSession.selectList("com.lifei.dao.UserMapper.findAll");

        // 5. loop print
        for (User u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void testMultiThread() throws Exception {
        int threadCount = 14;
        Thread[] ts = new Thread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            ts[i] = new Thread(new SqlClient());
            ts[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            ts[i].join();
        }
    }

    class SqlClient implements Runnable {
        @Override
        public void run() {
            try {
                List<User> users = sqlSession.selectList("com.lifei.dao.UserMapper.findAll");
                System.out.printf("%s find %d users.%n", Thread.currentThread().getName(), users.size());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
