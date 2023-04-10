package com.lifei.autoconfigure;


import com.lifei.lifeibatisframe.core.factory.SqlSessionFactory;
import com.lifei.lifeibatisframe.core.factory.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
@ConditionalOnClass(name = "com.lifei.lifeibatisframe.core.factory.SqlSessionFactory")
public class LifeibatisAutoConfiguration {
    @Bean
    @ConditionalOnProperty(value = "enable.lifeibatis", havingValue = "true", matchIfMissing = true)
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = builder.build(inputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        return sqlSessionFactory;
    }
}
