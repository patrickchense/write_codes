package com.patrickchen.code.designpattern.singleton;

public class LazySingletonThread extends Thread {
    @Override
    public void run() {
        System.out.println(LazySingleton.getInstance().hashCode());
    }

    public static void main(String[] args) {

        LazySingletonThread[] mts = new LazySingletonThread[10];
        for(int i = 0 ; i < mts.length ; i++){
            mts[i] = new LazySingletonThread();
        }

        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }
}
