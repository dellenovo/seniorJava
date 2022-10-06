package com.lifei.lifeicat;

import com.lifei.servlet.LifeiServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Map;

public class LifeiCatHandler extends ChannelInboundHandlerAdapter {
    private Map<String, LifeiServlet> nameToServletMap;
    private Map<String, String> name2ClassNameMap;

    public LifeiCatHandler(Map<String, LifeiServlet> nameToServletMap, Map<String, String> name2ClassNameMap) {
        this.nameToServletMap = nameToServletMap;
        this.name2ClassNameMap = name2ClassNameMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //todo: 为什么确定这里的msg一定是HttpRequest类型的呢？
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest)msg;
            String uri = request.uri();

            String servletName = "";
            if (uri.contains("/") && uri.contains("?")) {
                servletName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("?")).toLowerCase();
            }

            LifeiServlet servlet = new DefaultLifeiServlet();

            if (nameToServletMap.containsKey(servletName)) {
                servlet = nameToServletMap.get(servletName);
            } else if (name2ClassNameMap.containsKey(servletName)) {
                if (nameToServletMap.get(servletName) == null) {
                    synchronized (this) {
                        if (nameToServletMap.get(servletName) == null) {
                            String className = name2ClassNameMap.get(servletName);
                            servlet = (LifeiServlet)Class.forName(className).newInstance();
                            nameToServletMap.put(servletName, servlet);
                        }
                    }
                }
            }

            HttpLifeiRequest lifeiRequest = new HttpLifeiRequest(request);
            HttpLifeiResponse lifeiResponse = new HttpLifeiResponse(request, ctx);

            if (lifeiRequest.getMethod().equalsIgnoreCase("get")) {
                servlet.doGet(lifeiRequest, lifeiResponse);
            } else {
                servlet.doPost(lifeiRequest, lifeiResponse);
            }

            ctx.close();
        }
    }
}
