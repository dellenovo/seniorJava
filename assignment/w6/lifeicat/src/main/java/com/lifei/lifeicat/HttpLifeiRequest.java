package com.lifei.lifeicat;

import com.lifei.servlet.LifeiRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class HttpLifeiRequest implements LifeiRequest {
    private HttpRequest httpRequest;
    private QueryStringDecoder queryStringDecoder;

    public HttpLifeiRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
        queryStringDecoder = new QueryStringDecoder(httpRequest.uri());
    }

    @Override
    public String getUri() {
        return httpRequest.uri();
    }

    @Override
    public String getPath() {
        return queryStringDecoder.path();
    }

    @Override
    public String getMethod() {
        return httpRequest.method().name();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        return queryStringDecoder.parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> params = getParameters(name);
        return params == null || params.isEmpty() ? null : params.get(0);
    }
}
