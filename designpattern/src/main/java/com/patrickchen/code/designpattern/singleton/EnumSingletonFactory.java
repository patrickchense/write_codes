package com.patrickchen.code.designpattern.singleton;

public enum EnumSingletonFactory {
    singletonFactory;

    private EnumSingleton instance;

    private EnumSingletonFactory(){ // enum constructor is called when enum loaded by the JVM
        instance = new EnumSingleton();
    }

    public EnumSingleton getInstance(){
        return instance;
    }
}
