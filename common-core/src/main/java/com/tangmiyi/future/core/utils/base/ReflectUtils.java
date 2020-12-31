package com.tangmiyi.future.core.utils.base;

import java.lang.reflect.Constructor;


public class ReflectUtils {

    public static Object reflectByClassName(String className) {
        Object resultObj = null;
        try {
            Class<?> resultClass = Class.forName(className);
            // 获取指定的构造函数对象
            Constructor<?> constructor = resultClass.getConstructor();
            // 通过该构造器对象的newInstance方法进行对象的初始化
            resultObj = constructor.newInstance();

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultObj;
    }
}
