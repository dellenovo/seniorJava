package com.lifei.lifeicat;

public class LifeiCat {
    public static void run(String[] args) throws Exception {
        LifeiCatServer lifeiCatServer = new LifeiCatServer("com.lifei.webapp");
        lifeiCatServer.start();
    }
}
