package com.yjs.designpattern.creation.singleton;

/**
 * 懒汉 双重锁校验
 * create by jiangsongy on 2018/9/17
 */
public class SingletonSlacker {

    private SingletonSlacker(){}

    private static SingletonSlacker instance;

    public static SingletonSlacker getInstance(){
        if (instance == null){
            synchronized (SingletonSlacker.class){
                if (instance == null){
                    instance = new SingletonSlacker();
                }
            }
        }
        return instance;
    }

}
