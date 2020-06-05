package com.patrickchen.code.designpattern.singleton;

public class HungrySingletonThread extends Thread {
    @Override
    public void run() {
        System.out.println(HungrySingleton.getInstance().hashCode());
    }

    public static void main(String[] args) {

        HungrySingletonThread[] mts = new HungrySingletonThread[10];
        for(int i = 0 ; i < mts.length ; i++){
            mts[i] = new HungrySingletonThread();
        }

        for (int j = 0; j < mts.length; j++) {
            mts[j].start();
        }
    }
}
