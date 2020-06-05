package com.patrickchen.code.designpattern.singleton;

public class LazySingletonSync {
    private static LazySingletonSync instance = null;

    private LazySingletonSync() {
    }

    public synchronized static LazySingletonSync getInstance() {
        if (instance == null) {
            instance = new LazySingletonSync();
        }
        return instance;
    }

    public static LazySingletonSync getInstanceBySyncBlcok() {
        synchronized (LazySingletonSync.class) {
            if (instance == null) {
                instance = new LazySingletonSync();
            }
        }
        return instance;
    }

    public static LazySingletonSync getInstanceBySmallerSyncBlock() {
        if (instance != null) {
            // do something
        } else {
            synchronized (LazySingletonSync.class) {
                instance = new LazySingletonSync();
            }
        }
        return instance;
    }
}
