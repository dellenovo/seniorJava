package com.lifei;

import java.io.*;

public class HeroClassLoader extends ClassLoader{
    private String classpath;

    public HeroClassLoader(String classpath) {
        this.classpath = classpath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            //输入流，通过类的全限定名称加载文件到字节数组
            byte[] classData = getData(name);
            if (classData != null) {
                // defineClass方法将字节数组数据转为字节码对象
                return defineClass(name, classData, 0, classData.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private byte[] getData(String className) throws IOException {
        String path = classpath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        try (InputStream in = new FileInputStream(path);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
