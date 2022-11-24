package com.lifei.lifeibatisframe.core.factory;

import com.lifei.lifeibatisframe.core.entity.Configuration;
import com.lifei.lifeibatisframe.core.entity.SqlSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * build the factory for sql session factory
 */
public class SqlSessionFactoryBuilder {
    /**
     * build sql session factory
     * @param inputStream the input stream of SqlMapConfig.xml
     * @return
     * @throws DocumentException
     */
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException {
        Configuration configuration = new Configuration();
        // parse configuration file
        loadXmlConfig(configuration, inputStream);
        return new SqlSessionFactory(configuration);
    }

    /**
     * parse the configuration file passed in by the framework user
     * @param configuration
     * @param inputStream
     * @throws DocumentException
     */
    private void loadXmlConfig(Configuration configuration, InputStream inputStream) throws DocumentException {
        // create SAXReader for parsing XML file
        SAXReader saxReader = new SAXReader();

        // get the document object by reading the file stream of the SqlMapConfig.xml
        Document document = saxReader.read(inputStream);

        // get all elements of tag property from the SqlMapConfig.xml
        List<Element> selectNodes = document.selectNodes("//property");

        // extract configuration info by parsing the content of the tag property in the loop
        for (Element element : selectNodes) {
            String name = element.attributeValue("name");
            if ("driver".equals(name)) {
                // db driver
                configuration.setDriver(element.attributeValue("value"));
            } else if ("url".equals(name)) {
                // db url
                configuration.setUrl(element.attributeValue("value"));
            } else if ("username".equals(name)) {
                // db username
                configuration.setUsername(element.attributeValue("value"));
            } else if ("password".equals(name)) {
                // db password
                configuration.setPassword(element.attributeValue("value"));
            }
        }

        // parse the mapper from SqlMapConfig.xml
        List<Element> list = document.selectNodes("//mapper");
        for (Element element : list) {
            // the file path of SQL mapper configuration
            String resource = element.attributeValue("resource");

            // parse SQL mapper configuration file
            loadSqlConfig(resource, configuration);
        }
    }

    /**
     * parse SQL mapper configuration file
     * @param resource
     * @param configuration
     */
    private void loadSqlConfig(String resource, Configuration configuration) throws DocumentException {
       // read stream resource under classpath per the path of the SQL mapper configuration file
       InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);

       // create SAXReader for parsing SQL mapper XML file
        SAXReader saxReader = new SAXReader();

        // read UserMapper.xml
        Document document = saxReader.read(inputStream);

        // get root node of the document object: <mapper namespace="test">
        Element rootElement = document.getRootElement();

        // get the name space of the root node
        String namespace = rootElement.attributeValue("namespace");

        // get all tag select from the current SQL mapper file
        List<Element> selectNodes = document.selectNodes("//select");

        // repeatedly parse the tag select to extract SQL statements
        for (Element element : selectNodes) {
            // query statement ID
            String id = element.attributeValue("id");

            // the result type object of the current sql
            String resultType = element.attributeValue("resultType");

            // query statement
            String sql = element.getText();

            // create Mapper
            SqlSource mapper = new SqlSource();
            mapper.setSql(sql);
            mapper.setResultType(resultType);

            // add mapper to configuration with key: ${namespace}.${sql statement id}
            configuration.getSqlSourceMap().put(namespace + "." + id, mapper);
        }
    }
}
