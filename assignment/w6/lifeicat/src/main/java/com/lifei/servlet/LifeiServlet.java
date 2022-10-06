package com.lifei.servlet;

/**
 * 定义Servlet规范
 */
public abstract class LifeiServlet {

    public abstract void doGet(LifeiRequest request, LifeiResponse response) throws Exception;
    public abstract void doPost(LifeiRequest request, LifeiResponse response) throws Exception;
}
