package com.patrickchen.code.designpattern.singleton;

public class LazySingleton {
    private static LazySingleton instance = null;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) { // init for the first time
            instance = new LazySingleton();
        }
        return instance;
    }
}
