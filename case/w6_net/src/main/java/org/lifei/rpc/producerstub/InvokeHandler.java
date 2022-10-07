package org.lifei.rpc.producerstub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class InvokeHandler extends ChannelInboundHandlerAdapter {
    private String getImplClassName(ClassInfo classInfo) throws Exception {
        String interfacePath = "org.lifei.rpc.consumer";
        int lastDot = classInfo.getClassName().lastIndexOf(".");

        String interfaceName = classInfo.getClassName().substring(lastDot);

        Class superClass = Class.forName(interfacePath + interfaceName);

        Reflections reflections = new Reflections(interfacePath);
        Set<Class> ImplClassSet = reflections.getSubTypesOf(superClass);
        if (ImplClassSet.size() == 0) {
            System.out.println("未找到实现类");
            return null;
        } else if (ImplClassSet.size() > 1) {
            System.out.println("找到多个实现类，未明确使用哪一个");
            return null;
        } else {
            Class[] classes = ImplClassSet.toArray(new Class[0]);
            return classes[0].getName();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClassInfo classInfo = (ClassInfo)msg;
        Object clazz = Class.forName(getImplClassName(classInfo)).getDeclaredConstructor().newInstance();
        Method method = clazz.getClass().getMethod(classInfo.getMethodName(), classInfo.getTypes());

        Object result = method.invoke(clazz, classInfo.getObjects());
        ctx.writeAndFlush(result);
    }
}
