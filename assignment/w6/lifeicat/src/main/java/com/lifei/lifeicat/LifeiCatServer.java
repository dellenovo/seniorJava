package com.lifei.lifeicat;

import com.lifei.servlet.LifeiServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LifeiCatServer {
    // the simple name of a servlet -> the corresponding servlet
    private Map<String, LifeiServlet> name2ServletMap = new ConcurrentHashMap<>();
    // the simple name of a servlet -> the qualified name of the servlet
    private Map<String, String> name2ClassNameMap = new HashMap<>();
    // the storage of all servlets
    private String basePackage;

    public LifeiCatServer(String basePackage) {
        this.basePackage = basePackage;
    }

    // run the server
    public void start() throws Exception {
        // cache all servlet classes
        cacheClassName(basePackage);
        runServer();
    }

    private void cacheClassName(String basePackage) {
        String resourceBasePackage = basePackage;
        if (!basePackage.startsWith(File.separator)) {
            resourceBasePackage = File.separator + basePackage;
        }

        URL resource = this.getClass().getResource(resourceBasePackage.replace('.', File.separatorChar));
        if (resource == null) return;

        // convert URL resource to File resource
        File dir = new File(resource.getFile());

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                cacheClassName(String.format("%s.%s", basePackage, file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String simpleClassName = file.getName().replace(".class", "").trim();
                name2ClassNameMap.put(simpleClassName.toLowerCase(), String.format("%s.%s", basePackage, simpleClassName));
            }
        }
    }

    private void runServer() throws Exception {
        EventLoopGroup parent = new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parent, child)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new LifeiCatHandler(name2ServletMap, name2ClassNameMap));
                        }
                    });
            int port = initPort();
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("LifeiCat Server bootstrapped successfully with the port:" + port);
            future.channel().closeFuture().sync();
        } finally {
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }

    private int initPort() throws DocumentException {
        //todo: try getClassLoader() directly
        InputStream in = LifeiCatServer.class.getClassLoader().getResourceAsStream("server.xml");
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(in);
        Element portEle = (Element) doc.selectSingleNode("//port");
        return Integer.valueOf(portEle.getText());
    }
}
