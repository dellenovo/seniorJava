package com.lifei.webapp;

import com.lifei.servlet.LifeiRequest;
import com.lifei.servlet.LifeiResponse;
import com.lifei.servlet.LifeiServlet;

public class UserServlet extends LifeiServlet {
    @Override
    public void doGet(LifeiRequest request, LifeiResponse response) throws Exception {
        response.write(String.format("uri=%s\npath=%s\nmethod=%s\nparam=%s", request.getUri(), request.getPath(),
                request.getMethod(), request.getParameters()));
    }

    @Override
    public void doPost(LifeiRequest request, LifeiResponse response) throws Exception {
        doGet(request, response);
    }
}
