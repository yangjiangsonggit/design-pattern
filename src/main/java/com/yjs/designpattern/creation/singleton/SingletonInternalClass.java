package com.yjs.designpattern.creation.singleton;

/**
 * 内部类(变相懒加载)
 * create by jiangsongy on 2018/9/17
 */
public class SingletonInternalClass {

    private SingletonInternalClass(){}

    private static class SingletonHolder{
        private static SingletonInternalClass instance = new SingletonInternalClass();
    }

    public static SingletonInternalClass getInstance(){
        return SingletonHolder.instance;
    }
}
