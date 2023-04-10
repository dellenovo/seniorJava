package com.lifeiexample;

import com.lifei.lifeibatisframe.core.factory.SqlSessionFactory;
import com.lifei.lifeibatisframe.openapi.SqlSession;
import com.lifei.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LifeibatisTests {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    @Test
    void selectList() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users =
                sqlSession.selectList("com.lifei.dao.UserMapper.findAll");
        users.stream().forEach(System.out::println);
    }
}
