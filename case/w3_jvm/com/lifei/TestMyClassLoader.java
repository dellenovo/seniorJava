package com.lifei;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMyClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // the load path for customized class loader
        HeroClassLoader heroClassLoader = new HeroClassLoader(" /Users/lifei/Documents/T02_code/seniorJava/case/classfile");
        // package name + class name
        Class c = heroClassLoader.findClass("com.lifei.Test");

        if (c != null) {
            Object obj = c.newInstance();
            Method method = c.getMethod("say", null);
            method.invoke(obj, null);
            System.out.println(c.getClassLoader().toString());
        }
    }
}
