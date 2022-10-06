package com.lifei.webapp;

import com.lifei.servlet.LifeiRequest;

import java.util.List;
import java.util.Map;

public class TestLifeiCat {
    public static void main(String[] args) {
        LifeiRequest lifeiRequest = new LifeiRequest() {
            @Override
            public String getUri() {
                return null;
            }

            @Override
            public String getPath() {
                return null;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public Map<String, List<String>> getParameters() {
                return null;
            }

            @Override
            public List<String> getParameters(String name) {
                return null;
            }

            @Override
            public String getParameter(String name) {
                return null;
            }
        };
    }
}
