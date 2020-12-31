package com.tangmiyi.future.core.utils.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ListUtils {

    public static <T> List<T> nonEmptyList(List<T> list) {
        list.removeAll(Collections.singleton(null));
        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 一个list 等均分成几个
     */
    public static <T> List<List<T>> avgList(List<T> source, int n) {
        List<List<T>> result = new ArrayList<>();
        //(先计算出余数)
        int remainder = source.size() % n;
        //然后是商
        int number = source.size() / n;
        //偏移量
        int offset = 0;
        int len = (n > source.size()) ? source.size() : n;
        for (int i = 0; i < len; i++) {
            List<T> value;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }
}
