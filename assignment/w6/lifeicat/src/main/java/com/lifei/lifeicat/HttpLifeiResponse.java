package com.lifei.lifeicat;

import com.lifei.servlet.LifeiResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;

import java.nio.charset.StandardCharsets;

public class HttpLifeiResponse implements LifeiResponse {
    private HttpRequest request;
    private ChannelHandlerContext ctx;

    private String contentType = "text/json";

    public HttpLifeiResponse(HttpRequest httpRequest, ChannelHandlerContext ctx) {
        request = httpRequest;
        this.ctx = ctx;
    }

    @Override
    public void write(String content) throws Exception {
        if (StringUtil.isNullOrEmpty(content)) {
            return;
        }

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.copiedBuffer(content.getBytes(StandardCharsets.UTF_8)));

        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, contentType);
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.EXPIRES, 0);

        if (HttpUtil.isKeepAlive(request)) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ctx.writeAndFlush(response);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
