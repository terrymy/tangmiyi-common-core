package com.tangmiyi.future.core.utils.base;


import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @Classname StringToObjectUtil
 * @Description 拼接字符串转对象
 * @Date 2020/2/18 18:23
 * @Created by hyc
 */
public class StringToObjectUtil {

    public static <T> T StringToObj(String Str, String split, Class<T> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        String[] splitMaps = Str.split(split);//以指定字符分割字符对象
        Object instance = clazz.newInstance();
        for (String plit : splitMaps) {
            String[] splitMap = plit.split("=");
            BeanUtils.setProperty(instance, splitMap[0], splitMap[1]);
        }
        return (T) instance;
    }
}
