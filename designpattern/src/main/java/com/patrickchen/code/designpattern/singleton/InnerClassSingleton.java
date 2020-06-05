package com.patrickchen.code.designpattern.singleton;

public class InnerClassSingleton {
    //Inner class
    private static class MySingletonHandler{
        private static InnerClassSingleton instance = new InnerClassSingleton();
    }

    private InnerClassSingleton(){}

    public static InnerClassSingleton getInstance() {
        return MySingletonHandler.instance;
    }
}
