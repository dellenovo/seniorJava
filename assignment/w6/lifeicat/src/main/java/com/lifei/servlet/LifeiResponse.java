package com.lifei.servlet;

/**
 * Servlet规范之响应规范
 */
public interface LifeiResponse {
    // 将响应写入到Channel
    void write(String content) throws Exception;
}
